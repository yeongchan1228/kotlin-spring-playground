package study.concurrency.domain.postlike

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostLikeRepository : JpaRepository<PostLike, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select pl from PostLike pl where pl.id = :postLikeId")
    fun findByPostIdWithPessimisticLock(postLikeId: Long): PostLike

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select pl from PostLike pl where pl.id = :postLikeId")
    fun findByPostIdWithOptimisticLock(postLikeId: Long): PostLike
}
