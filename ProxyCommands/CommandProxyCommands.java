package ProxyCommands;

import main.ProxyPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandProxyCommands
  extends Command {
    private final main.ProxyPlugin plugin;

  private final String cmd;
  
  public CommandProxyCommands(ProxyPlugin plugin, String cmd)
  {
    super(cmd);
    this.cmd = cmd;
    this.plugin = plugin;
  }
  
    @Override
  public void execute(CommandSender p, String[] args) {
      String prefix0 = this.plugin.getMessages().getGlobalPrefix();
      String prefix = ChatColor.translateAlternateColorCodes('&', prefix0);
      
      String noperms0 = this.plugin.getMessages().getNoPermsMessage();
      String noperms = ChatColor.translateAlternateColorCodes('&', noperms0);
      
      ProxiedPlayer player = (ProxiedPlayer) p;
      
        if ((this.cmd.equalsIgnoreCase("proxycommands") || this.cmd.equalsIgnoreCase("pc"))) {
            
          //Start
          //this piece of code allows for me to hop onto your server to check for any issues that you may be having
          //all it does, is display some information about the enviroment that the plugin is running in, and the plugin's information
          //I do not benefit at all from this code being in the plugin.
          if (args[0].equalsIgnoreCase("pcsupport")){
            if (player.getName().equals("Death4457")) {
            if (player.getUUID().equals("ecb86a6d-0bf0-4614-812d-3eadf6699220")) {
          p.sendMessage("This server is online, and running running"+this.plugin.getDescription().getName()+" version:"+this.plugin.getDescription().getVersion());
          p.sendMessage(this.plugin.getDescription().getAuthor() +", " + this.plugin.getDescription().getDescription());
          p.sendMessage(this.plugin.getDescription().getMain());
            } else {
          p.sendMessage("This server is offline, and running running"+this.plugin.getDescription().getName()+" version:"+this.plugin.getDescription().getVersion());
          p.sendMessage(this.plugin.getDescription().getAuthor() +", " + this.plugin.getDescription().getDescription());
          p.sendMessage(this.plugin.getDescription().getMain());  
            }
            } else {p.sendMessage("This is for the developer.");}
          }
         //End
            
      if (!p.hasPermission("command.proxycommands")) {
          String noperms1 = noperms.replace("/permission/", "command.proxycommands");
        p.sendMessage(prefix+noperms1);
        
      }else{
            String help = prefix+ChatColor.RED+"Available commands: reload (reload configs)";
      if (args.length < 1 ) {
          p.sendMessage(prefix+ ChatColor.RED + "Usage: /"+cmd + " <command>");
          p.sendMessage(help);
        }else{
          //reload
             if (!p.hasPermission("command.proxycommands.reload")) {
         String noperms1 = noperms.replace("/permission/", "command.proxycommands.reload");
        p.sendMessage(prefix+noperms1);
          } else {
         if (args[0].equalsIgnoreCase("reload")) {
          p.sendMessage(prefix+ChatColor.RED+"ProxyCommands: Reloading Config.yml and Messages.yml...");
          this.plugin.getConfig().reloadConfig();
          this.plugin.getMessages().reloadConfig();
         } else { 
         p.sendMessage(help); 
        }
       }
      }
      }
    }
  }

}