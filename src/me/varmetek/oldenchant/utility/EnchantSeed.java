package me.varmetek.oldenchant.utility;

import com.google.common.base.Preconditions;
import me.varmetek.oldenchant.OldEnchantPlugin;

import java.lang.reflect.Field;
import java.util.Random;


/***
 *  This class is used to manipulate the enchantment seed that a enchanting table uses.
 * */

public class EnchantSeed
{



  private final Random random = new Random();
  private final Object container;
  private final Field fSeed = ReflectionUtil.getField_TableSeed();




  public EnchantSeed (Object container){
    Preconditions.checkNotNull(container,"Container cannot be null");
    Preconditions.checkArgument(fSeed.getDeclaringClass().isAssignableFrom(container.getClass()));
    this.container = container;
  }


  /**
   * Returns the container object
   *
   * */
  public Object getContainer(){
    return container;
  }

  /**
   * Returns the seed currently used by the enchantment table.
   *
   * */
  public int get(){

    boolean access = fSeed.isAccessible();
    int result = 0;
    fSeed.setAccessible(true);
    try {
      result = fSeed.getInt(container);
    } catch (IllegalAccessException e) {
      OldEnchantPlugin.getPluginLog().severe("Could not retrieve enchantment seed");
    }
    fSeed.setAccessible(access);
    return result;

  }
  /**
   * Allows a direct change to the seed
   *
   *
   * @param seed the new seed for the enchanting table to use;
   * */

  public void set(int seed){
    boolean access = fSeed.isAccessible();

    fSeed.setAccessible(true);
    try {
      fSeed.setInt(container,seed);
    } catch (IllegalAccessException e) {
      OldEnchantPlugin.getPluginLog().severe("Could not set enchantment seed");
    }
    fSeed.setAccessible(access);

  }


  /***
   *
   * Random
   *
   * **/
  public int randomize(){
    int seed= random.nextInt();
    set(seed);
    return seed;
  }






}
