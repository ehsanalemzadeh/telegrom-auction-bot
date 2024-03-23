package ir.ehsan.telegramauctionbot.configuration

import ir.ehsan.telegramauctionbot.AuctionBot
import ir.ehsan.telegramauctionbot.service.OfferService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class BotConfiguration {
    @Bean
    fun getAuctionBot(botProperties: BotProperties, offerService: OfferService):
            AuctionBot {
        val botOptions = DefaultBotOptions()
        botOptions.proxyHost = botProperties.proxy.host
        botOptions.proxyPort = botProperties.proxy.port

        botOptions.proxyType = botProperties.proxy.type

        return AuctionBot(botProperties.username, botProperties.token, botOptions, offerService)

    }

    @Bean
    fun get(bot: TelegramLongPollingBot): TelegramBotsApi {
        val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
        botsApi.registerBot(bot)
        return botsApi
    }
}