package com.licker2689.lcc;

import com.licker2689.lpc.utils.DataContainer;
import com.licker2689.lcc.commands.LCCCommand;
import com.licker2689.lcc.events.LCCEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatChannel extends JavaPlugin {
    private static ChatChannel plugin;
    public static DataContainer data;
    public static boolean force;

    public static ChatChannel getInstance() {
        return plugin;
    }

    public void onEnable() {
        plugin = this;
        data = new DataContainer(plugin);
        force = data.getConfig().getBoolean("Settings.forceUseChannel");
        plugin.getServer().getPluginManager().registerEvents(new LCCEvent(), plugin);
        getCommand("lcc").setExecutor(new LCCCommand());

    }

    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> data.saveAndLeave(player.getUniqueId()));
        data.save();
    }
}
