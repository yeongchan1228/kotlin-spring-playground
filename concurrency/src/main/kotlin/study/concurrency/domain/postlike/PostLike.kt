package study.concurrency.domain.postlike

import jakarta.persistence.*

private const val DEFAULT_INCREASED_LIKE_COUNT = 1

@Entity
class PostLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val postId: Long,

    likeCount: Long,
) {
    @Column(nullable = false)
    var likeCount: Long = likeCount
        private set

    fun increase() {
        this.likeCount += DEFAULT_INCREASED_LIKE_COUNT
    }
}
