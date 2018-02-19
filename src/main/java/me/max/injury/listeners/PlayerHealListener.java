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
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerHealListener implements Listener {

    private Injury injury;

    public PlayerHealListener(Injury injury){
        this.injury = injury;

        this.injury.getServer().getPluginManager().registerEvents(this, injury);
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent event){
        if (!(event.getEntityType() == EntityType.PLAYER)) return; //we want players.

        Player p = (Player) event.getEntity();

        if (injury.getConfig().getBoolean("blindness-effect.enabled")){
            if (injury.getConfig().getDouble("blindness-effect.health-requirement") > p.getHealth()) p.removePotionEffect(PotionEffectType.BLINDNESS);
        }

        if (injury.getConfig().getBoolean("slowness-effect.enabled")){
            if (injury.getConfig().getDouble("slowness-effect.health-requirement") > p.getHealth()) p.removePotionEffect(PotionEffectType.SLOW);
        }
    }
}
