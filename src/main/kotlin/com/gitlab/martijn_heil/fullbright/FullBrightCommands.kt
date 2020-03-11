/*
 * FullBright
 * Copyright (C) 2020  Martijn Heil
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

import com.gitlab.martijn_heil.nincommands.common.CommandTarget
import com.gitlab.martijn_heil.nincommands.common.Toggle
import com.sk89q.intake.Command
import com.sk89q.intake.Require
import org.bukkit.entity.Player

class FullBrightCommands {
    @Command(aliases = ["fullbright", "fb"], desc = "Toggle fullbright")
    @Require("fullbright")
    fun fullbright(@Toggle value: Boolean, @CommandTarget("fullbright.others") target: Player) {
        target.isFullBright = value
    }
}
