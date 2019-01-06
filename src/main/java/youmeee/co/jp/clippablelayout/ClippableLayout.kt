package youmeee.co.jp.clippablelayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class ClippableLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    private val clippableView: ClippableView = ClippableView(context, attributeSet, defStyle)

    init {
        val view = View(context, attributeSet, defStyle)
        view.setBackgroundColor(resources.getColor(R.color.error_color_material_dark))
        addView(view)
    }
}