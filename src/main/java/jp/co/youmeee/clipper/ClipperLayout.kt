package jp.co.youmeee.clipper

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
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
    var backGroundColor: Int = R.color.clipper_default_gray
    var clipAnimator: ClipAnimator? = null

    internal var queueDispatcher: ClipperQueueDispatcher? = null

    init {
        isClickable = true
    }

    fun add(clipEntry: ClipEntry) = clipEntries.add(clipEntry)

    fun add(vararg clipEntry: ClipEntry) = this.add(clipEntry.toList())

    fun add(entries: List<ClipEntry>) {
        for (entry in entries) {
            clipEntries.add(entry)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        clipAnimator?.animateClipStart(this, null)
    }

    fun clip(container: ViewGroup, window: Window) {
        clipperView.setClipViews(clipEntries)
        clipperView.showOverlay(this, window, backGroundColor)
        if (descView != null) {
            setOnDismissEvent(descView!!.itemToDismiss ?: this)
            addView(descView!!.descView, descView!!.lp)
        } else {
            setOnDismissEvent(this)
        }
        container.addView(this, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
    }

    private fun setOnDismissEvent(v: View) = v.setOnClickListener {
        if (clipAnimator == null) {
            dismiss().invoke()
        } else {
            clipAnimator?.animateClipEnd(this, dismiss())
        }
    }

    private fun dismiss(): () -> Unit = {
        (parent as? ViewGroup)?.removeView(this)
        queueDispatcher?.onDetachedClippableView()
    }

}

class DescriptionView(
    val descView: View,
    itemIdToDismiss: Int? = null,
    layoutWidth: Int = WRAP_CONTENT,
    layoutHeight: Int = WRAP_CONTENT,
    topMargin: Int = 0,
    leftMargin: Int = 0,
    bottomMargin: Int = 0,
    rightMargin: Int = 0,
    gravity: Int = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
) {
    internal var itemToDismiss: View? = null
    val lp = FrameLayout.LayoutParams(layoutWidth, layoutHeight).also {
        it.gravity = gravity
        it.topMargin = topMargin
        it.leftMargin = leftMargin
        it.bottomMargin = bottomMargin
        it.rightMargin = rightMargin
    }

    init {
        itemIdToDismiss?.let {
            try {
                itemToDismiss = descView.findViewById(itemIdToDismiss)
            } catch (e: Exception) {
                throw Exception("Cannot find the component for id '$itemIdToDismiss'")
            }
        }
    }
}