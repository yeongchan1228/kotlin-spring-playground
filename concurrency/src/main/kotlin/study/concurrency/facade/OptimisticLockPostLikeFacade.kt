package study.concurrency.facade


import org.springframework.stereotype.Component
import study.concurrency.service.OptimisticLockPostLikeService

@Component
class OptimisticLockPostLikeFacade(
    private val optimisticLockPostLikeService: OptimisticLockPostLikeService,
) {
    fun increase(postLikeVersionId: Long) {
        while (true) {
            try {
                optimisticLockPostLikeService.increase(postLikeVersionId)
                break
            } catch (ex: RuntimeException) {
                Thread.sleep(50)
            }
        }
    }
}
