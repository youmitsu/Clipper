package youmeee.co.jp.clippablelayout

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.Window
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
    var clipAnimator = ClipAnimator(AnimationType.DEFAULT)

    fun showOverlay(w: Window, p: ViewGroup) {
        setOnClickListener {
            (parent as? ViewGroup)?.removeView(this)
            queueDispatcher?.onDetachedClippableView()
        }
        clippableView?.showOverlay(this, w, backGroundColor)
        descView?.let { addView(it.descView, it.lp) }
        p.addView(this, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        clipAnimator.animateClip(this)
        invalidate()
    }
}

interface Animator {
    fun animateClip(targetLayout: ClippableLayout)
}

class ClipAnimator(animationType: AnimationType) : Animator {

    override fun animateClip(targetLayout: ClippableLayout) {
        ObjectAnimator.ofFloat(targetLayout, "alpha", 0f, 1f).apply {
            duration = 300
            start()
        }
    }

}

sealed class AnimationType {
    object DEFAULT : AnimationType()
    object CLOCKWISE : AnimationType()
    object ZOOM : AnimationType()
}