//    ProxyCommands, Plugin for md_5's BungeeCord found on http://www.spigotmc.org
//    Copyright (C) 2015  Tyler W. G. (Death4457)
//
//    This program (ProxyCommands) is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, version 3 of the License.
//
//    This program (ProxyCommands) is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    A copy of the GNU General Public License is contained in the default package of this
//    program (ProxyCommands), under the name "COPYING.LESSER"

package files;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.logging.Level;
import main.ProxyPlugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config
{
  private final ProxyPlugin plugin;
  private final File config;
  private final ConfigurationProvider yaml;
  
  //options
  private boolean useMsg = true;
  private boolean allowHubCommand = true;
  private String DefaultServer = "Hub";
  
  //chat
  private boolean BlockSpamming = true;
  private double SpamChatCooldown = 0.5;
  private double SpamCommandCooldown = 0.5;
  
  //slots
  private int ExtraSlots = 1;
  
  //update
  private boolean updateCheck = true;
  
  
  public Config(ProxyPlugin plugin)
  {
    this.plugin = plugin;
    this.config = new File(plugin.getDataFolder(), "Config.yml");
    this.config.getParentFile().mkdirs();
    this.yaml = ConfigurationProvider.getProvider(YamlConfiguration.class);
  }
  
  public void loadConfig()
  {
    try
    {
           if (!this.config.exists()) {
        Files.copy(this.plugin.getResourceAsStream("Config.yml"), this.config.toPath(), new CopyOption[0]);
      }
           else {
           reloadConfig();
    } 
    }
    catch (IOException e)
    {
      this.plugin.getLogger().log(Level.WARNING, "Cannot load configuration file.", e);
    }
  }
  
  public void saveConfig()
  {
    try
    {
      Configuration conf = this.yaml.load(this.config);
      this.yaml.save(conf, this.config);
    }
    catch (IOException e)
    {
      this.plugin.getLogger().log(Level.WARNING, "Cannot save configuration file.", e);
    }
  }
  
  public void reloadConfig()
  {
    try
    {
      Configuration conf = this.yaml.load(this.config);

      String pos = "options.UseMsg";
      if (conf.get(pos) == null) 
      { conf.set(pos,this.useMsg); } else {
      this.useMsg = conf.getBoolean(pos); }
      
      pos = "options.allow-hub-commands";
      if (conf.get(pos) == null)
      { conf.set(pos, this.allowHubCommand); } else {
      this.allowHubCommand = conf.getBoolean(pos); }
      
      pos = "options.default-server-name";
      if (conf.get(pos) == null) 
      { conf.set(pos, this.DefaultServer); } else {
      this.DefaultServer = conf.getString(pos); }
      
      pos = "chat.Disallow-Spam";
      if (conf.get(pos) == null)
      { conf.set(pos, this.BlockSpamming); } else {
      this.BlockSpamming = conf.getBoolean(pos); }
      
      pos = "chat.spam.ChatCooldown";
      if (conf.get(pos) == null)
      { conf.set(pos, this.SpamChatCooldown); } else {
      this.SpamChatCooldown = conf.getDouble(pos); }
      
       pos = "chat.spam.CommandCooldown";
      if (conf.get(pos) == null)
      { conf.set(pos, this.SpamCommandCooldown); } else {
      this.SpamCommandCooldown = conf.getDouble(pos); }
      
      pos = "slots.Added-Player-Slots";
      if (conf.get(pos) == null)
      { conf.set(pos, this.ExtraSlots); } else {
      this.ExtraSlots = conf.getInt(pos); }
      
      pos = "update.CheckForUpdate";
      if (conf.get(pos) == null)
      { conf.set(pos, this.updateCheck); } else {
      this.updateCheck = conf.getBoolean(pos); }
      
 
      this.yaml.save(conf, this.config);
    }
    catch (IOException e)
    {
       this.plugin.getLogger().log(Level.WARNING, "Could not load the config file!", e);
    }
  }
  
  
  public Configuration getRunningConfig()
  {
    try
    {
      return this.yaml.load(this.config);
    }
    catch (IOException e)
    {
      this.plugin.getLogger().log(Level.WARNING, "Could not load the config file!", e);
    }
    return null;
    }
  
    public boolean useMsg() 
    {
    return this.useMsg;
    }
  
    public boolean allowHubCommand()
    {
    return this.allowHubCommand;
    }
  
    public String getDefaultServer()
    {
    return this.DefaultServer;
    }
    
    public int getExtraSlots() 
    {
    return this.ExtraSlots;
    }
    
    public boolean updateCheck()
    {
    return this.updateCheck;
    }
    
    public boolean BlockSpamming()
    {
    return this.BlockSpamming;
    }
    
    public double getSpamCooldown(String type)
    {
    if (("Chat").equals(type)) {return this.SpamChatCooldown;} else
    if (("Command").equals(type)) {return this.SpamCommandCooldown;}
      return 0;
    }
    
}