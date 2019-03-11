package youmeee.co.jp.clipper

interface ClipperQueueDispatcher {
    fun add(clipperLayout: ClipperLayout)
    fun execute()
    fun onDetachedClippableView()
}