/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.minecraft.moonlake.tplogin.commands;

import com.minecraft.moonlake.api.annotation.plugin.command.Command;
import com.minecraft.moonlake.api.annotation.plugin.command.CommandArgument;
import com.minecraft.moonlake.api.annotation.plugin.command.CommandPermission;
import com.minecraft.moonlake.tplogin.TpLoginPlugin;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class CommandTpLogin {

    private final TpLoginPlugin main;

    public CommandTpLogin(TpLoginPlugin main) {
        this.main = main;
    }

    public TpLoginPlugin getMain() {
        return main;
    }

    @Command(name = "tplogin", usage = "<help|reload>", min = 1, max = 1)
    @CommandPermission("moonlake.tplogin")
    public void onCommand(CommandSender sender, @CommandArgument String arg) {
        if(arg.equalsIgnoreCase("help")) {
            sender.sendMessage(new String[] {
                    "/tplogin help - 查看命令帮助.",
                    "/tplogin reload - 重新载入配置文件.",
                    "/tplogin-set - 设置当前位置为传送点.",
                    "/tplogin-goto - 传送到设置的传送点.",
            });
        }
        else if(arg.equalsIgnoreCase("reload")) {
            if(getMain().getConfiguration().reload()) {
                sender.sendMessage(getMain().getConfiguration().getPrefix() + "配置文件已经重新载入...");
            }
        }
        else {
            sender.sendMessage(getMain().getConfiguration().getPrefix() + "未知的命令参数.");
        }
    }

    @Command(name = "tplogin-set")
    @CommandPermission("moonlake.tplogin.set")
    public void onSet(Player player) {
        getMain().getConfiguration().setLocation(player.getLocation());
        getMain().getConfiguration().save();
        player.sendMessage(getMain().getConfiguration().getPrefix() + "成功设置当前位置为登录传送点.");
    }

    @Command(name = "tplogin-goto")
    @CommandPermission("moonlake.tplogin.goto")
    public void onGoto(Player player) {
        Location target = getMain().getConfiguration().getLocation();

        if(target == null) {
            player.sendMessage(getMain().getConfiguration().getPrefix() + "错误, 登录传送点还没有进行设置.");
            return;
        }
        player.teleport(target, PlayerTeleportEvent.TeleportCause.PLUGIN);
        player.sendMessage(getMain().getConfiguration().getPrefix() + "成功传送到登录传送点.");
    }
}
