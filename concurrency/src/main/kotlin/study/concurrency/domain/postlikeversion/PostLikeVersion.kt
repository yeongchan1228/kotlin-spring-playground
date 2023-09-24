package study.concurrency.domain.postlikeversion

import jakarta.persistence.*


private const val DEFAULT_INCREASED_LIKE_COUNT = 1

@Entity
class PostLikeVersion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val postId: Long,

    @Version
    val version: Long = 0L,

    likeCount: Long,
) {
    @Column(nullable = false)
    var likeCount: Long = likeCount
        private set

    fun increase() {
        this.likeCount += DEFAULT_INCREASED_LIKE_COUNT
    }
}
