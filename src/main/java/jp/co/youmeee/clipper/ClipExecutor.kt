package jp.co.youmeee.clipper

import android.view.ViewGroup
import android.view.Window

class ClipExecutor(private val queueDispatcher: ClipperQueueDispatcher) {

    fun execute() {
        queueDispatcher.execute()
    }
}

object ClipExecutorFactory {

    private fun initQueueDispatcher(
        clipItems: List<ClipperLayout>,
        window: Window,
        parent: ViewGroup
    ): ClipperQueueDispatcher = ClipperQueueDispatcherImpl(window, parent).apply { addAll(clipItems) }

    fun create(parent: ViewGroup, window: Window, vararg clipItem: ClipperLayout): ClipExecutor =
        ClipExecutor(
            initQueueDispatcher(
                clipItem.toList(),
                window,
                parent
            )
        )

    fun create(parent: ViewGroup, window: Window, clipItems: List<ClipperLayout>): ClipExecutor =
        ClipExecutor(
            initQueueDispatcher(
                clipItems,
                window,
                parent
            )
        )
}