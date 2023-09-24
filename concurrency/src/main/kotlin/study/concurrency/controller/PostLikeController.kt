package study.concurrency.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import study.concurrency.facade.LettuceLockPostLikeFacade
import study.concurrency.facade.NamedLockPostLikeFacade
import study.concurrency.facade.RedissonLockPostLikeFacade
import study.concurrency.service.OptimisticLockPostLikeService
import study.concurrency.service.PessimisticLockPostLikeService
import study.concurrency.service.PostLikeService

@RestController
@RequestMapping("/api/v1/post-likes")
class PostLikeController(
    private val poseLikeService: PostLikeService,
    private val optimisticLockPostLikeService: OptimisticLockPostLikeService,
    private val pessimisticLockPostLikeService: PessimisticLockPostLikeService,
    private val namedLockPostLikeFacade: NamedLockPostLikeFacade,
    private val lettuceLockPostLikeFacade: LettuceLockPostLikeFacade,
    private val redissonLockPostLikeFacade: RedissonLockPostLikeFacade,
) {
    @PostMapping("/{postLikeId}/increase")
    fun increasePostLike(
        @PathVariable("postLikeId") postLikeId: Long,
    ): ResponseEntity<Void> {
        redissonLockPostLikeFacade.increase(postLikeId)
        return ResponseEntity.noContent().build()
    }
}
