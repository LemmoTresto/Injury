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

package me.max.injury.listeners;

import me.max.injury.Injury;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDamageListener implements Listener {

    private Injury injury;

    public PlayerDamageListener(Injury injury){
        this.injury = injury;

        this.injury.getServer().getPluginManager().registerEvents(this, injury);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        if (!(event.getEntityType() == EntityType.PLAYER)) return; // we want players.

        Player p = (Player) event.getEntity();

        if (p.getHealth() <= 0) return; //player is dead.
        if (p.hasPermission("injury.bypass")) return;

        for (String key : injury.getConfig().getConfigurationSection("effects").getKeys(false)){
            ConfigurationSection effectSection = injury.getConfig().getConfigurationSection("effects." + key);

            if (!effectSection.getBoolean("enabled")) continue;
            if (p.hasPermission(effectSection.getString("bypass-permission"))) continue;

            Double healthRequirement = effectSection.getDouble("health-requirement");
            if (p.getHealth() > healthRequirement) continue;

            PotionEffectType type = PotionEffectType.getByName(key);
            if (type == null) continue;

            p.addPotionEffect(new PotionEffect(type, 999999, effectSection.getInt("amplifier"), false, effectSection.getBoolean("particles")));
        }
    }
}
