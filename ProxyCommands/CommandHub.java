package ProxyCommands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import main.ProxyPlugin;
import net.md_5.bungee.api.config.ServerInfo;


public class CommandHub
  extends Command {
    
    
  private final main.ProxyPlugin plugin;
  
   private final String cmd; 
    String msg = ChatColor.GRAY+"//";
      
  public CommandHub(ProxyPlugin plugin, String cmd) {
    super(cmd);
    this.cmd = cmd;
    
    this.plugin = plugin;
  }
  
  @Override
  public void execute(CommandSender p, String[] args) {
      
      String ServerName = this.plugin.getConfig().getDefaultServer();
  
    if ((this.cmd.equalsIgnoreCase("hub")) || ((this.cmd.equalsIgnoreCase("lobby")))) {
     if (this.plugin.getConfig().allowHubCommand()) {
      if (p.hasPermission("command.hub")) {
         if ( !( p instanceof ProxiedPlayer ) ) {
                p.sendMessage( ChatColor.RED + "Only in game players can use this command" );
                return;
            }
        
      if (args.length != 0) {
        p.sendMessage(msg+ ChatColor.RED + "Usage: /"+cmd);
        return;
      }
      
      ServerInfo target = ProxyServer.getInstance().getServerInfo(ServerName);
      
      ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(p.getName());
      if (target == null) {
        p1.sendMessage(msg+ ChatColor.RED +cmd+" seems to be offline, try again in a few moments...");
      }
      if (!p1.getServer().getInfo().getName().equalsIgnoreCase(ServerName)) {
        p1.connect(target);
        p1.sendMessage(msg+ ChatColor.RED + "Going to the "+cmd+"...");
      } else {
        p1.sendMessage(msg+ ChatColor.RED + "You are already in the "+cmd+".");
      }
    }
    else{p.sendMessage(msg+ ChatColor.RED + "No perms. (command.hub)");
      }
      }
    }
    }
  }