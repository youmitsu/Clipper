package youmeee.co.jp.clippablelayout

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.View
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
    var descView: View? = null
    var backGroundColor: Int = R.color.default_gray
    var queueDispatcher: ClippableQueueDispatcher? = null

    fun showOverlay(w: Window, p: ViewGroup) {
        val lp = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        lp.topMargin = context.resources.getDimensionPixelSize(R.dimen.top_clip_margin)
        lp.gravity = CENTER_HORIZONTAL
        setOnClickListener {
            (parent as? ViewGroup)?.removeView(this)
            queueDispatcher?.onDetachedClippableView()
        }
        clippableView?.showOverlay(this, w, backGroundColor)
        descView?.let { addView(it, lp) }
        p.addView(this, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        invalidate()
    }

}