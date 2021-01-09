package app.xlui.pwitter.entity.db

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "operation_log")
data class OperationLog(
    @Id
    @GeneratedValue
    val id: Long,
    val operationType: Int,
    val objectId: Long,
    val beforeValue: String,
    val afterValue: String,
    val operator: Long,
    val comment: String,
    @CreationTimestamp
    val createTime: LocalDateTime,
    @UpdateTimestamp
    val updateTime: LocalDateTime
)
