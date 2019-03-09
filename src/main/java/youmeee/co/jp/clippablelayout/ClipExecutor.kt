package youmeee.co.jp.clippablelayout

import android.view.ViewGroup
import android.view.Window

class ClipExecutor(private val queueDispatcher: ClippableQueueDispatcher) {
    fun execute() {
        queueDispatcher.execute()
    }
}

object ClipExecutorFactory {

    private fun initQueueDispatcher(
        clipItems: List<ClippableLayout>,
        window: Window,
        parent: ViewGroup
    ): ClippableQueueDispatcher = ClippableQueueDispatcherImpl(window, parent).apply { addAll(clipItems) }

    fun create(parent: ViewGroup, window: Window, vararg clipItem: ClippableLayout): ClipExecutor =
        ClipExecutor(initQueueDispatcher(clipItem.toList(), window, parent))

    fun create(parent: ViewGroup, window: Window, clipItems: List<ClippableLayout>): ClipExecutor =
        ClipExecutor(initQueueDispatcher(clipItems, window, parent))
}