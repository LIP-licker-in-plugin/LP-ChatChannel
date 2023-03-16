package com.licker2689.lcc.events;

import com.licker2689.lcc.ChatChannel;
import com.licker2689.lcc.functions.LCCFunction;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("all")
public class LCCEvent implements Listener {
    private final ChatChannel plugin = ChatChannel.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        plugin.data.initUserData(e.getPlayer().getUniqueId());
        YamlConfiguration data = plugin.data.getUserData(e.getPlayer().getUniqueId());
        if (data.get("Enabled") == null) {
            data.set("Enabled", false);
        }
        if (data.get("Channel") == null) {
            data.set("Channel", "1");
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        plugin.data.saveAndLeave(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        if (plugin.data.getUserData(e.getPlayer().getUniqueId()).getBoolean("Enabled")) {
            e.setCancelled(true);
            LCCFunction.sendMessage(e.getPlayer(), e.getMessage());
        } else {
            if (plugin.force) {
                e.setCancelled(true);
                LCCFunction.sendMessage(e.getPlayer(), e.getMessage(), "1");
            }else{
                try{
                    for(Player p : e.getRecipients()) {
                        if (plugin.data.getUserData(p.getUniqueId()).getBoolean("Enabled")) {
                            e.getRecipients().remove(p);
                        }
                    }
                }catch (Exception ignored){

                }
            }
        }
    }
}
