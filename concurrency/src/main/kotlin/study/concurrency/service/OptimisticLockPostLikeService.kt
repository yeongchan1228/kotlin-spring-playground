package study.concurrency.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.concurrency.domain.postlikeversion.PostLikeVersionRepository

@Service
class OptimisticLockPostLikeService(
    private val postLikeVersionRepository: PostLikeVersionRepository,
) {
    @Transactional
    fun increase(postLikeVersionId: Long) {
        val postLike = postLikeVersionRepository.findByPostIdWithOptimisticLock(postLikeVersionId)
        postLike.increase()
    }
}
