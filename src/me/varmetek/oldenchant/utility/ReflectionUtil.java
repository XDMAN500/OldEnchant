package me.varmetek.oldenchant.utility;

import com.google.common.base.Preconditions;
import org.bukkit.Server;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectionUtil
{
  private ReflectionUtil(){throw new UnsupportedOperationException("Cannot instantiate ReflectionUtil");}


  private static  String NMS_ROOT, OBC_ROOT, VERSION;



  private static Field field_tableSeed;
  private static Field field_openContainer;
  private static Method method_getHandle;
  private static Class<?> clazz_EntityPlayer;
  private static Class<?> clazz_Container;
  private static Class<?> clazz_CraftPlayer;



  public static void init(Server server,ReflectionConfig config){
    VERSION = server.getClass().getName().split("\\.")[3];
    NMS_ROOT = "net.minecraft.server."+VERSION+".";
    OBC_ROOT = "org.bukkit.craftbukkit."+VERSION+".";

    clazz_CraftPlayer =   getObcClass("entity.CraftPlayer");
    clazz_Container = getNmsClass("ContainerEnchantTable");
    clazz_EntityPlayer = getNmsClass("EntityPlayer");

    method_getHandle = getMethod(clazz_CraftPlayer,"getHandle");
    field_openContainer = getField(clazz_EntityPlayer,config.getOpenContainerName());
    field_tableSeed =  getField(clazz_Container,config.getTableSeedName());

  }
  public static boolean isInitialized(){
    return VERSION != null && NMS_ROOT !=null &&OBC_ROOT != null;

  }

  public static void checkState(){
    Preconditions.checkState(isInitialized(),"Reflection Util is not initialized");
  }

  public static String getVersion(){

    return VERSION;
  }

  public static Field getField_TableSeed(){
    return field_tableSeed;
  }

  public static Field getField_openContainer(){
    return field_openContainer;
  }


  public static Method getMethod_getHandle(){
    return method_getHandle;
  }

  public static Class<?> getClass_EntityPlayer(){
    return clazz_EntityPlayer;
  }

  public static Class<?> getClass_CraftPlayer(){
    return clazz_CraftPlayer;
  }


  public static Class<?> getClass_Container(){
    return clazz_Container;
  }



  private static Class<?> getClass(String root,String clazz){
    checkState();
    try {
      return Class.forName(root+clazz);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  public static Class<?> getNmsClass(String clazz){
    checkState();
    return getClass(NMS_ROOT,clazz);
  }

  public static Class<?> getObcClass(String clazz){
    checkState();
    return getClass(OBC_ROOT,clazz);
  }


  public static  Field getField(Class<?> clazz ,String name){
    checkState();
    try {
      return clazz.getField(name);
    } catch (NoSuchFieldException e) {
      return null;
    }
  }

  public static Field getPrivateField(Class<?> clazz ,String name){
    checkState();
    try {
      return clazz.getDeclaredField(name);
    } catch (NoSuchFieldException e) {
      return null;
    }
  }


  public static Method getMethod(Class<?> clazz ,String name){
    checkState();
  try {
    return clazz.getMethod(name,null);
  } catch (NoSuchMethodException e) {
    return null;
  }
}

  public static Method getMethod(Class<?> clazz ,String name, Class<?>...parameters){
    checkState();
    try {
      return clazz.getMethod(name,parameters);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }
  public static Method getPrivateMethod(Class<?> clazz ,String name, Class<?>...parameters){
    checkState();
    try {
      return clazz.getDeclaredMethod(name,parameters);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }


  public static Constructor getConstructor(Class<?> clazz, Class<?>...parameters){
    checkState();
    try {
      return clazz.getConstructor(parameters);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }

  public static Constructor getPrivateConstructor(Class<?> clazz, Class<?>...parameters){
    checkState();
    try {
      return clazz.getDeclaredConstructor(parameters);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }




}
