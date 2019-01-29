package youmeee.co.jp.clippablelayout

import android.content.Context
import android.util.AttributeSet
import android.view.Window
import android.widget.FrameLayout

class ClippableLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    val clippableView: ClippableView = ClippableView(context, attributeSet, defStyle)
    private var backGroundColorResId: Int = 0
    private var transitionEnabled = true

    init {
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.ClippableLayout)
        try {
            backGroundColorResId = a.getResourceId(
                R.styleable.ClippableLayout_backgroundColor,
                R.color.default_gray
            )
            //TODO: transitionの実装
            transitionEnabled = a.getBoolean(R.styleable.ClippableLayout_transitionEnabled, true)
        } finally {
            a.recycle()
        }
    }

    fun setWindow(window: Window) {
        clippableView.window = window
    }

    fun setClipViews(entryList: List<ClipEntry>) = clippableView.setClipViews(entryList)

    fun setClipViews(vararg entries: ClipEntry) = clippableView.setClipViews(*entries)

    fun showOverlay() {
        clippableView.setBackGroundColor(resources.getColor(backGroundColorResId))
        removeView(clippableView)
        addView(clippableView)
        invalidate()
    }

    fun clear() = clippableView.clear()
}