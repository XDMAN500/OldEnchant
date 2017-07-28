package me.varmetek.oldenchant;

import com.google.common.base.Preconditions;
import me.varmetek.oldenchant.listener.EnchantListener;
import me.varmetek.oldenchant.utility.EnchantSeed;
import me.varmetek.oldenchant.utility.ReflectionConfig;
import me.varmetek.oldenchant.utility.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class OldEnchantPlugin extends JavaPlugin
{

  private static Logger log;


  private ReflectionConfig reflectionConfig;


 @Override
  public void onLoad() {
   log = this.getLogger();
   ReflectionUtil.init(Bukkit.getServer(),reflectionConfig = ReflectionConfig.COMMON);

   log.info("Running on Minecraft Version "+ ReflectionUtil.getVersion());
   log.severe("For any errors, contact the author (Varmetek) on spigot.org");
  }

  @Override
  public void onDisable() {
  }



  public ReflectionConfig getReflectionConfig(){
    return reflectionConfig;
  }

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(new EnchantListener(this),this);
  }



  public static Logger getPluginLog(){
    return log;
  }


  public static  Object getEntityPlayer(Player player){


    try {
      return ReflectionUtil.getMethod_getHandle().invoke(player);
    } catch (IllegalAccessException |InvocationTargetException e) {
      log.severe("Cannot retrieve nmsPlayer instance");
      return null;
    }



  }

  public static  Object getPlayerOpenContainer(Player player){
    return getPlayerOpenContainer(getEntityPlayer(player));
  }

  private  static Object getPlayerOpenContainer(Object entityPlayer){


    try {
      return ReflectionUtil.getField_openContainer().get(entityPlayer);
    } catch (IllegalAccessException e) {
      log.severe("Cannot find retrieve openContainer instance");
      return null;
    }


  }

  public static EnchantSeed getEnchantSeedInfo(Player player){

    Inventory inv = player.getOpenInventory().getTopInventory();
    Preconditions.checkNotNull(inv,"Inventory cannot be null");
    Preconditions.checkState(inv.getType() == InventoryType.ENCHANTING,"Player is not enchanting");
    Object entityPlayer =Preconditions.checkNotNull(getEntityPlayer(player),"Cannot find the player handle");
    Object container = Preconditions.checkNotNull(getPlayerOpenContainer(entityPlayer),"Cannot find the player's open container");

    return new EnchantSeed(container); 
  }
}
