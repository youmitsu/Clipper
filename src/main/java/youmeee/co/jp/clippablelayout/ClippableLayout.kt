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

    val view = View(context, attributeSet, defStyle)

    fun showOverlay() {
        view.setBackgroundColor(resources.getColor(R.color.primary_text_default_material_light))
        addView(view)
        invalidate()
    }

}