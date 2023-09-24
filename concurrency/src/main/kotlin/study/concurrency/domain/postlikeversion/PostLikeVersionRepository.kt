package study.concurrency.domain.postlikeversion

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostLikeVersionRepository : JpaRepository<PostLikeVersion, Long> {
    @Lock(LockModeType.OPTIMISTIC)
    @Query("select plv from PostLikeVersion plv where plv.id = :postLikeVersionId")
    fun findByPostIdWithOptimisticLock(postLikeVersionId: Long): PostLikeVersion
}
