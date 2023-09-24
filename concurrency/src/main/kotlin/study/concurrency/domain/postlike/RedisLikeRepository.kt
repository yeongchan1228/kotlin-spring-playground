package study.concurrency.domain.postlike

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

private const val DEFAULT_REDIS_LOCK_TIMEOUT_MS = 3000L

@Component
class RedisLikeRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {
    // setIfAbsent를 사용하여 키를 덮어쓰지 않고 데이터를 추가한다.
    fun lock(key: Long): Boolean =
        redisTemplate.opsForValue().setIfAbsent(
            generateKey(key), "lock", Duration.ofMillis(
                DEFAULT_REDIS_LOCK_TIMEOUT_MS
            )
        ) ?: false

    fun unlock(key: Long) {
        redisTemplate.delete(generateKey(key))
    }

    private fun generateKey(key: Long): String = key.toString()
}
