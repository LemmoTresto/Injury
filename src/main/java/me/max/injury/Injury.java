/*
 *
 *  *
 *  *  * Injury - Make injuries more realistic!
 *  *  * Copyright (C) 2018 Max Berkelmans AKA LemmoTresto
 *  *  *
 *  *  * This program is free software: you can redistribute it and/or modify
 *  *  * it under the terms of the GNU General Public License as published by
 *  *  * the Free Software Foundation, either version 3 of the License, or
 *  *  * (at your option) any later version.
 *  *  *
 *  *  * This program is distributed in the hope that it will be useful,
 *  *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  *  * GNU General Public License for more details.
 *  *  *
 *  *  * You should have received a copy of the GNU General Public License
 *  *  * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *  *
 *
 */

package me.max.injury;

import me.max.injury.listeners.PlayerDamageListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Injury extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!new File(getDataFolder(), "config.yml").exists()){
            try {
                getLogger().info("Config does not exist creating it now..");
                saveDefaultConfig();
                getLogger().info("Successfully created config file.");
            } catch (Exception e) {
                getLogger().severe("Could not create config!");
                e.printStackTrace();
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }

        try {
            getLogger().info("Initialising listeners..");
            new PlayerDamageListener(this);
            getLogger().info("Successfully initialised listeners");
        } catch (Exception e){
            getLogger().severe("Could not initialise listeners");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("Enabled successfully.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
