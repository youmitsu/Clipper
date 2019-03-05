package youmeee.co.jp.clippablelayout

import android.animation.ObjectAnimator
import android.content.Context

class ClippableItem private constructor(context: Context) {

    constructor(context: Context, clipEntry: ClipEntry) : this(context, listOf(clipEntry))

    constructor(context: Context, clipEntries: List<ClipEntry>) : this(context, clipEntries, null)

    constructor(context: Context, clipEntries: List<ClipEntry>, _descView: DescriptionView?) : this(context) {
        clippableLayout.apply {
            this.clippableView = ClippableView(context).also { it.setClipViews(clipEntries) }
            this.descView = _descView
        }
        ObjectAnimator.ofFloat(clippableLayout, "alpha", 0f, 1f).apply {
            duration = 1500
            start()
        }
    }

    internal val clippableLayout = ClippableLayout(context)
}