package com.gitlab.martijn_heil.fullbright

import com.gitlab.martijn_heil.nincommands.common.CommonModule
import com.gitlab.martijn_heil.nincommands.common.bukkit.BukkitAuthorizer
import com.gitlab.martijn_heil.nincommands.common.bukkit.provider.BukkitModule
import com.gitlab.martijn_heil.nincommands.common.bukkit.provider.sender.BukkitSenderModule
import com.gitlab.martijn_heil.nincommands.common.bukkit.registerCommand
import com.sk89q.intake.Intake
import com.sk89q.intake.fluent.CommandGraph
import com.sk89q.intake.parametric.ParametricBuilder
import com.sk89q.intake.parametric.provider.PrimitivesModule
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

private val data = HashMap<Player, Boolean>()

class FullBright : JavaPlugin() {
    override fun onEnable() {
        val injector = Intake.createInjector()
        injector.install(PrimitivesModule())
        injector.install(BukkitModule(server))
        injector.install(BukkitSenderModule())
        injector.install(CommonModule())

        val builder = ParametricBuilder(injector)
        builder.authorizer = BukkitAuthorizer()

        val dispatcher = CommandGraph()
                .builder(builder)
                .commands()
                .registerMethods(FullBrightCommands())
                .graph()
                .dispatcher

        registerCommand(dispatcher, this, dispatcher.aliases.toList())
    }

    override fun onDisable() {
        data.clear()
    }
}

var Player.isFullBright
    set(value) {
        if(value) {
            data[this] = true
            this.addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, kotlin.Int.MAX_VALUE,
                    1, false, false))
        } else {
            data[this] = false
            this.removePotionEffect(PotionEffectType.NIGHT_VISION)
        }
    }
    get() = data[this] ?: false