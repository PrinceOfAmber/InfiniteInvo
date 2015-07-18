package infiniteinvo.core;

import net.minecraft.nbt.NBTTagCompound;

/**
 * A container for all the configurable settings in the mod
 */
public class ModSettings
{
	//public static NBTTagCompound cachedSettings = new NBTTagCompound();
	
	public static int invoSize = 52;

	public static int MORE_ROWS = 1;
	public static int MORE_COLS = 1;
/*
	public static void SaveToCache()
	{
		cachedSettings = new NBTTagCompound();
		cachedSettings.setInteger("invoSize", invoSize);
	}
	
	public static void LoadFromCache()
	{
		LoadFromTags(cachedSettings);
	}
	
	public static void LoadFromTags(NBTTagCompound tags)
	{
		invoSize = tags.getInteger("invoSize");
	}*/
}
