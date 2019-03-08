package youmeee.co.jp.clippablelayout

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import android.widget.FrameLayout

/**
 * ClippableLayout
 */
class ClippableLayout : FrameLayout {

    constructor(context: Context) : this(context, null)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : this(context, attrs, 0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle)

    var clippableView: ClippableView? = null
    var descView: DescriptionView? = null
    var backGroundColor: Int = R.color.default_gray
    var queueDispatcher: ClippableQueueDispatcher? = null
    var clipAnimator: ClipAnimator? = null

    fun showOverlay(w: Window, p: ViewGroup) {
        setOnClickListener {
            (parent as? ViewGroup)?.removeView(this)
            queueDispatcher?.onDetachedClippableView()
        }
        clippableView?.showOverlay(this, w, backGroundColor)
        descView?.let { addView(it.descView, it.lp) }
        clipAnimator = ZoomClipAnimator(this)
        p.addView(this, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        clipAnimator?.animateClip()
    }
}


abstract class ClipAnimator(val targetLayout: ClippableLayout) {
    abstract fun animateClip()
}

class DefaultClipAnimator(targetLayout: ClippableLayout, private val startAlpha: Float, private val endAlpha: Float) :
    ClipAnimator(targetLayout) {
    override fun animateClip() {
        ObjectAnimator.ofFloat(targetLayout, "alpha", startAlpha, endAlpha).apply {
            duration = 300
            start()
        }
    }
}

class ZoomClipAnimator(
    targetLayout: ClippableLayout,
    val duration: Long = 1000L,
    val interpolator: Interpolator = AccelerateInterpolator()
) : ClipAnimator(targetLayout) {
    override fun animateClip() {
        val targetClipView: View? = targetLayout.clippableView?.clipList?.firstOrNull()?.targetView
        val centerX: Int =
            if (targetClipView == null) targetLayout.measuredWidth / 2 else (targetClipView.right + targetClipView.left) / 2
        val centerY: Int =
            if (targetClipView == null) targetLayout.measuredHeight / 2 else (targetClipView.top + targetClipView.bottom) / 2
        val radius: Float =
            if (targetClipView == null) 0f else (targetClipView.right - targetClipView.left).toFloat()

        val animator = ViewAnimationUtils.createCircularReveal(
            targetLayout.parent as View,
            centerX,
            centerY,
            Math.sqrt(
                Math.pow(
                    targetLayout.measuredWidth.toDouble(), 2.0
                ) + Math.pow(
                    targetLayout.measuredHeight.toDouble(), 2.0
                )
            ).toFloat(),
            radius
        )
        interpolator?.let { animator.interpolator = it }
        animator.duration = duration
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