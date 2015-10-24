package ProxyCommands;

import java.util.ArrayList;
import main.ProxyPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class CommandChat
  extends Command
  implements TabExecutor
{
  public static ArrayList<String> staffchat = new ArrayList();

  private final String cmd;
  
  String msg = ChatColor.GRAY+"//";
  
  public CommandChat(ProxyPlugin plugin, String cmd)
  {
    super(cmd);
    this.cmd = cmd;
  }
  
  @Override
  public void execute(CommandSender p, String[] args) {
      
    //command.staffchat//  
        if ((this.cmd.equalsIgnoreCase("sc"))||(this.cmd.equalsIgnoreCase("staffchat"))){
            
            if ( !( p instanceof ProxiedPlayer ) )
            {
                p.sendMessage( ChatColor.RED + "Only in game players can use this command" );
                return;
            }
            
      if (!p.hasPermission("command.staffchat")) {
        p.sendMessage(msg+ ChatColor.RED + "No Perms! (command.staffchat)");
      } else {
      
      ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(p.getName());
      
      if (args.length < 1) {
            if (!staffchat.contains(p1.getUUID())){
             staffchat.add(p1.getUUID());
             p1.sendMessage(msg + "You are now talking in StaffChat!");
            } else {
            staffchat.remove(p1.getUUID());
            p1.sendMessage(msg + "You are no longer talking in StaffChat!");
            }
      
      }else{
            
          StringBuilder message;
               message = new StringBuilder();
               
          for (int i = 0; i < args.length; i++) {
            message.append(args[i]).append(" ");
         }
         sChat(p1, message.toString());
       }
      }
      }
  }

   //end staffchat command code
   
  

//backend sChat code//
    public void sChat(ProxiedPlayer p1, String  msg) {                  
             String coloredmsg = ChatColor.translateAlternateColorCodes('&', msg.trim());
             
           for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
       if (p.hasPermission("command.staffchat")) {
           p.sendMessage(ChatColor.RED+"["+ChatColor.GREEN+"SC"+ChatColor.RED+"]" + ChatColor.GRAY + p1.getDisplayName()+ " " + coloredmsg);
       } 
    }             
  }

    @Override
    public Iterable<String> onTabComplete(CommandSender cs, String[] strings) {
        throw new UnsupportedOperationException();
    }
}