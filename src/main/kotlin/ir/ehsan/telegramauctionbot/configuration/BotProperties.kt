package ir.ehsan.telegramauctionbot.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.telegram.telegrambots.bots.DefaultBotOptions

@ConfigurationProperties(prefix = "bot")
@ConfigurationPropertiesScan
class BotProperties(val username: String, val token: String, val proxy: ProxySettings) {
    class ProxySettings(val host: String, val port: Int, val type: DefaultBotOptions.ProxyType)
}