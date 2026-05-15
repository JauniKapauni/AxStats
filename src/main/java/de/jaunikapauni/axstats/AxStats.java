package de.jaunikapauni.axstats;

import de.jaunikapauni.axstats.manager.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AxStats extends JavaPlugin {
    DatabaseManager databaseManager;
    public DatabaseManager getDatabaseManager(){
        return databaseManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
