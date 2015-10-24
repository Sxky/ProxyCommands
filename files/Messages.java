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

public class Messages {
  private final ProxyPlugin plugin;
  private final File messages;
  private final ConfigurationProvider yaml;
  
  //global
  private String prefix = "&7//";
  private String LackOfPermission = "&cInsufficient Permisions. (/permission/)";
  //alert
  private String NormalAlertStyle = "&f[&6&lAlert&r&f]&a /sender/&f: /message/";
  private String SpecialAlertStyleH = "/message/";
  private String SpecialAlertStyleS = "&f[&6&lAlert&r&f]&a /message/";
  private String SpecialAlertStyleG = "&a/sender/&f: /message/";
  //messages
  private String SSsender = "&7Me&8➟&7/reciever/ &8/message/";
  private String SSreciever = "&7/sender/&8➟&7Me &8/message/";
  private String DSsender = "&7Me&8➟&7&o/reciever_with_server/&r &8/message/";
  private String DSreciever = "&7&o/sender_with_server/&r&8➟&7Me &8/message/";
  private String SocialSpyformat = "&8º/sender_with_server/➟/reciever_with_server/ /message/";
  
  
  public Messages(ProxyPlugin plugin) {
    this.plugin = plugin;
    this.messages = new File(plugin.getDataFolder(), "Messages.yml");
    this.messages.getParentFile().mkdirs();
    this.yaml = ConfigurationProvider.getProvider(YamlConfiguration.class);
  }

  public void loadConfig() {
    try {
           if (!this.messages.exists()) {
        Files.copy(this.plugin.getResourceAsStream("Messages.yml"), this.messages.toPath(), new CopyOption[0]);
      } else {
           reloadConfig();
    } 
    }
    catch (IOException e) {
      this.plugin.getLogger().log(Level.WARNING, "Cannot load Messages file.", e);
    }
  }
  
  public void saveConfig() {
    try {
      Configuration conf = this.yaml.load(this.messages);
      this.yaml.save(conf, this.messages);
    }
    catch (IOException e) {
      this.plugin.getLogger().log(Level.WARNING, "Cannot save Messages file.", e);
    }
  }
  
  public void reloadConfig() {
    try {
      Configuration conf = this.yaml.load(this.messages);
      String pos;
      //Global
      pos = "Global.Prefix";
      if (conf.get(pos) == null) {
      conf.set(pos, this.prefix); } else {
      this.prefix = conf.getString(pos);
      }
      
      pos = "Global.No-Perms";
      if (conf.get(pos) == null) {
      conf.set(pos, this.LackOfPermission); } else {
      this.LackOfPermission = conf.getString(pos);
      }
      
      //Alert
      pos = "Alert.AlertStyle.Normal";
      if (conf.get(pos) == null) {
      conf.set(pos, this.NormalAlertStyle); } else {
      this.NormalAlertStyle = conf.getString(pos); 
      }
      
      pos = "Alert.AlertStyle.StyleH";
      if (conf.get(pos) == null)
      { conf.set(pos, this.SpecialAlertStyleH); } else {
      this.SpecialAlertStyleH = conf.getString(pos); 
      }
      
      pos = "Alert.AlertStyle.StyleS";
      if (conf.get(pos) == null) {
      conf.set(pos, this.SpecialAlertStyleS); } else {
      this.SpecialAlertStyleS = conf.getString(pos); 
      }
      
      pos = "Alert.AlertStyle.StyleG";
      if (conf.get(pos) == null) {
      conf.set(pos, this.SpecialAlertStyleG); } else {
      this.SpecialAlertStyleG = conf.getString(pos); 
      }
      
      pos = "Private-Messages.SameServer-Format.Sender";
      if (conf.get(pos) == null) {
      conf.set(pos, this.SSsender); } else {
      this.SSsender = conf.getString(pos); 
      }
      
      pos = "Private-Messages.SameServer-Format.Reciever";
      if (conf.get(pos) == null) {
      conf.set(pos, this.SSreciever); } else {
      this.SSreciever = conf.getString(pos); 
      }
      
      pos = "Private-Messages.DifferentServer-Format.Sender";
      if (conf.get(pos) == null) {
      conf.set(pos, this.DSsender); } else {
      this.DSsender = conf.getString(pos); 
      }
      
      pos = "Private-Messages.DifferentServer-Format.Reciever";
      if (conf.get(pos) == null) {
      conf.set(pos, this.DSreciever); } else {
      this.DSreciever = conf.getString(pos); 
      }
      
      pos = "Private-Messages.SocialSpy-Format";
      if (conf.get(pos) == null)
      { conf.set(pos, this.SocialSpyformat); } else {
      this.SocialSpyformat = conf.getString(pos); 
      }
 
      this.yaml.save(conf, this.messages);
    }
    catch (IOException e) {
       this.plugin.getLogger().log(Level.WARNING, "Could not load the Messages file!", e);
    }
  }
  

  public Configuration getRunningConfig() {
    try {
      return this.yaml.load(this.messages);
    }
    catch (IOException e) {
      this.plugin.getLogger().log(Level.WARNING, "Could not load the Messages file!", e);
    }
    return null;
    }
  
    public String getGlobalPrefix() {
    return this.prefix;
    }
    
    public String getNoPermsMessage() {
    return this.LackOfPermission;
    }
  
    public String getAlertStyle(String type) {
    if ("Normal".equals(type)) {return this.NormalAlertStyle;} else
    if ("G".equals(type)) {return this.SpecialAlertStyleG;} else
    if ("H".equals(type)) {return this.SpecialAlertStyleH;} else
    if ("S".equals(type)) {return this.SpecialAlertStyleS;} else
        return null;
    }
    
    public String getMessageFormat(String type) {
     if ("SSsender".equals(type)) {return this.SSsender;} else
     if ("SSreciever".equals(type)) {return this.SSreciever;} else
     if ("DSsender".equals(type)) {return this.DSsender;} else
     if ("DSreciever".equals(type)) {return this.DSreciever;} else
      return null;
    }
    
    public String getSocialSpyFormat() {
    return this.SocialSpyformat;
    }
     
}