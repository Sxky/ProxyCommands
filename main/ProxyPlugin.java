package main;

import net.md_5.bungee.api.plugin.Plugin;
import files.Config;
import files.Messages;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class ProxyPlugin
  extends Plugin {
    
    public static ProxyPlugin main;
    
    private Config config;
    private Messages messages;
    public int resource = 10578;
    private final String PName = "ProxyCommands";
    
    public boolean availableUpdate = false;
    public String content;
    
    @Override
  public void onEnable() {
      main = this;
      getProxy().registerChannel(PName);
      
       this.config = new Config(this);
       this.config.loadConfig();
       
      this.messages = new Messages(this);
       this.messages.loadConfig();
              
    if (this.config.updateCheck()) {
        System.out.println(PName+": Checking to see if there is a newer version...");
        System.out.println(PName+": You can disable this in the config.");
     checkForUpdate(false);
    }

    registerEvents();
    registerCommands();
  }
  
    @Override
  public void onDisable() {
   this.config.saveConfig();
   this.messages.saveConfig();
  }
  
  
  public void registerCommands() {
      
if (this.config.useMsg()) {   
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandMsg(this, "message"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandMsg(this, "ss"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandMsg(this, "socialspy"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandMsg(this, "msg"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandMsg(this, "tell"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandMsg(this, "w"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandMsg(this, "whisper"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandMsg(this, "tell"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandMsg(this, "m"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandMsg(this, "reply"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandMsg(this, "r"));
    }

    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandProxyCommands(this, "proxycommands"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandProxyCommands(this, "pc"));

    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandHelpOp(this, "helpop"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandAlert(this, "alert"));
 
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandHub(this, "hub"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandHub(this, "lobby"));
    
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandFind(this, "find"));
    
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandChat(this, "sc"));
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandChat(this, "staffchat"));
    
    getProxy().getPluginManager().registerCommand(this, new ProxyCommands.CommandGoTo(this, "goto"));
  }
  
  
  private void registerEvents() {
    getProxy().getPluginManager().registerListener(this, new ProxyListeners.Slots(main));
    getProxy().getPluginManager().registerListener(this, new ProxyListeners.StaffChatListener());
    
   if (this.getConfig().BlockSpamming()) {
    getProxy().getPluginManager().registerListener(this, new ProxyListeners.SpamChatListener(main));
   }
   
   if (this.getConfig().updateCheck()) {
    getProxy().getPluginManager().registerListener(this, new ProxyListeners.UpdateMessages(main));
   }
  }
  
  
  public void checkForUpdate(Boolean player) {
    String Version = getDescription().getVersion();
    try {
      HttpURLConnection con = (HttpURLConnection)new URL("http://www.spigotmc.org/api/general.php").openConnection();
      con.setDoOutput(true);
      con.setRequestMethod("POST");
      con.getOutputStream()
        .write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resource)
        .getBytes("UTF-8"));
       content = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
      
      if (Version.equalsIgnoreCase(content)) {
          if (!player) {
              System.out.println(PName+": The latest version is running!");
          }
      } else {
          this.availableUpdate = true;
          if (!player) {
              System.out.println(PName+": Found an Update! Go to the Spigot Site and download the new Update ("+content+")!");
              System.out.println("https://www.spigotmc.org/resources/"+PName.toLowerCase()+"."+resource+"/");
          }
       }
    }
    catch (IOException e) {
       getLogger().log(Level.WARNING, "Cannot connect to Spigot's WebAPI.  The server possibly does not have access to internet, or Spigot is down.", e);
    }
  }

    public Config getConfig() {
return this.config;
    }
    
    public Messages getMessages() {
return this.messages;
    }

}