package youmeee.co.jp.clippablelayout

import android.content.Context
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout

/**
 * ClippableLayout
 */
class ClippableLayout private constructor(context: Context) : FrameLayout(context) {

    constructor(context: Context, clipEntry: ClipEntry) : this(context, listOf(clipEntry))

    constructor(context: Context, clipEntries: List<ClipEntry>) : this(context, clipEntries, null)

    constructor(context: Context, clipEntries: List<ClipEntry>, _descView: DescriptionView?) : this(context) {
        this.clippableView = ClippableView(context).also { it.setClipViews(clipEntries) }
        this.descView = _descView
    }

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