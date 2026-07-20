package de.jaunikapauni.axstats.listener;

import de.jaunikapauni.axstats.AxStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerDeathListener implements Listener {
    AxStats reference;
    public PlayerDeathListener(AxStats reference){
        this.reference = reference;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getPlayer();
        reference.getStatsManager().addDeath(p.getUniqueId());
    }
}
