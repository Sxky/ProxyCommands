package ProxyCommands;

import main.ProxyPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class CommandAlert
    extends Command {
    
    private final main.ProxyPlugin plugin;

   
   private String cmd;   
   
  public CommandAlert(ProxyPlugin plugin, String cmd) {
    super(cmd);
    this.cmd = cmd;
    this.plugin = plugin;
  }
  
  public void execute(CommandSender p, String[] args) {
      String prefix0 = this.plugin.getMessages().getGlobalPrefix();
      String prefix = ChatColor.translateAlternateColorCodes('&', prefix0);
      
      String noperms0 = this.plugin.getMessages().getNoPermsMessage();
      String noperms = ChatColor.translateAlternateColorCodes('&', noperms0);
      
  if (cmd.equalsIgnoreCase("alert")){
      if (p.hasPermission("command.alert")){
    if (args.length == 0) {
      p.sendMessage(prefix+ ChatColor.RED+"/"+cmd+" [special(&(h|s|g))]<message>");
    }
    else
     if(args[0].equals("help")&&args.length==1) {
       p.sendMessage(prefix+ ChatColor.RED+"There are the multiple ways to run this command:");
      p.sendMessage(prefix+ ChatColor.RED+"/"+cmd+" <message>");
      p.sendMessage(prefix+ ChatColor.RED+"/"+cmd+" &h<message>");
      p.sendMessage(prefix+ ChatColor.RED+"/"+cmd+" &s<message>");
      p.sendMessage(prefix+ ChatColor.RED+"/"+cmd+" &g<message>");
     }
     else {
        String style;
        //h
      if (args[0].startsWith("&h")) {
          args[0] = args[0].substring(2, args[0].length());
       style = this.plugin.getMessages().getAlertStyle("H");
      }
      //s
      else if (args[0].startsWith("&s")) {
        args[0] = args[0].substring(2, args[0].length());
       style = this.plugin.getMessages().getAlertStyle("S");
      }
      //g
      else if (args[0].startsWith("&g")) {
       args[0] = args[0].substring(2, args[0].length());
      style = this.plugin.getMessages().getAlertStyle("G");
      }
      //normal
      else {
      style = this.plugin.getMessages().getAlertStyle("Normal");
      }
      //backend
      StringBuilder words = new StringBuilder();
      for (String s : args) {
        words.append(ChatColor.translateAlternateColorCodes('&', s));
        words.append(" ");
      }
      String message = words.substring(0, words.length() - 1);

     
        String replaceAll = style.replaceAll("/sender/", p.getName());
        String replaceAll1 = ChatColor.translateAlternateColorCodes('&', replaceAll);
        String replaceAll2= replaceAll1.replaceAll("/message/", message);
        
         String sendmessage = replaceAll2;
      ProxyServer.getInstance().broadcast(sendmessage);
    }
    
    
    //no perm
  } else {
          String noperms1 = noperms.replace("/permission/", "command.alert");
      p.sendMessage(prefix+noperms1);
      }
}
  
  
  
}
}