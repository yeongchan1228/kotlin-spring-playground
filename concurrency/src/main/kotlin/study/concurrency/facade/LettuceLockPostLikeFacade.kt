package study.concurrency.facade

import org.springframework.stereotype.Component
import study.concurrency.domain.postlike.RedisLikeRepository
import study.concurrency.service.PostLikeService

@Component
class LettuceLockPostLikeFacade(
    private val redisLikeRepository: RedisLikeRepository,
    private val postLikeService: PostLikeService,
) {

    fun increase(postLikeId: Long) {
        while (!redisLikeRepository.lock(postLikeId)) {
            Thread.sleep(100)
        }

        try {
            postLikeService.increase(postLikeId)
        } finally {
            redisLikeRepository.unlock(postLikeId)
        }
    }
}
