package de.jaunikapauni.axstats;

import de.jaunikapauni.axstats.listener.EntityDeathListener;
import de.jaunikapauni.axstats.listener.PlayerDeathListener;
import de.jaunikapauni.axstats.listener.PlayerJoinListener;
import de.jaunikapauni.axstats.listener.PlayerQuitListener;
import de.jaunikapauni.axstats.manager.DatabaseManager;
import de.jaunikapauni.axstats.manager.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxStats extends JavaPlugin {
    DatabaseManager databaseManager;
    public DatabaseManager getDatabaseManager(){
        return databaseManager;
    }
    StatsManager statsManager;
    public StatsManager getStatsManager(){
        return statsManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        databaseManager = new DatabaseManager(this);
        try{
            if(databaseManager.initDatabaseTable1() == false){
                getLogger().severe("Error creating table!");
                Bukkit.getServer().shutdown();
            }
            getLogger().info("DB connection successfully established!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        statsManager = new StatsManager(this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(this), this);
        getLogger().info("");
        getLogger().info("----------------------------------------");
        getLogger().info("Name: " + getName());
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info(String.join("Authors: " + ", ", getDescription().getAuthors()));
        getLogger().info("----------------------------------------");
        getLogger().info("");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        databaseManager.close();
    }
}
