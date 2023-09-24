package study.concurrency.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import study.concurrency.domain.postlike.PostLike
import study.concurrency.domain.postlike.PostLikeRepository
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PostLikeServiceTest(
    private val postLikeService: PostLikeService,
    private val postLikeRepository: PostLikeRepository,
) {
    @AfterEach
    fun tearDown() {
        postLikeRepository.deleteAllInBatch()
    }

    @Test
    fun `좋아요가 정상적으로 1 증가한다`() {
        // given
        val postLike = postLikeRepository.save(PostLike(postId = 1L, likeCount = 0L))

        // when
        postLikeService.increase(postLike.id)
        val findpostLike = postLikeRepository.getReferenceById(postLike.id)

        // then
        assertThat(findpostLike.likeCount).isEqualTo(1)
    }

    @Test
    fun `멀티 스레드 환경에서 동시에 총 100개의 좋아요 증가 요청이 들어오면, 좋아요 수는 100이다`() {
        // given
        val postLike = postLikeRepository.save(PostLike(postId = 1L, likeCount = 0L))
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(20)
        val countDownLatch = CountDownLatch(threadCount)

        // when
        repeat(threadCount) {
            executorService.submit {
                try {
                    postLikeService.increaseWithSynchronized(postLike.id)
                } finally {
                    countDownLatch.countDown()
                }
            }
        }

        countDownLatch.await() // countDownLatch로 설정한 값이 모두 0이 될 때까지 대기

        val findViewCount = postLikeRepository.getReferenceById(postLike.id)

        // then
        assertThat(findViewCount.likeCount).isEqualTo(100)
    }
}
