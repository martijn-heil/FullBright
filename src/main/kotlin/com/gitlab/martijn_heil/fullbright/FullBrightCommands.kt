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