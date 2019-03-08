package youmeee.co.jp.clippablelayout

import android.view.ViewGroup
import android.view.Window
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class ClippableQueueDispatcherImpl(val window: Window, val parent: ViewGroup) : ClippableQueueDispatcher {

    private val clippableLayoutQueue: Queue<ClippableLayout> = LinkedBlockingQueue()

    override fun add(clippableLayout: ClippableLayout) {
        clippableLayout.queueDispatcher = this
        clippableLayoutQueue.add(clippableLayout)
    }

    fun addAll(vararg clippableLayoutList: ClippableLayout) {
        clippableLayoutList.forEach { add(it) }
    }

    fun addAll(clippableLayoutList: List<ClippableLayout>) {
        clippableLayoutList.forEach { add(it) }
    }

    override fun execute() {
        clippableLayoutQueue.poll()?.showOverlay(window, parent)
    }

    override fun onDetachedClippableView() {
        execute()
    }
}