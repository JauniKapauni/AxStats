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
    public void onJoin(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("SELECT uuid FROM players WHERE uuid = ?")){
                ps.setString(1, p.getUniqueId().toString());
                ResultSet rs = ps.executeQuery();
                if(!rs.next()){
                    try(PreparedStatement ps1 = conn.prepareStatement("INSERT INTO players (uuid, deaths, sessions, isOnline, first_join, last_online, player_kills, mob_kills) VALUES (?, ?, ?, ?, NOW(), null, 0, 0)")){
                        ps1.setString(1, p.getUniqueId().toString());
                        ps1.setInt(2, 0);
                        ps1.setInt(3, 1);
                        ps1.executeUpdate();
                    }
                } else {
                    try(PreparedStatement update = conn.prepareStatement("UPDATE players SET sessions = sessions + 1 WHERE uuid = ?")){
                        update.setString(1, p.getUniqueId().toString());
                        update.executeUpdate();
                    }
                    try(PreparedStatement update = conn.prepareStatement("UPDATE players SET isOnline = true WHERE uuid = ?")){
                        update.setString(1, p.getUniqueId().toString());
                        update.executeUpdate();
                    }
                }
            }
        }
    }
}
