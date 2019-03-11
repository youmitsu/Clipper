package youmeee.co.jp.clipper

interface ClippableQueueDispatcher {
    fun add(clippableLayout: ClippableLayout)
    fun execute()
    fun onDetachedClippableView()
}