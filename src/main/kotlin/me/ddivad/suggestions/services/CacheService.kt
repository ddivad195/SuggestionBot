package me.ddivad.suggestions.services

import dev.kord.core.supplier.EntitySupplyStrategy
import kotlinx.coroutines.flow.toList
import me.ddivad.suggestions.dataclasses.Configuration
import me.jakejmattson.discordkt.Discord
import me.jakejmattson.discordkt.annotations.Service
import me.jakejmattson.discordkt.extensions.toSnowflake

@Service
class CacheService(private val discord: Discord, private val configuration: Configuration) {
    suspend fun run() {
        configuration.guildConfigurations.forEach { config ->
            try {
                val guild = config.value.id!!.toSnowflake().let { discord.kord.getGuild(it) } ?: return@forEach
                guild.withStrategy(EntitySupplyStrategy.cachingRest).roles.toList()
            } catch (ex: Exception) {
                println(ex.message)
            }
        }
    }
}