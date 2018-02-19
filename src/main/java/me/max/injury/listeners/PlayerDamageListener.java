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

        if (injury.getConfig().getBoolean("blindness-effect.enabled")){
            if (p.getHealth() <= injury.getConfig().getDouble("blindness-effect.health-requirement")) p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999999, injury.getConfig().getInt("blindness-effect.amplifier"), false, injury.getConfig().getBoolean("blindness-effect.particles")));
        }

        if (injury.getConfig().getBoolean("slowness-effect.enabled")){
            if (p.getHealth() <= injury.getConfig().getDouble("slowness-effect.health-requirement")) p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999999, injury.getConfig().getInt("slowness-effect.amplifier"), false, injury.getConfig().getBoolean("slowness-effect.particles")));
        }
    }
}
