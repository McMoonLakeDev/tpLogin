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


package com.minecraft.moonlake.tplogin.config;

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.annotation.plugin.config.ConfigValue;
import com.minecraft.moonlake.tplogin.TpLoginPlugin;
import com.minecraft.moonlake.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;

public class TpLoginPluginConfig {

    @ConfigValue(path = "Prefix", colorChar = '&')
    private String prefix;

    @ConfigValue(path = "Location")
    private String location;

    private final TpLoginPlugin main;

    public TpLoginPluginConfig(TpLoginPlugin main) {
        this.main = main;
    }

    public TpLoginPlugin getMain() {
        return main;
    }

    public boolean reload() {
        if(!getMain().getDataFolder().exists())
            getMain().getDataFolder().mkdirs();
        File config = new File(getMain().getDataFolder(), "config.yml");
        if(!config.exists())
            getMain().saveDefaultConfig();

        MoonLakeAPI.getPluginAnnotation().getConfig().load(getMain(), config, this);

        return true;
    }

    public boolean save() {
        MoonLakeAPI.getPluginAnnotation().getConfig().save(getMain(), "config.yml", this);
        return true;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setLocation(Location target) {
        if(target == null) {
            location = null;
            return;
        }
        double x, y, z;
        float yaw, pitch;

        x = StringUtil.rounding(target.getX(), 3);
        y = StringUtil.rounding(target.getY(), 3);
        z = StringUtil.rounding(target.getZ(), 3);
        yaw = (float) StringUtil.rounding(target.getYaw(), 3);
        pitch = (float) StringUtil.rounding(target.getPitch(), 3);

        location = new StringBuilder(target.getWorld().getName())
                .append(",").append(x)
                .append(",").append(y)
                .append(",").append(z)
                .append(",").append(yaw)
                .append(",").append(pitch)
                .toString();
    }

    public Location getLocation() {
        if(location == null || location.isEmpty()) {
            return null;
        }
        Location location1 = null;

        try {
            // 格式: world,x,y,z,yaw,pitch
            String[] data = location.split(",");

            if(data.length == 6) {
                World target = Bukkit.getServer().getWorld(data[0]);

                if(target != null) {

                    double x, y, z;
                    float yaw, pitch;

                    x = Double.parseDouble(data[1]);
                    y = Double.parseDouble(data[2]);
                    z = Double.parseDouble(data[3]);
                    yaw = Float.parseFloat(data[4]);
                    pitch = Float.parseFloat(data[5]);

                    location1 = new Location(target, x, y, z, yaw, pitch);
                }
            }
        }
        catch (Exception e) {

            getMain().getMLogger().error("The format location data exception: ");

            e.printStackTrace();
        }
        return location1;
    }
}
