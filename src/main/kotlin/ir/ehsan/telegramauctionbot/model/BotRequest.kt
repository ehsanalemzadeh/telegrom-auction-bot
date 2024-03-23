package ir.ehsan.telegramauctionbot.model

import ir.ehsan.telegramauctionbot.model.enums.RequestStep
import ir.ehsan.telegramauctionbot.persistance.entity.OfferEntity
import org.telegram.telegrambots.meta.api.objects.Message


abstract class BotRequest<T>(open var username: String?, open var nextStep: T?) where T : RequestStep {
    abstract fun process(message: Message): String

    abstract fun toOfferEntity(): OfferEntity

    fun isComplete(): Boolean {
        return (nextStep == null)
    }

}