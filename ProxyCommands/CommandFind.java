package ProxyCommands;

import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.Set;
import main.ProxyPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class CommandFind
  extends Command
  implements TabExecutor
{
  private final String cmd;
  
  String msg = ChatColor.GRAY+"//";
  
  public CommandFind(ProxyPlugin plugin, String cmd)
  {
    super(cmd);
    this.cmd = cmd;
  }
  
  @Override
  public void execute(CommandSender p, String[] args) {
      
    //command.find//  
        if (this.cmd.equalsIgnoreCase("find")) {
            ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(p.getName());
            String noperms = msg+ ChatColor.RED + "No Perms! (command.find)";
            if (!p.hasPermission("command.find")) {
           p.sendMessage(noperms);
           return;
           }
            if (args.length != 1) {
           p.sendMessage(msg+ ChatColor.RED + "Usage: /"+cmd+" <player>");
           return;
         }
            if (args.length > 0) {
            ProxiedPlayer p2 = ProxyServer.getInstance().getPlayer(args[0]);
             if ( !( p instanceof ProxiedPlayer ) ) {
               if (p2 != null){
                String p2server = p2.getServer().getInfo().getName();
                TextComponent message = new TextComponent(ChatColor.YELLOW + p2.getDisplayName() + " is on the " + p2server + " server.");
                p.sendMessage(message);
             return;
             } else {
               p.sendMessage(msg+ ChatColor.RED + "Could not find " + args[0] + ".");
               }
             }
         if (p2 ==null){p.sendMessage(msg+ ChatColor.RED + "Could not find " + args[0] + ".");}else{            
         if (!p2.getName().equals(p1.getName())) {
         findPlayer(p1, p2);
        }else{
        p.sendMessage(msg+ ChatColor.RED + "Have you gone and lost yourself, already?");
        }
       }
      }
    }
   //end find command code
  }
  //end all command code

//backend findPlayer code//
    public void findPlayer(ProxiedPlayer p1, ProxiedPlayer p2) {
                String p1server = p1.getServer().getInfo().getName();
                String p2server = p2.getServer().getInfo().getName();
                                          
          if (p2server.equals(p1server)) {
 p1.sendMessage(new TextComponent(msg + ChatColor.RED+p2.getDisplayName()+" is on the same server as you!"));
 } else {         
TextComponent message = new TextComponent(ChatColor.YELLOW + p2.getDisplayName() + " is on the " + p2server + " server.");
  message.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.YELLOW + "Click this message to join " + p2.getDisplayName() + " on the " + p2server+ " server!" ).create() ));
  message.setClickEvent(new ClickEvent (ClickEvent.Action.RUN_COMMAND, "/server " + p2server));
   p1.sendMessage(message);
  }
}
//end findPlayer()
  
  //autotab for names//
  @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
    if (args.length > 1 || args.length == 0) {
      return ImmutableSet.of(); 
    }
    Set matches = new HashSet();
    if (args.length == 1 && ((cmd.equalsIgnoreCase("find")))) {
        if (sender.hasPermission("command.find")){
      for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
        if (player.getDisplayName().toLowerCase().startsWith(args[0].toLowerCase())) {
          matches.add(player.getDisplayName());
        }
      }
   }
    }
    return matches;
  }
  //end autotab 
 }