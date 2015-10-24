package ProxyCommands;

import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.Set;
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

public class MSGWIP
  extends Command
  implements TabExecutor
{
private final main.ProxyPlugin plugin;

    private static final WeakHashMap<String, String> reply = new WeakHashMap();
    private static final WeakHashMap<String, String> spy = new WeakHashMap();

  private final String cmd;
  
  public MSGWIP(ProxyPlugin plugin, String cmd) {
    super(cmd);
    this.cmd = cmd;
    this.plugin = plugin;
  }
    
    @Override
  public void execute(CommandSender p, String[] args) {
       String prefix = ChatColor.translateAlternateColorCodes('&', this.plugin.getMessages().getGlobalPrefix());
       String noperms = ChatColor.translateAlternateColorCodes('&', this.plugin.getMessages().getNoPermsMessage());
       
    //command.msg//
        if ((this.cmd.equalsIgnoreCase("message"))||(this.cmd.equalsIgnoreCase("msg"))
              ||(this.cmd.equalsIgnoreCase("tell"))||(this.cmd.equalsIgnoreCase("whisper"))
                  ||(this.cmd.equalsIgnoreCase("w"))||(this.cmd.equalsIgnoreCase("m"))){
            
            if ( !( p instanceof ProxiedPlayer ) )
            {
                p.sendMessage( ChatColor.RED + "Only in game players can use this command" );
                return;
            }
            String noperms1 = noperms.replaceAll("/permission/", "command.msg");
        if (!p.hasPermission("command.msg")) {
        p.sendMessage(prefix+noperms1);
        
      }else{
      
      ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(p.getName());
      
      if (args.length < 2) {
          p.sendMessage(prefix+ ChatColor.RED + "Usage: /"+cmd+" <player> <message>");
        }else{
          ProxiedPlayer p2 = ProxyServer.getInstance().getPlayer(args[0]);
          
          
         if (p2 ==null){p.sendMessage(prefix+ ChatColor.RED + "Could not find " + args[0] + ".");}else{
            
         if (!p2.getName().equals(p1.getName())) {
            
          reply.put(p1.getName(), p2.getName());
          StringBuilder sentmsg;
               sentmsg = new StringBuilder();
               
          for (int i = 1; i < args.length; i++) {
            sentmsg.append(args[i]).append(" ");
         }
         message(p1, p2, sentmsg.toString());
         
        }else{
        p.sendMessage(prefix+ ChatColor.RED + "Why would you want to message yourself?");
        }
       }
      }
      }
    }
   //end message command code
      
        

  //command.ss//      
    if ((this.cmd.equalsIgnoreCase("ss") || (this.cmd.equalsIgnoreCase("socialspy")))) {  
          if ( !( p instanceof ProxiedPlayer ) )
            {
                p.sendMessage( ChatColor.RED + "Only in game players can use this command" );
                return;
            }
       if (p.hasPermission("command.ss")) {
           ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(p.getName());    
      String ss = spy.get(p1.getUUID());
     if (!"true".equals(ss)) {
         spy.put(p1.getUUID(), "true");
     p.sendMessage(prefix + ChatColor.RED + "Social Spy enabled!"); 
        }else{
         spy.put(p1.getUUID(), "false");
     p.sendMessage(prefix + ChatColor.RED + "Social Spy disabled!");
     }
       }else{
           String noPerms1 = noperms.replace("/permission/", "command.ss");
       p.sendMessage(prefix+noPerms1);
       return;
       }
    }
    //end ss code
    
  //command.reply//  
    if ((this.cmd.equalsIgnoreCase("r")) || (this.cmd.equalsIgnoreCase("reply"))) {
                  if ( !( p instanceof ProxiedPlayer ) )
            {
                p.sendMessage( ChatColor.RED + "Only in game players can use this command" );
                return;
            }
                  
    if (p.hasPermission("command.reply")){
      if (args.length < 1) {
        p.sendMessage(prefix+ ChatColor.RED + "Usage: /"+cmd+" <message>");
        return;
      }
      else {
        ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(p.getName());
        ProxiedPlayer p2 = ProxyServer.getInstance().getPlayer(reply.get(p1.getName()));
        if (p2 == null) {
          p1.sendMessage(prefix+ ChatColor.RED + "You don't have anyone to reply to.");
          return;
        }
        else {
          StringBuilder msg = new StringBuilder();
          for (int i = 0; i < args.length; i++) {
            msg.append(args[i]).append(" ");
          }
         message(p1, p2, msg.toString());
        }
      }
    }else{
        String noPerms1 = noperms.replace("/permission/", "command.reply");
        p.sendMessage(prefix+noPerms1);
    return;
    }
  }
    //end reply

    }
 
  //end all command code

//backend message code//
    public void message(ProxiedPlayer p1, ProxiedPlayer p2, String  msg) {
                String p1server = p1.getServer().getInfo().getName();
                String p2server = p2.getServer().getInfo().getName();
            String coloredmsg = ChatColor.translateAlternateColorCodes('&', msg.trim());
            
          TextComponent player1 = new TextComponent(p1.getDisplayName() );
              player1.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.BLUE + p1.getDisplayName()+" appears to be on the "+p1server+" server." + ChatColor.YELLOW + "  Click here to join!").create() ) );
              player1.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/goto " + p1.getName() ) );
          String ply1 = player1.toString();
          TextComponent player2 = new TextComponent(p2.getDisplayName() );
              player2.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.BLUE + p2.getDisplayName()+" appears to be on the "+p2server+" server." + ChatColor.YELLOW + " Click here to join!").create() ) );
              player2.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/goto " + p2.getName() ) );
              
          String ply2 = player2.toString();
  String SSsender0 = ChatColor.translateAlternateColorCodes('&',  this.plugin.getMessages().getMessageFormat("SSsender"));
        String aa = SSsender0.replace("/sender/", p1.getDisplayName());
        String ab = aa.replace("/reciever/", p2.getDisplayName());
        TextComponent ac =  new TextComponent(ab.replace("/sender_with_server/", ply1) );
        TextComponent ad = new TextComponent (ac.toString().replace("/reciever_with_server/", ply2) );
          String SSsender1 = ad.toString().replace("/message/", coloredmsg);
  String SSreciever0 = ChatColor.translateAlternateColorCodes('&', this.plugin.getMessages().getMessageFormat("SSreciever"));
        String ba = SSreciever0.replace("/sender/", p1.getDisplayName());
        String bb = ba.replace("/reciever/", p2.getDisplayName());
        String bc = bb.replace("/sender_with_server/", ply1);
        String bd = bc.replace("/reciever_with_server/", ply2);
        String SSreciever1 = bd.replace("/message/", coloredmsg);  
  String DSsender0 = ChatColor.translateAlternateColorCodes('&', this.plugin.getMessages().getMessageFormat("DSsender"));
        String ca = DSsender0.replace("/sender/", p1.getDisplayName());
        String cb = ca.replace("/reciever/", p2.getDisplayName());
        String cc = cb.replace("/sender_with_server/", ply1);
        String cd = cc.replace("/reciever_with_server/", ply2);
        String DSsender1 = cd.replace("/message/", coloredmsg);
  String DSreciever0 = ChatColor.translateAlternateColorCodes('&', this.plugin.getMessages().getMessageFormat("DSreciever"));
        String da = DSreciever0.replace("/sender/", p1.getDisplayName());
        String db = da.replace("/reciever/", p2.getDisplayName());
        String dc = db.replace("/sender_with_server/", ply1);
        String dd = dc.replace("/reciever_with_server/", ply2);
        String DSreciever1 = dd.replace("/message/", coloredmsg);
 String SSformat0 = ChatColor.translateAlternateColorCodes('&', this.plugin.getMessages().getSocialSpyFormat());
        String ea = SSformat0.replace("/sender/", p1.getDisplayName());
        String eb = ea.replace("/reciever/", p2.getDisplayName());
        String ec = eb.replace("/sender_with_server/", ply1);
        String ed = ec.replace("/reciever_with_server/", ply2);
        String SSformat1 = ed.replace("/message/", coloredmsg);
        
 TextComponent SSsender = new TextComponent(SSsender1);
 TextComponent SSreciever = new TextComponent(SSreciever1);
 TextComponent DSsender = new TextComponent(DSsender1);
 TextComponent DSreciever = new TextComponent(DSreciever1);
 TextComponent SSformat = new TextComponent(SSformat1);
              
            for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                 String ss = (spy.get(p.getUUID()));
                if (ss=="true"){
if (! (p2.getName().equalsIgnoreCase(p.getName()) || p1.getName().equalsIgnoreCase(p.getName()) )){
    p.sendMessage(SSformat);
                }
          }
  ProxyServer.getInstance().getConsole().sendMessage(p1.getServer().getInfo().getName() + " " + p1.getDisplayName()+ " > " + p2.getServer().getInfo().getName() + " " + p2.getDisplayName()+": "+ msg);
    }                         
  if (p2server == p1server) { 
   p1.sendMessage(SSsender);
   p2.sendMessage(SSreciever);
  }else {
   p1.sendMessage(DSsender);
   p2.sendMessage(DSreciever);
  }
}
//end message()
  
    
  //autotab for names//
    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
    if (args.length > 1 || args.length == 0) {
      return ImmutableSet.of(); 
    }
    Set matches = new HashSet();
    if (args.length == 1 && ((!cmd.equalsIgnoreCase("r"))||(!cmd.equalsIgnoreCase("reply")))) {
      for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
        if (player.getDisplayName().toLowerCase().startsWith(args[0].toLowerCase())) {
          matches.add(player.getDisplayName());
        }
      }
    }
    return matches;
  }
 }