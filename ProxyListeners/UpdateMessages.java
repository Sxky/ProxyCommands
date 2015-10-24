package ProxyListeners;

import main.ProxyPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class UpdateMessages implements Listener {
    private final main.ProxyPlugin plugin;
    Boolean availableUpdate;
 
 public UpdateMessages(ProxyPlugin plugin) {
  this.plugin = plugin;
 }
    
 @EventHandler
  public void onJoin(PostLoginEvent e) {
     if (this.plugin.resource !=0) {
           ProxiedPlayer p = (ProxiedPlayer) e.getPlayer();
      
      if (p.hasPermission("command.proxycommands")) {
          if (this.plugin.availableUpdate) {
          TextComponent CurrentVersion = new TextComponent( this.plugin.getDescription().getVersion() );
          CurrentVersion.setHoverEvent(new HoverEvent (HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.YELLOW+"This is the current version running.").create()));
          TextComponent OnlineVersion = new TextComponent( this.plugin.content );
          OnlineVersion.setHoverEvent(new HoverEvent (HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.YELLOW+"This is the latest version available.").create()));
          String PName = this.plugin.getDescription().getName();
          
          TextComponent Message = new TextComponent(PName+": Found an update! (");
          Message.addExtra(CurrentVersion);
          Message.addExtra("/");
          Message.addExtra(OnlineVersion);
          Message.addExtra(")");
          Message.setColor(ChatColor.AQUA);
          
          p.sendMessage(Message);
          p.sendMessage(ChatColor.AQUA+""+"https://www.spigotmc.org/resources/"+PName.toLowerCase()+"."+this.plugin.resource+"/");
          p.sendMessage(ChatColor.AQUA+PName+": You can turn these messages off in the config!");
        } else {
        this.plugin.checkForUpdate(true);
        }
      }
       }
    }
}