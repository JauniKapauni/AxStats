package de.jaunikapauni.axstats.listener;

import de.jaunikapauni.axstats.AxStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerJoinListener implements Listener {
    AxStats reference;
    public PlayerJoinListener(AxStats reference){
        this.reference = reference;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        reference.getStatsManager().onPlayerJoin(p.getUniqueId());
    }
}
