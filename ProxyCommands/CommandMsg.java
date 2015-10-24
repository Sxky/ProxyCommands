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

public class CommandMsg
  extends Command
  implements TabExecutor
{
  private final ProxyPlugin plugin;
  private static final WeakHashMap<String, String> reply = new WeakHashMap();
  private static final WeakHashMap<String, String> spy = new WeakHashMap();
  private final String cmd;
  String msg = ChatColor.GRAY + "//";
  
  public CommandMsg(ProxyPlugin plugin, String cmd)
  {
    super(cmd);
    this.cmd = cmd;
    this.plugin = plugin;
  }
  
  public void execute(CommandSender p, String[] args)
  {
    if ((this.cmd.equalsIgnoreCase("message")) || (this.cmd.equalsIgnoreCase("msg")) || 
      (this.cmd.equalsIgnoreCase("tell")) || (this.cmd.equalsIgnoreCase("whisper")) || 
      (this.cmd.equalsIgnoreCase("w")) || (this.cmd.equalsIgnoreCase("m")))
    {
      if (!(p instanceof ProxiedPlayer))
      {
        p.sendMessage(ChatColor.RED + "Only in game players can use this command");
        return;
      }
      if (!p.hasPermission("command.msg"))
      {
        p.sendMessage(this.msg + ChatColor.RED + "No Perms! (command.msg)");
      }
      else
      {
        ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(p.getName());
        if (args.length < 2)
        {
          p.sendMessage(this.msg + ChatColor.RED + "Usage: /" + this.cmd + " <player> <message>");
        }
        else
        {
          ProxiedPlayer p2 = ProxyServer.getInstance().getPlayer(args[0]);
          if (p2 == null)
          {
            p.sendMessage(this.msg + ChatColor.RED + "Could not find " + args[0] + ".");
          }
          else if (!p2.getName().equals(p1.getName()))
          {
            reply.put(p1.getName(), p2.getName());
            
            StringBuilder sentmsg = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
              sentmsg.append(args[i]).append(" ");
            }
            message(p1, p2, sentmsg.toString());
          }
          else
          {
            p.sendMessage(this.msg + ChatColor.RED + "Why would you want to message yourself?");
          }
        }
      }
    }
    if ((this.cmd.equalsIgnoreCase("ss")) || (this.cmd.equalsIgnoreCase("socialspy")))
    {
      if (!(p instanceof ProxiedPlayer))
      {
        p.sendMessage(ChatColor.RED + "Only in game players can use this command");
        return;
      }
      if (p.hasPermission("command.ss"))
      {
        ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(p.getName());
        String ss = (String)spy.get(p1.getUUID());
        if (!"true".equals(ss))
        {
          spy.put(p1.getUUID(), "true");
          p.sendMessage(this.msg + ChatColor.RED + "Social Spy enabled!");
        }
        else
        {
          spy.put(p1.getUUID(), "false");
          p.sendMessage(this.msg + ChatColor.RED + "Social Spy disabled!");
        }
      }
      else
      {
        p.sendMessage(this.msg + ChatColor.RED + "No Perms! (command.ss)");
        return;
      }
    }
    if ((this.cmd.equalsIgnoreCase("r")) || (this.cmd.equalsIgnoreCase("reply")))
    {
      if (!(p instanceof ProxiedPlayer))
      {
        p.sendMessage(ChatColor.RED + "Only in game players can use this command");
        return;
      }
      if (p.hasPermission("command.reply"))
      {
        if (args.length < 1)
        {
          p.sendMessage(this.msg + ChatColor.RED + "Usage: /" + this.cmd + " <message>");
          return;
        }
        ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(p.getName());
        ProxiedPlayer p2 = ProxyServer.getInstance().getPlayer((String)reply.get(p1.getName()));
        if (p2 == null)
        {
          p1.sendMessage(this.msg + ChatColor.RED + "You don't have anyone to reply to.");
          return;
        }
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
          msg.append(args[i]).append(" ");
        }
        message(p1, p2, msg.toString());
      }
      else
      {
        p.sendMessage(this.msg + ChatColor.RED + "No perms. (command.reply)");
        return;
      }
    }
  }
  
  public void message(ProxiedPlayer p1, ProxiedPlayer p2, String msg)
  {
    String p1server = p1.getServer().getInfo().getName();
    String p2server = p2.getServer().getInfo().getName();
    
    TextComponent player1 = new TextComponent(p1.getDisplayName());
    player1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.BLUE + p1.getDisplayName() + " appears to be on the " + p1server + " server." + ChatColor.YELLOW + "  Click here to join!").create()));
    player1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/goto " + p1.getName()));
    player1.setColor(ChatColor.GRAY);
    
    TextComponent player2 = new TextComponent(p2.getDisplayName());
    player2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.BLUE + p2.getDisplayName() + " appears to be on the " + p2server + " server." + ChatColor.YELLOW + " Click here to join!").create()));
    player2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/goto " + p2.getName()));
    
    String coloredmsg = ChatColor.translateAlternateColorCodes('&', msg.trim());
    
    ProxyServer.getInstance().getConsole().sendMessage(p1.getServer().getInfo().getName() + " " + p1.getDisplayName() + " > " + p2.getServer().getInfo().getName() + " " + p2.getDisplayName() + ": " + msg);
    for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers())
    {
      String ss = (String)spy.get(p.getUUID());
      if (ss == "true") {
        if ((!p2.getName().equalsIgnoreCase(p.getName())) && (!p1.getName().equalsIgnoreCase(p.getName())))
        {
          TextComponent spymsg = new TextComponent(ChatColor.DARK_GRAY + "º");
          spymsg.addExtra(player1);
          player1.setColor(ChatColor.DARK_GRAY);
          spymsg.addExtra("➟");
          spymsg.addExtra(player2);
          player2.setColor(ChatColor.DARK_GRAY);
          spymsg.addExtra(" " + msg);
          spymsg.setItalic(Boolean.TRUE);
          spymsg.setColor(ChatColor.DARK_GRAY);
          p.sendMessage(spymsg);
        }
      }
    }
    if (p2server == p1server)
    {
      p1.sendMessage(new TextComponent(ChatColor.GRAY + "Me" + ChatColor.DARK_GRAY + "➟" + ChatColor.GRAY + p2.getDisplayName() + ChatColor.DARK_GRAY + " " + coloredmsg));
      p2.sendMessage(new TextComponent(ChatColor.GRAY + p1.getDisplayName() + ChatColor.DARK_GRAY + "➟" + ChatColor.GRAY + "Me" + ChatColor.DARK_GRAY + " " + coloredmsg));
    }
    else
    {
      TextComponent message = new TextComponent(ChatColor.GRAY + "Me" + ChatColor.DARK_GRAY + "➟");
      message.addExtra(player2);
      player2.setItalic(Boolean.TRUE);
      player2.setColor(ChatColor.GRAY);
      message.addExtra(ChatColor.DARK_GRAY + " " + coloredmsg + "");
      p1.sendMessage(message);
      
      TextComponent message1 = new TextComponent("");
      message1.addExtra(player1);
      player1.setItalic(Boolean.TRUE);
      player2.setColor(ChatColor.GRAY);
      message1.addExtra(ChatColor.DARK_GRAY + "➟" + ChatColor.GRAY + "Me " + ChatColor.DARK_GRAY + coloredmsg);
      p2.sendMessage(message1);
    }
  }
  
  public Iterable<String> onTabComplete(CommandSender sender, String[] args)
  {
    if ((args.length > 1) || (args.length == 0)) {
      return ImmutableSet.of();
    }
    Set matches = new HashSet();
    if ((args.length == 1) && ((!this.cmd.equalsIgnoreCase("r")) || (!this.cmd.equalsIgnoreCase("reply")))) {
      for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
        if (player.getDisplayName().toLowerCase().startsWith(args[0].toLowerCase())) {
          matches.add(player.getDisplayName());
        }
      }
    }
    return matches;
  }
}
