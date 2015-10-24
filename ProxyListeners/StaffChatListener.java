package ProxyListeners;

import static ProxyCommands.CommandChat.staffchat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class StaffChatListener
   extends Plugin
  implements Listener
{

 @EventHandler
  public void StaffChatListener(ChatEvent e)
  {
      ProxiedPlayer p1 = (ProxiedPlayer) e.getSender();
   
     String coloredmsg = ChatColor.translateAlternateColorCodes('&', e.getMessage().trim());
     
     if (!e.isCommand()){
    if (staffchat.contains(p1.getUUID())){
      for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
          if (p.hasPermission("command.staffchat")) {
  p.sendMessage(ChatColor.RED+"["+ChatColor.GREEN+"SC"+ChatColor.RED+"]" + ChatColor.GRAY + p1.getDisplayName()+ " " + coloredmsg);
     }
    }
      e.setCancelled(true);
     } else {
      e.setMessage(e.getMessage());
     } 
}
}
}