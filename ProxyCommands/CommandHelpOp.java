package ProxyCommands;

import java.util.WeakHashMap;
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

public class CommandHelpOp
  extends Command
  implements TabExecutor
{

  private final String cmd;
  
  String msg = ChatColor.GRAY+"//";
  
  public CommandHelpOp(ProxyPlugin plugin, String cmd)
  {
    super(cmd);
    this.cmd = cmd;
  }
  
    @Override
  public void execute(CommandSender p, String[] args) {
      
    //command.helpop//  
        if (this.cmd.equalsIgnoreCase("helpop")) {
            
            if ( !( p instanceof ProxiedPlayer ) )
            {
                p.sendMessage( ChatColor.RED + "Only in game players can use this command" );
                return;
            }
            
      if (!p.hasPermission("command.helpop")) {
        p.sendMessage(msg+ ChatColor.RED + "No Perms! (command.helpop)");
        
      }else{
      
      ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(p.getName());
      
      if (args.length < 1) {
          p.sendMessage(msg+ ChatColor.RED + "Usage: /"+cmd+" <message>");
        }else{          
          
          StringBuilder sentmsg;
               sentmsg = new StringBuilder();
               
          for (int i = 0; i < args.length; i++) {
            sentmsg.append(args[i]).append(" ");
         }
          p1.sendMessage(ChatColor.GREEN+ "Your message has been sent.");
         message(p1, sentmsg.toString());
         
        }
       
      }
      }
  }
    
   //end helpop command code
    

  //end all command code

//backend message code//
    public void message(ProxiedPlayer p1, String  msg) {   
        String p1server = p1.getServer().getInfo().getName();
            TextComponent player1 = new TextComponent(p1.getDisplayName() );
          player1.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.BLUE + p1.getDisplayName()+" appears to be on the "+p1server+" server." + ChatColor.YELLOW + "  Click here to join!").create() ) );
         player1.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/goto " + p1.getName() ) );
          player1.setColor(ChatColor.GREEN);
                                               
            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (p.hasPermission("command.helpop.op")) {
                    
                TextComponent message = new TextComponent(ChatColor.RED+"[HelpOp]");
                message.addExtra(player1);
                player1.setColor(ChatColor.GREEN);
                message.addExtra(": ");
                message.addExtra(msg);
                p.sendMessage(message);
                }
                
    }
                                                
//end message()
  

 }

    @Override
    public Iterable<String> onTabComplete(CommandSender cs, String[] strings) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}