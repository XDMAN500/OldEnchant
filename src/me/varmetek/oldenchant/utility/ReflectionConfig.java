package me.varmetek.oldenchant.utility;

import java.util.logging.Logger;


/****
 *
 * To deal with incompatibilities this enum hold different options which have different
 * field names to choose from.
 *
 * **/
public enum ReflectionConfig
{

  /***
   * This holds the common field names used across multiple versions.
   * */
  COMMON("activeContainer","f");

  private final Logger log = Logger.getLogger("Minecraft");


  private  final String openContainerName, tableSeedName;

  ReflectionConfig (String activeContainer, String tableSeed){
   this.openContainerName = activeContainer;
   this.tableSeedName = tableSeed;
  }



  /***
   * returns the Field name to access the player's active inventory
   * **/
  public String getOpenContainerName(){
    return openContainerName;
  }



  /***
   * returns the Field name to access the enchantment table's enchanting seed
   * **/
  public String getTableSeedName(){
    return tableSeedName;
  }





}
