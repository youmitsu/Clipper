package youmeee.co.jp.clippablelayout

import android.content.Context
import android.view.View

class ClippableItem(context: Context) {

    constructor(context: Context, clipEntries: List<ClipEntry>) : this(context, clipEntries, null)

    constructor(context: Context, clipEntries: List<ClipEntry>, _descView: View?) : this(context) {
        _clippableView.setClipViews(clipEntries)
        clippableLayout.apply {
            clippableView = _clippableView
            descView = _descView
        }
    }

    private val _clippableView = ClippableView(context)
    internal val clippableLayout = ClippableLayout(context)
}