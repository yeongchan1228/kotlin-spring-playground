package study.concurrency.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import study.concurrency.domain.postlike.PostLikeRepository

@Service
class PostLikeService(
    private val postLikeRepository: PostLikeRepository,
) {
    @Transactional
    fun increase(postLikeId: Long) {
        val postLike = postLikeRepository.findById(postLikeId).orElseThrow {
            NoSuchElementException("PostLike not found")
        }
        postLike.increase()
    }

    @Synchronized
    fun increaseWithSynchronized(postLikeId: Long) {
        val postLike = postLikeRepository.findById(postLikeId).orElseThrow {
            NoSuchElementException("postLike not found")
        }
        postLike.increase()
        postLikeRepository.saveAndFlush(postLike)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun increaseWithNamedLock(postLikeId: Long) {
        val postLike = postLikeRepository.findById(postLikeId).orElseThrow {
            NoSuchElementException("postLike not found")
        }
        postLike.increase()
    }
}
