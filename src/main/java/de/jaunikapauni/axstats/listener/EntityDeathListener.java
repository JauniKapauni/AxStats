package de.jaunikapauni.axstats.listener;

import de.jaunikapauni.axstats.AxStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class EntityDeathListener implements Listener {
    AxStats reference;
    public EntityDeathListener(AxStats reference){
        this.reference = reference;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        Player killer = e.getEntity().getKiller();
        if(killer == null){
            return;
        }
        if(e.getEntity() instanceof Player){
            reference.getStatsManager().addPlayerKill(killer.getUniqueId());
            return;
        }
        reference.getStatsManager().addMobKill(killer.getUniqueId());
    }
}
