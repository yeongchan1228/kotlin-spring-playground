package study.concurrency.facade


import org.springframework.stereotype.Component
import study.concurrency.domain.postlike.NamedLockRepository
import study.concurrency.service.PostLikeService

private const val DEFAULT_POST_VIEW_NAMED_LOCK_TIMEOUT_MS = 3000L

@Component
class NamedLockPostLikeFacade(
    private val namedLockRepository: NamedLockRepository,
    private val postLikeService: PostLikeService,
) {
    fun increase(postLikeId: Long) {
        try {
            namedLockRepository.getLock(postLikeId.toString(), DEFAULT_POST_VIEW_NAMED_LOCK_TIMEOUT_MS.toString())
            postLikeService.increaseWithNamedLock(postLikeId)
        } finally {
            namedLockRepository.releaseLock(postLikeId.toString())
        }
    }
}
