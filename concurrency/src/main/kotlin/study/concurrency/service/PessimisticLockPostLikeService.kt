package study.concurrency.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.concurrency.domain.postlike.PostLikeRepository

@Service
class PessimisticLockPostLikeService(
    private val postLikeRepository: PostLikeRepository,
) {
    @Transactional
    fun increase(postLikeId: Long) {
        val postLike = postLikeRepository.findByPostIdWithPessimisticLock(postLikeId)
        postLike.increase()
    }
}
