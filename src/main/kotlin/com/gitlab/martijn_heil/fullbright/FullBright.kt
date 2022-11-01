/*
 * FullBright
 * Copyright (C) 2020-2022  Martijn Heil
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitlab.martijn_heil.fullbright

import com.gitlab.martijn_heil.nincommands.common.CommonModule
import com.gitlab.martijn_heil.nincommands.common.bukkit.BukkitAuthorizer
import com.gitlab.martijn_heil.nincommands.common.bukkit.provider.BukkitModule
import com.gitlab.martijn_heil.nincommands.common.bukkit.provider.sender.BukkitSenderModule
import com.gitlab.martijn_heil.nincommands.common.bukkit.registerCommand
import com.gitlab.martijn_heil.nincommands.common.bukkit.unregisterCommand
import com.sk89q.intake.Intake
import com.sk89q.intake.fluent.CommandGraph
import com.sk89q.intake.parametric.ParametricBuilder
import com.sk89q.intake.parametric.provider.PrimitivesModule
import org.bukkit.command.Command
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

private val data = HashMap<Player, Boolean>()

class FullBright : JavaPlugin() {
    private lateinit var commands: Collection<Command>

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

        commands = dispatcher.commands.mapNotNull { registerCommand(it.callable, this, it.allAliases.toList()) }
    }

    override fun onDisable() {
        data.clear()
        commands.forEach { unregisterCommand(it) }
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
