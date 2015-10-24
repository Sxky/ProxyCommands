package ProxyListeners;

import main.ProxyPlugin;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class Slots
  extends Plugin
  implements Listener {
        
    private final main.ProxyPlugin plugin;
    
    public Slots(ProxyPlugin plugin) {
    this.plugin = plugin;
    }

  @EventHandler
  public void onProxyPing(ProxyPingEvent evt) {
    // int addedSlots = Integer.parseInt(this.plugin.getConfig().getExtraSlots());
     int addedSlots = this.plugin.getConfig().getExtraSlots();
      if (addedSlots == 0) {return;}
    ServerPing original = evt.getResponse();
   evt.setResponse(new ServerPing(original.getVersion(), new ServerPing.Players(original.getPlayers().getOnline() + addedSlots, original.getPlayers().getOnline(), null), original.getDescription(), original.getFavicon()));
  }
}