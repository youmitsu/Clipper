package jp.co.youmeee.clipper

import android.view.ViewGroup
import android.view.Window

object Clipper {

    private fun initQueueDispatcher(
        clipItems: List<ClipperLayout>,
        window: Window,
        parent: ViewGroup,
        clipAnimator: ClipAnimator
    ): ClipperQueueDispatcher = ClipperQueueDispatcherImpl(window, parent, clipAnimator).apply { addAll(clipItems) }

    fun createBundleExecutor(
        parent: ViewGroup,
        window: Window,
        clipAnimator: ClipAnimator,
        vararg clipItem: ClipperLayout
    ): ClipBundleExecutor =
        ClipBundleExecutor(
            Clipper.initQueueDispatcher(
                clipItem.toList(),
                window,
                parent,
                clipAnimator
            )
        )

    fun createBundleExecutor(
        parent: ViewGroup,
        window: Window,
        clipAnimator: ClipAnimator,
        clipItems: List<ClipperLayout>
    ): ClipBundleExecutor =
        ClipBundleExecutor(
            Clipper.initQueueDispatcher(
                clipItems,
                window,
                parent,
                clipAnimator
            )
        )
}

class ClipBundleExecutor(private val queueDispatcher: ClipperQueueDispatcher) {

    fun execute() {
        queueDispatcher.execute()
    }
}