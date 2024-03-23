package ir.ehsan.telegramauctionbot.model

import ir.ehsan.telegramauctionbot.model.enums.AddOrUpdateStep
import ir.ehsan.telegramauctionbot.model.enums.AddOrUpdateStep.*
import ir.ehsan.telegramauctionbot.model.enums.OfferType
import ir.ehsan.telegramauctionbot.persistance.entity.OfferEntity
import ir.ehsan.telegramauctionbot.service.OfferService
import org.telegram.telegrambots.meta.api.objects.Message

data class BotAddRequest(var type: OfferType?, var name: String?, var price: Long?, var
description: String?, override var username: String?, override var nextStep: AddOrUpdateStep?,
                         private val offerService: OfferService) :
        BotRequest<AddOrUpdateStep>
        (username,
                nextStep) {
    constructor(username: String?, offerService: OfferService) : this(null, null, null, null, username,
            TYPE, offerService)

    override fun process(message: Message): String {
        when (nextStep) {
            TYPE -> type = OfferType.value(message.text)
            NAME -> name = message.text
            // TODO : check if creatable
            PRICE -> price = message.text.toLong()
            DESCRIPTION -> description = message.text
            null -> throw RuntimeException("هیچ درخواستی در جریان نیست")
        }
        nextStep = nextStep?.next()
        if (isComplete()) {
            offerService.createOffer(this)
            return "پیشنهاد شما به لیست پیشنهادات اضافه شد."
        } else
            return nextStep!!.message
    }

    override fun toOfferEntity(): OfferEntity {
        return OfferEntity(username, name, price, description, type)
    }
}