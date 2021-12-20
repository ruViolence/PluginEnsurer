package ru.violence.pluginensurer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginEnsurer extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getScheduler().runTaskTimer(this, () -> {
            reloadConfig();

            PluginManager pluginManager = getServer().getPluginManager();

            for (String plugin : getConfig().getStringList("plugins")) {
                if (pluginManager.isPluginEnabled(plugin)) continue;

                getLogger().warning("Plugin \"" + plugin + "\" is not enabled! Stopping the server...");
                getServer().dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("stop_command"));
                return;
            }
        }, 1, getConfig().getLong("check_delay") * 20);
    }
}
