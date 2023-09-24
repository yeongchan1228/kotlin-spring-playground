package study.concurrency.domain.postlike

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface NamedLockRepository :
    JpaRepository<PostLike, Long> { // 실제 사용할 때는, PostView와 매핑해서 사용하는 것이 아닌, 별도의 jdbc를 사용해서 Metadata에 접근 및 사용해야 한다..
    @Query(value = "select get_lock(:key, :timeout)", nativeQuery = true)
    fun getLock(key: String, timeout: String)

    @Query(value = "select release_lock(:key)", nativeQuery = true)
    fun releaseLock(key: String)
}
