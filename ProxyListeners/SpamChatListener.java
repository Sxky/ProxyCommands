package ProxyListeners;

import java.util.HashMap;
import java.util.UUID;
import main.ProxyPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class SpamChatListener implements Listener {
    private final main.ProxyPlugin plugin;
    
     public SpamChatListener(ProxyPlugin plugin) {
    this.plugin = plugin;
    this.cooldown = new HashMap();
    }
    
    private HashMap<UUID, Long> cooldown;
    
      @EventHandler(priority = EventPriority.HIGH)
  public void onChat(ChatEvent evt) {
   ProxiedPlayer p = (ProxiedPlayer) evt.getSender();
    UUID uuid = p.getUniqueId();
    if (!p.hasPermission("command.spambypass")) {
        if (evt.isCommand()) {
        return;
      }
    if (this.cooldown.containsKey(uuid)) {
      float time = (float)((System.currentTimeMillis() - ((Long)this.cooldown.get(uuid)).longValue()) / 1000L);
      if (time < this.plugin.getConfig().getSpamCooldown("Chat")) {
        evt.setCancelled(true);
        p.sendMessage(ChatColor.GRAY+"//"+ChatColor.RED+"Please slow down.  You are spamming the chat too quickly.");
      } else {
        this.cooldown.put(uuid, Long.valueOf(System.currentTimeMillis()));
      }
    } else {
      this.cooldown.put(uuid, Long.valueOf(System.currentTimeMillis()));
    }
  }
 }
    
      @EventHandler
  public void onLeave(ServerDisconnectEvent evt) {
   if (this.cooldown.containsKey(evt.getPlayer().getUniqueId())){
    this.cooldown.remove(evt.getPlayer().getUniqueId());
   }
  }

        @EventHandler
  public void onCommand(ChatEvent evt) {
           ProxiedPlayer p = (ProxiedPlayer) evt.getSender();
    UUID uuid = p.getUniqueId();
    
        if (!p.hasPermission("command.spambypass")){
      if (!evt.isCommand()) {
        return;
      }
      
    if (this.cooldown.containsKey(uuid)) {
      float time = (float)((System.currentTimeMillis() - ((Long)this.cooldown.get(uuid)).longValue()) / 1000L);
      if (time < this.plugin.getConfig().getSpamCooldown("Command")) {
        evt.setCancelled(true);
        p.sendMessage(ChatColor.GRAY+"//"+ChatColor.RED+"Please slow down. You are spamming commands too quickly.");
      } else {
        this.cooldown.put(uuid, Long.valueOf(System.currentTimeMillis()));
      }
    } else {
      this.cooldown.put(uuid, Long.valueOf(System.currentTimeMillis()));
    }
  }
  }
  }