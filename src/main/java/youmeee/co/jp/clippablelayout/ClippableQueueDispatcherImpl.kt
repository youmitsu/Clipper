package youmeee.co.jp.clippablelayout

import android.view.ViewGroup
import android.view.Window
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class ClippableQueueDispatcherImpl(val window: Window, val parent: ViewGroup) : ClippableQueueDispatcher {

    private val clippableLayoutQueue: Queue<ClippableItem> = LinkedBlockingQueue()

    override fun add(clippableLayout: ClippableItem) {
        clippableLayout.clippableLayout.queueDispatcher = this
        clippableLayoutQueue.add(clippableLayout)
    }

    fun addAll(vararg clippableLayoutList: ClippableItem) {
        clippableLayoutList.forEach { add(it) }
    }

    fun addAll(clippableLayoutList: List<ClippableItem>) {
        clippableLayoutList.forEach { add(it) }
    }

    override fun execute() {
        clippableLayoutQueue.poll()?.clippableLayout?.showOverlay(window, parent)
    }

    override fun onDetachedClippableView() {
        execute()
    }
}