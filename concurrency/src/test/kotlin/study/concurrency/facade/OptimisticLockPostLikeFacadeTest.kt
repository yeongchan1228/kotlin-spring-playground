package study.concurrency.facade

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import study.concurrency.domain.postlikeversion.PostLikeVersion
import study.concurrency.domain.postlikeversion.PostLikeVersionRepository
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OptimisticLockPostLikeFacadeTest(
    private val optimisticLockPostLikeFacade: OptimisticLockPostLikeFacade,
    private val postLikeVersionRepository: PostLikeVersionRepository,
) {
    @AfterEach
    fun tearDown() {
        postLikeVersionRepository.deleteAllInBatch()
    }

    @Test
    fun `멀티 스레드 환경에서 동시에 총 100개의 좋아요 증가 요청이 들어오면, 좋아요 수는 100이다`() {
        // given
        val postLike = postLikeVersionRepository.save(PostLikeVersion(postId = 1L, likeCount = 0L))
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(20)
        val countDownLatch = CountDownLatch(threadCount)

        // when
        repeat(threadCount) {
            executorService.submit {
                try {
                    optimisticLockPostLikeFacade.increase(postLike.id)
                } finally {
                    countDownLatch.countDown()
                }
            }
        }

        countDownLatch.await()

        val findPostView = postLikeVersionRepository.getReferenceById(postLike.id)

        // then
        Assertions.assertThat(findPostView.likeCount).isEqualTo(100)
    }
}


