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
class ClipperLayout constructor(context: Context) : FrameLayout(context) {

    internal var clipperView: ClipperView = ClipperView(context)
    var clipEntries: MutableList<ClipEntry> = mutableListOf()
    var descView: DescriptionView? = null
    var backGroundColor: Int = R.color.default_gray
    var clipAnimator: ClipAnimator? = null

    internal var queueDispatcher: ClipperQueueDispatcher? = null

    fun add(clipEntry: ClipEntry) {
        clipEntries.add(clipEntry)
    }

    fun add(vararg clipEntry: ClipEntry) {
        this.add(clipEntry.toList())
    }

    fun add(entries: List<ClipEntry>) {
        for (entry in entries) {
            clipEntries.add(entry)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        clipAnimator?.animateClip(this)
    }

    fun clip(container: ViewGroup, window: Window) {
        clipperView.setClipViews(clipEntries)
        clipperView.showOverlay(this, window, backGroundColor)
        descView?.let { addView(it.descView, it.lp) }
        container.addView(this, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        setOnClickListener {
            (parent as? ViewGroup)?.removeView(this)
            queueDispatcher?.onDetachedClippableView()
        }
    }
}

class DescriptionView(
    val descView: View,
    val lp: FrameLayout.LayoutParams
)