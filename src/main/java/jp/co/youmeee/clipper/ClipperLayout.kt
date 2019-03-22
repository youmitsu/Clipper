package jp.co.youmeee.clipper

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import youmeee.co.jp.clipper.R

/**
 * ClipperLayout
 */
class ClipperLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var backGroundColor: Int = R.color.clipper_default_gray
    private var clipAnimator: ClipAnimator? = null

    private var itemIdToDismiss: Int = 0
    internal var clipperView: ClipperView = ClipperView(context)
    private var clipEntries: MutableList<ClipEntry> = mutableListOf()
    internal var queueDispatcher: ClipperQueueDispatcher? = null

    init {
        isClickable = true
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.ClipperLayout
        )
        try {
            itemIdToDismiss = a.getResourceId(R.styleable.ClipperLayout_dismissTriggerItemId, 0)
        } finally {
            a.recycle()
        }
    }

    @SuppressWarnings
    fun addEntry(clipEntry: ClipEntry): ClipperLayout {
        clipEntries.add(clipEntry)
        return this
    }

    @SuppressWarnings("unused")
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
        if (itemIdToDismiss != 0) {
            val nextTriggerView = try {
                findViewById<View>(itemIdToDismiss)
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Cannot find view by $itemIdToDismiss")
            }
            setOnDismissEvent(nextTriggerView)
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