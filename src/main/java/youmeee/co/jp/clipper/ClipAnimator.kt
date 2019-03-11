package youmeee.co.jp.clipper

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator

abstract class ClipAnimator {
    abstract fun animateClip(targetLayout: ClipperLayout)
}

class DefaultClipAnimator(private val duration: Int = 250) :
    ClipAnimator() {
    override fun animateClip(targetLayout: ClipperLayout) {
        ObjectAnimator.ofFloat(targetLayout, "alpha", 0f, 1f).apply {
            duration = this.duration
            start()
        }
    }
}

class CircleRevealClipAnimator(
    private val duration: Long = 250L,
    private val interpolator: Interpolator = AccelerateInterpolator()
) : ClipAnimator() {
    override fun animateClip(targetLayout: ClipperLayout) {
        val targetClipView: View? = targetLayout.clipperView?.clipList?.firstOrNull()?.targetView
        val centerX: Int =
            if (targetClipView == null) targetLayout.measuredWidth / 2 else (targetClipView.right + targetClipView.left) / 2
        val centerY: Int =
            if (targetClipView == null) targetLayout.measuredHeight / 2 else (targetClipView.top + targetClipView.bottom) / 2
        val radius: Float =
            if (targetClipView == null) 0f else (targetClipView.right - targetClipView.left).toFloat()

        val animator = ViewAnimationUtils.createCircularReveal(
            targetLayout,
            centerX,
            centerY,
            radius,
            Math.sqrt(
                Math.pow(
                    targetLayout.measuredWidth.toDouble(), 2.0
                ) + Math.pow(
                    targetLayout.measuredHeight.toDouble(), 2.0
                )
            ).toFloat()
        )
        animator.interpolator = this.interpolator
        animator.duration = this.duration
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animator.start()
    }

}