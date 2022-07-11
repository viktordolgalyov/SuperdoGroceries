package com.dolgalyovviktor.superdogroceries.common.arch

inline fun <reified R : UIRouter> R?.exec(crossinline action: R.() -> Unit) = this?.run(action)