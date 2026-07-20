package de.jaunikapauni.axstats.listener;

import de.jaunikapauni.axstats.AxStats;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerQuitListener implements Listener {
    AxStats reference;
    public PlayerQuitListener(AxStats reference){
        this.reference = reference;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        reference.getStatsManager().onPlayerQuit(e.getPlayer().getUniqueId());
    }
}
