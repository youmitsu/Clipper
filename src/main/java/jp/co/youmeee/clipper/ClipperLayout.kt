package jp.co.youmeee.clipper

import android.content.Context
import android.util.AttributeSet
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
class ClipperLayout : FrameLayout {

    constructor(context: Context) : this(context, descView = null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(
        context: Context,
        descView: DescriptionView? = null,
        backGroundColor: Int = R.color.clipper_default_gray
    ) : super(context) {
        this.descView = descView
        this.backGroundColor = backGroundColor
    }

    private var descView: DescriptionView? = null
    private var backGroundColor: Int = R.color.clipper_default_gray
    private var clipAnimator: ClipAnimator? = null

    internal var clipperView: ClipperView = ClipperView(context)
    private var clipEntries: MutableList<ClipEntry> = mutableListOf()
    internal var queueDispatcher: ClipperQueueDispatcher? = null

    init {
        isClickable = true
    }

    @SuppressWarnings
    fun addEntry(clipEntry: ClipEntry): ClipperLayout {
        clipEntries.add(clipEntry)
        return this
    }

    @SuppressWarnings
    fun addEntries(vararg clipEntry: ClipEntry): ClipperLayout {
        this.addEntries(clipEntry.toList())
        return this
    }

    @SuppressWarnings
    fun addEntries(entries: List<ClipEntry>): ClipperLayout {
        for (entry in entries) {
            clipEntries.add(entry)
        }
        return this
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        clipAnimator?.animateClipStart(this, null)
    }

    @SuppressWarnings
    fun clip(container: ViewGroup, window: Window, animator: ClipAnimator? = null) {
        clipAnimator = animator
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
        removeAllViews()
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