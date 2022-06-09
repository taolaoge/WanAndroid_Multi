package com.ndhzs.module.main.behavior

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutLinearInInterpolator

/**
 * description:悬浮按钮的自定义行为
 * author:than
 * email:2058109198@qq.com
 * date:2022/6/6
 */
class ButtonHide(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {
    private var sum = 0
    private var a = true
    private var b = true
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (dy > 0 && sum < 0 || dy < 0 && sum > 0) {
            child.animate().cancel()
            sum = 0
        }
        sum += dy
        val size = child.height / 100
        if (sum > 0) {
            hide(child, size)
        } else if (sum < 0) {
            show(child, size)
        }
    }

    private fun hide(view: View, size: Int) {
        val animator = ValueAnimator.ofFloat(size.toFloat(), 0f)
        animator.duration = 200
        animator.interpolator = FastOutLinearInInterpolator()
        animator.addUpdateListener { valueAnimator ->
            view.scaleX = valueAnimator.animatedValue as Float
            view.scaleY = valueAnimator.animatedValue as Float
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                view.isClickable = false
            }

            override fun onAnimationStart(animation: Animator) {
                a = false
                b = true
            }
        })
        if (a) animator.start()
    }

    private fun show(view: View, size: Int) {
        val animator = ValueAnimator.ofFloat(0f, size.toFloat())
        animator.duration = 200
        animator.interpolator = FastOutLinearInInterpolator()
        animator.addUpdateListener { valueAnimator ->
            view.scaleX = valueAnimator.animatedValue as Float
            view.scaleY = valueAnimator.animatedValue as Float
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                view.isClickable = true
            }

            override fun onAnimationStart(animation: Animator) {
                b = false
                a = true
            }
        })
        if (b) animator.start()
    }
}