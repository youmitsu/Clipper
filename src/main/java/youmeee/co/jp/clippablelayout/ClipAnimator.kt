package youmeee.co.jp.clippablelayout

import android.animation.ObjectAnimator

abstract class ClipAnimator(val targetLayout: ClippableLayout) {
    abstract fun animateClip()
}

class DefaultClipAnimator(targetLayout: ClippableLayout, private val duration: Int = 300) :
    ClipAnimator(targetLayout) {
    override fun animateClip() {
        ObjectAnimator.ofFloat(targetLayout, "alpha", 0f, 1f).apply {
            duration = this.duration
            start()
        }
    }
}

//TODO: I will add it.
//class ZoomClipAnimator(
//    targetLayout: ClippableLayout,
//    private val duration: Long = 1000L,
//    private val interpolator: Interpolator = AccelerateInterpolator()
//) : ClipAnimator(targetLayout) {
//    override fun animateClip() {
//        val targetClipView: View? = targetLayout.clippableView?.clipList?.firstOrNull()?.targetView
//        val centerX: Int =
//            if (targetClipView == null) targetLayout.measuredWidth / 2 else (targetClipView.right + targetClipView.left) / 2
//        val centerY: Int =
//            if (targetClipView == null) targetLayout.measuredHeight / 2 else (targetClipView.top + targetClipView.bottom) / 2
//        val radius: Float =
//            if (targetClipView == null) 0f else (targetClipView.right - targetClipView.left).toFloat()
//
//        val animator = ViewAnimationUtils.createCircularReveal(
//            targetLayout.parent as View,
//            centerX,
//            centerY,
//            Math.sqrt(
//                Math.pow(
//                    targetLayout.measuredWidth.toDouble(), 2.0
//                ) + Math.pow(
//                    targetLayout.measuredHeight.toDouble(), 2.0
//                )
//            ).toFloat(),
//            radius
//        )
//        animator.interpolator = this.interpolator
//        animator.duration = this.duration
//        animator.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationRepeat(animation: Animator?) {
//            }
//
//            override fun onAnimationEnd(animation: Animator?) {
//            }
//
//            override fun onAnimationCancel(animation: Animator?) {
//            }
//
//            override fun onAnimationStart(animation: Animator?) {
//            }
//
//        })
//        animator.start()
//    }
//
//}