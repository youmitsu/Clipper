package youmeee.co.jp.clippablelayout

import android.content.Context

class ClippableItem private constructor(context: Context) {

    constructor(context: Context, clipEntry: ClipEntry) : this(context, listOf(clipEntry))

    constructor(context: Context, clipEntries: List<ClipEntry>) : this(context, clipEntries, null)

    constructor(context: Context, clipEntries: List<ClipEntry>, _descView: DescriptionView?) : this(context) {
        clippableLayout.apply {
            this.clippableView = ClippableView(context).also { it.setClipViews(clipEntries) }
            this.descView = _descView
        }
    }

    internal val clippableLayout = ClippableLayout(context)
}