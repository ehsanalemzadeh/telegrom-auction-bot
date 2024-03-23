package ir.ehsan.telegramauctionbot.persistance.repo

import ir.ehsan.telegramauctionbot.persistance.entity.OfferEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OfferRepository : JpaRepository<OfferEntity, Long> {
    fun findAllByUsername(userId: String): List<OfferEntity>
}