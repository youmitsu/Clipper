package youmeee.co.jp.clippablelayout

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.Window

class ClippableView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attributeSet, defStyle) {

    private val backGroundPaint = Paint()
    private val clipPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val porterDuffXferMode: PorterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

    private var clipList: MutableList<ClipEntry> = mutableListOf()
    private val decorRect = Rect()
    var window: Window? = null

    private var listener = { v: View -> visibility = GONE }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setOnClickListener { v -> listener(v) }
        window?.let { w ->
            setLayerType(View.LAYER_TYPE_HARDWARE, null)

            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backGroundPaint)
            clipPaint.xfermode = porterDuffXferMode

            w.decorView.getWindowVisibleDisplayFrame(decorRect)
            clipList.forEach { it.clip(canvas, clipPaint, decorRect) }
        }
    }

    fun setBackGroundColor(color: Int) {
        backGroundPaint.color = color
    }

    /**
     * @param entry: It's object to be a clipped entry.
     *
     * Method to add this entry object.
     */
    fun setClipView(entry: ClipEntry) {
        clipList.add(entry)
    }

    /**
     * @param entryList: It's object to be a list of clipped entries.
     *
     * Method to add a list of these entries objects composed List.
     */
    fun setClipViews(entryList: List<ClipEntry>) {
        entryList.forEach {
            clipList.add(it)
        }
    }

    /**
     * @param entries: There are multiple objects to be clipped.
     *
     * Method to add a list of these entries objects.
     */
    fun setClipViews(vararg entries: ClipEntry) {
        setClipViews(entries.toList())
    }

    fun setClickListener(listener: (v: View) -> Unit) {
        this.listener = listener
    }

    fun clear() = clipList.clear()

    fun showOverlay(parent: ViewGroup) {
        parent.removeView(this)
        parent.addView(this)
        invalidate()
    }
}