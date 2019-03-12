package jp.co.youmeee.clipper

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import youmeee.co.jp.clipper.R

/**
 * ClipperLayout
 */
class ClipperLayout private constructor(context: Context) : FrameLayout(context) {

    constructor(context: Context, clipEntry: ClipEntry) : this(context, listOf(clipEntry))

    constructor(context: Context, clipEntries: List<ClipEntry>) : this(context, clipEntries, null)

    constructor(context: Context, clipEntries: List<ClipEntry>, _descView: DescriptionView?) : this(context) {
        this.clipperView = ClipperView(context).also { it.setClipViews(clipEntries) }
        this.descView = _descView
    }

    var clipperView: ClipperView? = null
    private var descView: DescriptionView? = null
    var backGroundColor: Int = R.color.default_gray
    var queueDispatcher: ClipperQueueDispatcher? = null
    var clipAnimator: ClipAnimator? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        clipAnimator?.animateClip(this)
    }

    fun clip(container: ViewGroup, window: Window) {
        setOnClickListener {
            (parent as? ViewGroup)?.removeView(this)
            queueDispatcher?.onDetachedClippableView()
        }
        clipperView?.showOverlay(this, window, backGroundColor)
        descView?.let { addView(it.descView, it.lp) }
        container.addView(this, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
    }
}

class DescriptionView(
    val descView: View,
    val lp: FrameLayout.LayoutParams
)