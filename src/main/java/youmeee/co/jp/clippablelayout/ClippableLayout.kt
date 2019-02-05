package youmeee.co.jp.clippablelayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * ClippableLayout
 */
class ClippableLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    var clippableView: ClippableView? = null
    var descView: View? = null

    fun showOverlay(parent: ViewGroup) {
        setOnClickListener {
            removeAllViews()
        }
        clippableView?.showOverlay(this)
        descView?.let { addView(it) }
        parent.addView(this, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        invalidate()
    }

    fun clear() = clippableView?.clear()
}