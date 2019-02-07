package youmeee.co.jp.clippablelayout

import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class ClippableQueueDispatcherImpl : ClippableQueueDispatcher {

    private val clippableLayoutQueue: Queue<ClippableLayout> = LinkedBlockingQueue()

    fun add(clippableLayout: ClippableLayout) {
        clippableLayout.queueDispatcher = this
        clippableLayoutQueue.add(clippableLayout)
    }

    fun addAll(vararg clippableLayoutList: ClippableLayout) {
        clippableLayoutList.forEach { add(it) }
    }

    fun addAll(clippableLayoutList: MutableList<ClippableLayout>) {
        clippableLayoutList.forEach { add(it) }
    }

    fun execute() {
        clippableLayoutQueue.poll()?.let { it.showOverlay() }
    }

    override fun onDetachedClippableView() {
        execute()
    }
}