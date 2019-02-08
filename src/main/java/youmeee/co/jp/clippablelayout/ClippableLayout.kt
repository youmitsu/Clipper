package youmeee.co.jp.clippablelayout

import android.content.Context
import android.util.AttributeSet
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

    var window: Window? = null
    var parent: ViewGroup? = null
    var clippableView: ClippableView? = null
    var descView: View? = null
    var backGroundColor: Int = R.color.default_gray
    var queueDispatcher: ClippableQueueDispatcher? = null

    fun showOverlay() {
        window?.let { w ->
            parent?.let { p ->
                setOnClickListener {
                    removeAllViews()
                    queueDispatcher?.onDetachedClippableView()
                }
                clippableView?.showOverlay(this, w, backGroundColor)
                descView?.let { addView(it) }
                p.addView(this, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
                invalidate()
            }
        }
    }

    fun clear() = clippableView?.clear()
}