package study.concurrency.service

class TransactionPostLikeService(
    private val postLikeService: PostLikeService,
) {

    fun increase(postId: Long) {
        startTransaction()

        postLikeService.increase(postId)

        endTransaction()
    }

    private fun startTransaction() = println("Start Transaction")

    private fun endTransaction() = println("End Transaction")
}
