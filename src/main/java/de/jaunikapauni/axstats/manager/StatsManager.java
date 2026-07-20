package de.jaunikapauni.axstats.manager;

import de.jaunikapauni.axstats.AxStats;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class StatsManager {

    AxStats reference;
    public StatsManager(AxStats reference){
        this.reference = reference;
    }

    public void addPlayerKill(UUID uuid){
        Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
            try(Connection conn = reference.getDatabaseManager().getConnection()){
                try(PreparedStatement ps = conn.prepareStatement("UPDATE players SET player_kills = player_kills + 1 WHERE uuid = ?")){
                    ps.setString(1, uuid.toString());
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void addMobKill(UUID uuid){
        Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
            try(Connection conn = reference.getDatabaseManager().getConnection()){
                try(PreparedStatement ps = conn.prepareStatement("UPDATE players SET mob_kills = mob_kills + 1 WHERE uuid = ?")){
                    ps.setString(1, uuid.toString());
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void addDeath(UUID uuid){
        Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
            try(Connection conn = reference.getDatabaseManager().getConnection()){
                try(PreparedStatement ps = conn.prepareStatement("UPDATE players SET deaths = deaths + 1 WHERE uuid = ?")){
                    ps.setString(1, uuid.toString());
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void onPlayerJoin(UUID uuid){
        Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
            try(Connection conn = reference.getDatabaseManager().getConnection()){
                boolean playerExists;
                try(PreparedStatement ps = conn.prepareStatement("SELECT uuid FROM players WHERE uuid = ?")){
                    ps.setString(1, uuid.toString());
                    playerExists = ps.executeQuery().next();
                }
                if(!playerExists){
                    try(PreparedStatement ps = conn.prepareStatement("INSERT INTO players (uuid, deaths, sessions, isOnline, first_join, player_kills, mob_kills) VALUES (?, ?, ?, ?, NOW(), 0, 0)")){
                        ps.setString(1, uuid.toString());
                        ps.setInt(2, 0);
                        ps.setInt(3, 1);
                        ps.setBoolean(4, true);
                        ps.executeUpdate();
                    }
                } else {
                    try(PreparedStatement ps = conn.prepareStatement("UPDATE players SET sessions = sessions + 1, isOnline = true WHERE uuid = ?")){
                        ps.setString(1, uuid.toString());
                        ps.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void onPlayerQuit(UUID uuid){
        Bukkit.getScheduler().runTaskAsynchronously(reference, () -> {
            try(Connection conn = reference.getDatabaseManager().getConnection()){
                try(PreparedStatement ps = conn.prepareStatement("UPDATE players SET isOnline = false, last_online = NOW() WHERE uuid = ?")){
                    ps.setString(1, uuid.toString());
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
