package ir.ehsan.telegramauctionbot

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import ir.ehsan.telegramauctionbot.model.BotAddRequest
import ir.ehsan.telegramauctionbot.model.BotRequest
import ir.ehsan.telegramauctionbot.model.enums.RequestStep
import ir.ehsan.telegramauctionbot.service.OfferService
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.builder
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException


class AuctionBot(private val botUsername: String, private val botToken: String, options: DefaultBotOptions?, private val offerService: OfferService) : TelegramLongPollingBot(options) {

    private val OBJECT_MAPPER: ObjectMapper = JsonMapper.builder().addModule(JavaTimeModule()).build()
    private val offerMap: MutableMap<Long, BotRequest<out RequestStep>> = HashMap()
    override fun getBotToken(): String {
        return botToken
    }

    override fun getBotUsername(): String {
        return botUsername
    }

    override fun onUpdateReceived(update: Update?) {
        if (update != null && update.message != null) {
            try {
                val message = update.message
                if (message.isCommand) {
                    processCommand(message)
                } else {
                    val botRequest = offerMap[message.chatId]
                    if (botRequest != null) {
                        val processMessage = botRequest.process(message)
                        if (botRequest.isComplete()) {
                            offerMap.remove(message.chatId)
                        }
                        sendMessage(message.chatId, processMessage)
                    }
                }
            } catch (e: RuntimeException) {
                e.message?.let { sendMessage(update.message.chatId, it) }
            }
        }
    }

    private fun createAddOfferRequest(message: Message): String {
        val botAddRequest = BotAddRequest(getUsername(message), offerService)
        offerMap[message.chatId] = botAddRequest
        return botAddRequest.nextStep!!.message
    }

    private fun sendMessage(chatId: Long, msg: Any) {
        if (msg is String) sendMessage(chatId, msg)
        else sendMessage(chatId, OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(msg))
    }

    private fun sendMessage(chatId: Long, msg: String) {
        val message: SendMessage = builder().chatId(chatId.toString()).text(msg).build()
        try {
            execute(message)
        } catch (e: TelegramApiException) {
            throw RuntimeException(e)
        }
    }

    private fun processCommand(message: Message) {
        when (message.text) {
            "/all" -> sendMessage(message.chatId, offerService.getAllOffers())
            "/get" -> sendMessage(message.chatId, offerService.getUserOffers(getUsername(message)))
            "/create" -> sendMessage(message.chatId, createAddOfferRequest(message))
        }
    }

    private fun getUsername(message: Message): String {
        return message.chat.userName
    }
}