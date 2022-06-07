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
 * description:底部横条的自定义行为
 * author:than
 * email:2058109198@qq.com
 * date:2022/6/6
 */
class BottomHide(context: Context?, attrs: AttributeSet?) :
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
        if (sum > child.height) {
            hide(child)
        } else if (sum < 0) {
            show(child)
        }
    }

    private fun hide(view: View) {
        val animator = ValueAnimator.ofInt(0, view.height)
        animator.duration = 200
        animator.interpolator = FastOutLinearInInterpolator()
        animator.addUpdateListener { valueAnimator ->
            view.translationY = valueAnimator.animatedValue.toString().toFloat()
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                a = false
                b = true
            }
        })
        if (a) animator.start()
    }

    private fun show(view: View) {
        val animator = ValueAnimator.ofInt(view.height, 0)
        animator.duration = 200
        animator.interpolator = FastOutLinearInInterpolator()
        animator.addUpdateListener { valueAnimator ->
            view.translationY = valueAnimator.animatedValue.toString().toFloat()
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                b = false
                a = true
            }
        })
        if (b) animator.start()
    }
}