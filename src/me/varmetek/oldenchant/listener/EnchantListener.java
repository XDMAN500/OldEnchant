package me.varmetek.oldenchant.listener;

import me.varmetek.oldenchant.OldEnchantPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class EnchantListener implements Listener
{
  private OldEnchantPlugin plugin;
  private Set<UUID> resetLock = new HashSet<>();
  private Map<UUID,Integer> debt = new HashMap<>();
  public EnchantListener(OldEnchantPlugin plugin){
    this.plugin = plugin;
  }

  @EventHandler
  public void clickEvent(InventoryClickEvent ev){
    if(ev.getClickedInventory()== null
      || ev.getClickedInventory().getType() != InventoryType.ENCHANTING) return;

    resetLock.remove(ev.getWhoClicked().getUniqueId());

  }

  @EventHandler
  public void invCloseEvent(InventoryCloseEvent ev){
    resetLock.remove(ev.getPlayer().getUniqueId());
  }


  @EventHandler(priority = EventPriority.MONITOR)
  public void commitEnchant(EnchantItemEvent ev){
    Player player = ev.getEnchanter();
    int buffer = ev.whichButton()+1;
    if(ev.getExpLevelCost() <=buffer) return;

    int expLoss = ev.getExpLevelCost()-buffer;

    resetLock.remove(ev.getEnchanter().getUniqueId());
    debt.put(ev.getEnchanter().getUniqueId(),expLoss);

  }

  @EventHandler
  public void logout(PlayerQuitEvent ev){
    resetLock.remove(ev.getPlayer().getUniqueId());
  }


  @EventHandler
  public void kick(PlayerKickEvent ev){
    resetLock.remove(ev.getPlayer().getUniqueId());
  }



  @EventHandler
  public void prepEnchant(PrepareItemEnchantEvent ev){
    UUID id = ev.getEnchanter().getUniqueId();

    if(resetLock.contains(id)) return;
    if(debt.containsKey(id)){
      ((Player)ev.getEnchanter()).giveExpLevels(-debt.get(id));
      debt.remove(id);
    }
    OldEnchantPlugin.getEnchantSeedInfo(ev.getEnchanter()).randomize();

    resetLock.add(id);

  }


}
