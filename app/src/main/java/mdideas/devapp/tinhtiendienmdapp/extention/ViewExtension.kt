package mdideas.devapp.tinhtiendienmdapp.extention

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.SystemClock
import android.util.Property
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

fun View.safeClick(listener: View.OnClickListener, blockInMillis: Long = 500) {
    var lastClickTime: Long = 0
    this.setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime < blockInMillis) return@setOnClickListener
        lastClickTime = SystemClock.elapsedRealtime()
        listener.onClick(this)
    }
}

fun View.hideSoftKeyBoard() {
    val inputMethodManager = this.context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showSoftKeyBoard() {
    val inputMethodManager = this.context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.setMargins(top: Int? = null, end: Int? = null, bottom: Int? = null, start: Int? = null) {
    val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.setMargins(
        start ?: layoutParams.marginStart,
        top ?: layoutParams.topMargin,
        end ?: layoutParams.marginEnd,
        bottom ?: layoutParams.bottomMargin
    )
    this.requestLayout()
}

fun View.startValueAnimator(
    property: Property<View, Float>, vararg values: Float,
    duration: Long = 1000,
    repeatMode: Int = ValueAnimator.RESTART, repeatCount: Int = 1
) {
    ObjectAnimator.ofFloat(this, property, *values).apply {
        this.duration = duration
        this.repeatMode = repeatMode
        this.repeatCount = repeatCount
        start()
    }
}

fun View.handleVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

fun View.handleGoneVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

