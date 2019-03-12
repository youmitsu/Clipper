package jp.co.youmeee.clipper

interface ClipperQueueDispatcher {
    fun add(clipperLayout: ClipperLayout)
    fun execute()
    fun onDetachedClippableView()
}