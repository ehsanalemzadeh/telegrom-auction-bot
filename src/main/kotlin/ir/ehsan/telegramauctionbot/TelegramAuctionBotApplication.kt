package ir.ehsan.telegramauctionbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication


@SpringBootApplication
@ConfigurationPropertiesScan("ir.ehsan.telegramauctionbot.configuration")
class TelegramAuctionBotApplication

fun main(args: Array<String>) {
    runApplication<TelegramAuctionBotApplication>(*args)
}
