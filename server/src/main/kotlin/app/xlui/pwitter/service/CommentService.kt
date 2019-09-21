package app.xlui.pwitter.service

import app.xlui.pwitter.entity.db.Comment
import app.xlui.pwitter.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService @Autowired constructor(
        val commentRepository: CommentRepository
) {
    fun save(comment: Comment) = commentRepository.save(comment)

    fun save(comments: Iterable<Comment>) = commentRepository.saveAll(comments)

    fun delete(comment: Comment) = commentRepository.delete(comment)

    fun deleteAll() = commentRepository.deleteAll()
}