package jp.co.youmeee.clipper

import android.view.ViewGroup
import android.view.Window
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class ClipperQueueDispatcherImpl(
    private val window: Window,
    private val parent: ViewGroup,
    private val clipAnimator: ClipAnimator?
) : ClipperQueueDispatcher {

    private val clipperLayoutQueue: Queue<ClipperLayout> = LinkedBlockingQueue()

    override fun add(clipperLayout: ClipperLayout) {
        clipperLayout.queueDispatcher = this
        clipperLayoutQueue.add(clipperLayout)
    }

    fun addAll(vararg clipperLayoutList: ClipperLayout) {
        clipperLayoutList.forEach { add(it) }
    }

    fun addAll(clipperLayoutList: List<ClipperLayout>) {
        clipperLayoutList.forEach { add(it) }
    }

    override fun execute() {
        clipperLayoutQueue.poll()?.clip(parent, window, clipAnimator)
    }

    override fun onDetachedClippableView() {
        execute()
    }
}