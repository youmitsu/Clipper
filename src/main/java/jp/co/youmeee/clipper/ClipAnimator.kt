package jp.co.youmeee.clipper

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator

abstract class ClipAnimator {
    abstract fun animateClipStart(targetLayout: ClipperLayout, onAnimateEnded: (() -> Unit)?)
    abstract fun animateClipEnd(targetLayout: ClipperLayout, onAnimateEnded: (() -> Unit)?)
}

class DefaultClipAnimator(private val animateDuration: Long = 250L) : ClipAnimator() {

    override fun animateClipStart(targetLayout: ClipperLayout, onAnimateEnded: (() -> Unit)?) {
        animateClip(targetLayout, 0f, 1f, onAnimateEnded)
    }

    override fun animateClipEnd(targetLayout: ClipperLayout, onAnimateEnded: (() -> Unit)?) {
        animateClip(targetLayout, 1f, 0f, onAnimateEnded)
    }

    private fun animateClip(
        targetLayout: ClipperLayout,
        startAlpha: Float,
        endAlpha: Float,
        onAnimateEnded: (() -> Unit)? = null
    ) {
        ObjectAnimator.ofFloat(targetLayout, "alpha", startAlpha, endAlpha).apply {
            this.duration = animateDuration
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    onAnimateEnded?.invoke()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

            })
            start()
        }
    }
}

class CircleRevealClipAnimator(
    private val duration: Long = 250L,
    private val interpolator: Interpolator = AccelerateInterpolator()
) : ClipAnimator() {

    override fun animateClipStart(targetLayout: ClipperLayout, onAnimateEnded: (() -> Unit)?) {
        animateClip(targetLayout, 0f, targetLayout.measuredHeight.toFloat(), onAnimateEnded)
    }

    override fun animateClipEnd(targetLayout: ClipperLayout, onAnimateEnded: (() -> Unit)?) {
        animateClip(targetLayout, targetLayout.measuredHeight.toFloat(), 0f, onAnimateEnded)
    }

    private fun animateClip(
        targetLayout: ClipperLayout,
        startRadius: Float,
        endRadius: Float,
        onAnimateEnded: (() -> Unit)? = null
    ) {
        val targetClipView: View? = targetLayout.clipperView.clipList.first().targetView
        val centerX: Int =
            if (targetClipView == null) targetLayout.measuredWidth / 2 else (targetClipView.right + targetClipView.left) / 2
        val centerY: Int =
            if (targetClipView == null) targetLayout.measuredHeight / 2 else (targetClipView.top + targetClipView.bottom) / 2

        val animator = ViewAnimationUtils.createCircularReveal(
            targetLayout,
            centerX,
            centerY,
            startRadius,
            endRadius
        )
        animator.interpolator = this.interpolator
        animator.duration = this.duration
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                onAnimateEnded?.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animator.start()
    }

}