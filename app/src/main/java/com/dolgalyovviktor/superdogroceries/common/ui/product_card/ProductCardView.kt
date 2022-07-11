package com.dolgalyovviktor.superdogroceries.common.ui.product_card

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import com.dolgalyovviktor.superdogroceries.databinding.LayoutProductCardBinding


class ProductCardView : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    private val binding = LayoutProductCardBinding.inflate(
        LayoutInflater.from(context), this
    )

    fun render(model: ProductCardRenderModel) = binding.run {
        productName.text = model.name
        productQuantity.text = model.quantity
        productImage.setImageDrawable(
            generateCircleDrawable(
                color = Color.parseColor(model.color)
            )
        )
    }

    private fun generateCircleDrawable(@ColorInt color: Int) = GradientDrawable().apply {
        setColor(color)
        shape = GradientDrawable.OVAL
    }
}