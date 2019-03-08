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
    var clipAnimator: ClipAnimator? = DefaultClipAnimator(this)

    fun showOverlay(w: Window, p: ViewGroup) {
        setOnClickListener {
            (parent as? ViewGroup)?.removeView(this)
            queueDispatcher?.onDetachedClippableView()
        }
        clippableView?.showOverlay(this, w, backGroundColor)
        descView?.let { addView(it.descView, it.lp) }
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

class DefaultClipAnimator(targetLayout: ClippableLayout, private val duration: Int = 300) :
    ClipAnimator(targetLayout) {
    override fun animateClip() {
        ObjectAnimator.ofFloat(targetLayout, "alpha", 0f, 1f).apply {
            duration = this.duration
            start()
        }
    }
}

class ZoomClipAnimator(
    targetLayout: ClippableLayout,
    private val duration: Long = 1000L,
    private val interpolator: Interpolator = AccelerateInterpolator()
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