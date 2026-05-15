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
        UUID killerUUID = killer.getUniqueId();
        try(Connection conn = reference.getDatabaseManager().getConnection()){
            if(e.getEntity() instanceof Player){
                try(PreparedStatement ps = conn.prepareStatement("UPDATE players SET player_kills = player_kills + 1 WHERE uuid = ?")){
                    ps.setString(1, killerUUID.toString());
                    ps.executeUpdate();
                }
            } else {
                try(PreparedStatement ps = conn.prepareStatement("UPDATE players SET mob_kills = mob_kills + 1 WHERE uuid = ?")){
                    ps.setString(1, killerUUID.toString());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
