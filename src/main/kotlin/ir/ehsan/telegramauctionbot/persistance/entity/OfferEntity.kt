package ir.ehsan.telegramauctionbot.persistance.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import ir.ehsan.telegramauctionbot.model.enums.OfferType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "offers")
@JsonInclude(JsonInclude.Include.NON_NULL)
class OfferEntity(
        @JsonIgnore
        var username: String? = null,
        @get:JsonProperty("عنوان")
        var name: String? = null,
        @get:JsonProperty("قیمت پیشنهادی")
        var price: Long? = null,
        @get:JsonProperty("توضیحات")
        var description: String? = null,
        @get:JsonProperty("نوع پیشنهاد")
        @Enumerated(EnumType.STRING)
        var type: OfferType? = null,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @get:JsonProperty("شناسه پیشنهاد")
        var id: Long? = null) {
    @JsonIgnore
    private var createDate: LocalDateTime? = null

    @JsonIgnore
    private var updateDate: LocalDateTime? = null

    @get:JsonProperty("لینک کاربر‌")
    private val userLink: String
        get() {
            return "https://t.me/$username"
        }

    @PrePersist
    fun onCreate() {
        createDate = LocalDateTime.now()
    }

    @PreUpdate
    fun onUpdate() {
        updateDate = LocalDateTime.now()
    }
}