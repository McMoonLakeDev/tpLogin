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


package com.minecraft.moonlake.tplogin.listeners;

import com.minecraft.moonlake.api.event.MoonLakeListener;
import com.minecraft.moonlake.tplogin.TpLoginPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerListener implements MoonLakeListener {

    private final TpLoginPlugin main;

    public PlayerListener(TpLoginPlugin main) {
        this.main = main;
    }

    public TpLoginPlugin getMain() {
        return main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location target = getMain().getConfiguration().getLocation();

        if(target != null) {
            player.teleport(target, PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }
}
