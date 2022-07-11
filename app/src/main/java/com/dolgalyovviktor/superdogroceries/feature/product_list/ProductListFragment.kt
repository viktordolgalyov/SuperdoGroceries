package com.dolgalyovviktor.superdogroceries.feature.product_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dolgalyovviktor.superdogroceries.R
import com.dolgalyovviktor.superdogroceries.common.arch.UIEvent
import com.dolgalyovviktor.superdogroceries.common.arch.viewBinding
import com.dolgalyovviktor.superdogroceries.common.ui.util.doOnNextLayout
import com.dolgalyovviktor.superdogroceries.databinding.FragmentProductListBinding
import com.dolgalyovviktor.superdogroceries.feature.product_list.di.ProductListComponent
import com.dolgalyovviktor.superdogroceries.feature.product_list.presentation.ProductListAction
import com.dolgalyovviktor.superdogroceries.feature.product_list.presentation.ProductListPresentationModel
import com.dolgalyovviktor.superdogroceries.feature.product_list.presentation.ProductListPresentationModel.ListeningButtonState
import com.dolgalyovviktor.superdogroceries.feature.product_list.presentation.ProductListViewModel
import com.dolgalyovviktor.superdogroceries.feature.product_list.presentation.ProductListViewModelFactory
import com.dolgalyovviktor.superdogroceries.feature.product_list.router.ProductListRouter
import com.dolgalyovviktor.superdogroceries.feature.product_list.view.ProductListAdapter
import javax.inject.Inject

class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    companion object {
        private const val AUTOSCROLL_THRESHOLD = 10
        fun create() = ProductListFragment()
    }

    private val component by lazy {
        (activity as ProductListComponent.ComponentProvider).provideProductListComponent()
    }
    @Inject lateinit var viewModelFactory: ProductListViewModelFactory
    @Inject lateinit var router: ProductListRouter
    private val viewModel by viewModels<ProductListViewModel> { viewModelFactory }
    private val binding by viewBinding(FragmentProductListBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.router = router
        viewModel.observableModel.observe(viewLifecycleOwner, Observer(::renderModel))
        viewModel.observableEvent.observe(viewLifecycleOwner, Observer(::renderEvent))
    }

    private fun renderModel(model: ProductListPresentationModel) = binding?.run {
        (products.adapter as ProductListAdapter).submitList(model.items)
        listeningState.text = when (model.listeningButton) {
            ListeningButtonState.Start -> getString(R.string.label_start)
            ListeningButtonState.Stop -> getString(R.string.label_stop)
        }
        scrollToBottom()
    }

    private fun renderEvent(event: UIEvent) = Unit

    private fun initViews() = binding?.run {
        products.layoutManager = LinearLayoutManager(products.context).apply {
            orientation = RecyclerView.VERTICAL
        }
        products.itemAnimator = DefaultItemAnimator().apply {
            supportsChangeAnimations = false
        }
        products.adapter = ProductListAdapter()
        listeningState.setOnClickListener {
            viewModel.dispatch(ProductListAction.ChangeListeningStateClick)
        }
    }

    private fun scrollToBottom() = binding?.products?.doOnNextLayout(
        action = {
            val lastPosition = binding?.products?.adapter?.itemCount ?: 0
            binding?.products?.scrollToPosition(lastPosition)
        },
        condition = { (binding?.products?.adapter?.itemCount ?: 0) > AUTOSCROLL_THRESHOLD }
    )
}