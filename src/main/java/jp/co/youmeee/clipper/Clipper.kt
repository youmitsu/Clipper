package jp.co.youmeee.clipper

import android.view.ViewGroup
import android.view.Window


object Clipper {

    private fun initQueueDispatcher(
        clipItems: List<ClipperLayout>,
        window: Window,
        parent: ViewGroup
    ): ClipperQueueDispatcher = ClipperQueueDispatcherImpl(window, parent).apply { addAll(clipItems) }

    fun createBundleExecutor(parent: ViewGroup, window: Window, vararg clipItem: ClipperLayout): ClipBundleExecutor =
        ClipBundleExecutor(
            Clipper.initQueueDispatcher(
                clipItem.toList(),
                window,
                parent
            )
        )

    fun createBundleExecutor(parent: ViewGroup, window: Window, clipItems: List<ClipperLayout>): ClipBundleExecutor =
        ClipBundleExecutor(
            Clipper.initQueueDispatcher(
                clipItems,
                window,
                parent
            )
        )
}

class ClipBundleExecutor(private val queueDispatcher: ClipperQueueDispatcher) {

    fun execute() {
        queueDispatcher.execute()
    }
}