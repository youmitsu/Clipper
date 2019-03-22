package jp.co.youmeee.clipper

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes

object ClipperLayoutInflater {

    fun inflate(context: Context, @LayoutRes layoutId: Int): ClipperLayout {
        val layout = LayoutInflater.from(context).inflate(layoutId, null)
        lateinit var clipperLayout: ClipperLayout
        try {
            clipperLayout = layout as ClipperLayout
        } catch (e: ClassCastException) {
            throw ClassCastException("Cannot cast $layout to ClipperLayout")
        }
        return clipperLayout
    }
}

class ClipperBundleExecutor(private val queueDispatcher: ClipperQueueDispatcher) {

    companion object {
        fun create(
            parent: ViewGroup,
            window: Window,
            clipAnimator: ClipAnimator?,
            vararg clipItem: ClipperLayout
        ): ClipperBundleExecutor =
            ClipperBundleExecutor(
                initQueueDispatcher(
                    clipItem.toList(),
                    window,
                    parent,
                    clipAnimator
                )
            )

        fun create(
            parent: ViewGroup,
            window: Window,
            clipAnimator: ClipAnimator?,
            clipItems: List<ClipperLayout>
        ): ClipperBundleExecutor =
            ClipperBundleExecutor(
                initQueueDispatcher(
                    clipItems,
                    window,
                    parent,
                    clipAnimator
                )
            )

        private fun initQueueDispatcher(
            clipItems: List<ClipperLayout>,
            window: Window,
            parent: ViewGroup,
            clipAnimator: ClipAnimator?
        ): ClipperQueueDispatcher = ClipperQueueDispatcherImpl(window, parent, clipAnimator).apply { addAll(clipItems) }
    }

    fun execute() {
        queueDispatcher.execute()
    }
}