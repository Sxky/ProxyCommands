package ProxyCommands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import main.ProxyPlugin;
import net.md_5.bungee.api.config.ServerInfo;


public class CommandGoTo
  extends Command {
    
    
  private final main.ProxyPlugin plugin;
  
   private final String cmd; 
    String msg = ChatColor.GRAY+"//";
      
  public CommandGoTo(ProxyPlugin plugin, String cmd) {
    super(cmd);
    this.cmd = cmd;
    
    this.plugin = plugin;
  }
  
  @Override
  public void execute(CommandSender p, String[] args) {
      
      String ServerName = this.plugin.getConfig().getDefaultServer();
  
    if (this.cmd.equalsIgnoreCase("goto")) {
      if (p.hasPermission("command.goto")) {
         
      if (args.length != 1) {
        p.sendMessage(msg+ ChatColor.RED + "Usage: /"+cmd+" <player>");
        return;
      }
      
      ProxiedPlayer player2 = (ProxiedPlayer) ProxyServer.getInstance().getPlayer(args[0]);
      if (player2 == null) {p.sendMessage(msg+ ChatColor.RED + "Could not find " + args[0] + "."); }else{
          
      ServerInfo target = player2.getServer().getInfo();
      
      ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(p.getName());
      if (!p1.getServer().getInfo().getName().equalsIgnoreCase(ServerName)) {
        p1.connect(target);
        p1.sendMessage(msg+ ChatColor.RED + "Going to "+args[0]+"'s server...");
      }
      else {
        p1.sendMessage(msg+ ChatColor.RED + "You are already in the same server as "+args[0]+".");
      }
    }
      
      }else{p.sendMessage(msg+ ChatColor.RED + "No perms. (command.goto)");
    }}
    }
  }