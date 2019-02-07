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
class ClippableLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyle: Int = 0,
        window: Window
    ) : this(context, attributeSet, defStyle) {
        this.window = window
    }

    private lateinit var window: Window
    var clippableView: ClippableView? = null
    var descView: View? = null
    var backGroundColor: Int = R.color.default_gray

    fun showOverlay(parent: ViewGroup) {
        setOnClickListener {
            removeAllViews()
        }
        clippableView?.showOverlay(this, window, backGroundColor)
        descView?.let { addView(it) }
        parent.addView(this, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        invalidate()
    }

    fun clear() = clippableView?.clear()
}