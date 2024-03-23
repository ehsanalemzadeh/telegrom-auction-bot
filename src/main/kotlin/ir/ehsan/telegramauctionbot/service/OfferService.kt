package ir.ehsan.telegramauctionbot.service

import ir.ehsan.telegramauctionbot.model.BotRequest
import ir.ehsan.telegramauctionbot.model.enums.AddOrUpdateStep
import ir.ehsan.telegramauctionbot.persistance.entity.OfferEntity
import ir.ehsan.telegramauctionbot.persistance.repo.OfferRepository
import org.springframework.stereotype.Service

@Service
class OfferService(private val offerRepository: OfferRepository) {

    // TODO : must add pagination and sorting
    fun getAllOffers(): List<OfferEntity> {
        return offerRepository.findAll()
    }

    // TODO : must add pagination and sorting
    fun getUserOffers(username: String): List<OfferEntity> {
        return offerRepository.findAllByUsername(username)
    }

    fun createOffer(botRequest: BotRequest<AddOrUpdateStep>): OfferEntity {
        val offerEntity: OfferEntity = botRequest.toOfferEntity()
        return offerRepository.save(offerEntity)
    }
}