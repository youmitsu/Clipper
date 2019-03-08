package youmeee.co.jp.clippablelayout

interface ClippableQueueDispatcher {
    fun add(clippableLayout: ClippableLayout)
    fun execute()
    fun onDetachedClippableView()
}