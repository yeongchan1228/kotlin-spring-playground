package study.concurrency.facade


import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import study.concurrency.service.PostLikeService
import java.util.concurrent.TimeUnit

@Component
class RedissonLockPostLikeFacade(
    private val redissonClient: RedissonClient,
    private val postLikeService: PostLikeService,
) {

    fun increase(postId: Long) {
        val lock = redissonClient.getLock(postId.toString())

        try {
            val available = lock.tryLock(10, 1, TimeUnit.SECONDS)
            if (!available) {
                println("Lock 획득 실패")
                return
            }

            postLikeService.increase(postId)
        } finally {
            lock.unlock()
        }
    }
}
