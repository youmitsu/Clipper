package youmeee.co.jp.clippablelayout

interface ClippableQueueDispatcher {
    fun add(clippableLayout: ClippableItem)
    fun execute()
    fun onDetachedClippableView()
}