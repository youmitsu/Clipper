package youmeee.co.jp.clippablelayout

import android.view.ViewGroup
import android.view.Window

class ClipExecutor(private val queueDispatcher: ClippableQueueDispatcher) : AbstractClipExecutor() {
    override fun execute() {
        queueDispatcher.execute()
    }
}

object ClipExecutorFactory {

    private fun initQueueDispatcher(
        clipItems: List<ClippableItem>,
        window: Window,
        parent: ViewGroup
    ): ClippableQueueDispatcher = ClippableQueueDispatcherImpl(window, parent).apply { addAll(clipItems) }

    fun create(vararg clipItem: ClippableItem, window: Window, parent: ViewGroup): ClipExecutor =
        ClipExecutor(initQueueDispatcher(clipItem.toList(), window, parent))

    fun create(clipItems: List<ClippableItem>, window: Window, parent: ViewGroup): ClipExecutor =
        ClipExecutor(initQueueDispatcher(clipItems, window, parent))
}