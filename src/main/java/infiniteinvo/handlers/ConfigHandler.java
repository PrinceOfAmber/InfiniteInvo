package infiniteinvo.handlers;

import infiniteinvo.core.ModSettings;
import infiniteinvo.core.InfiniteInvo;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class ConfigHandler
{
	public static Configuration config;
	
	public static void initConfigs()
	{
		if(config == null)
		{
			InfiniteInvo.logger.log(Level.ERROR, "Config attempted to be loaded before it was initialised!");
			return;
		}
		
		config.load();
 
		ModSettings.MORE_ROWS = config.getInt("Extra Rows", Configuration.CATEGORY_GENERAL, 8, 0, 20, "How many extra rows are displayed in the inventory screen");
		ModSettings.MORE_COLS = config.getInt("Extra Columns", Configuration.CATEGORY_GENERAL, 20, 0, 10, "How many extra columns are displayed in the inventory screen");
		
		
		int fullCols = 9 + ModSettings.MORE_COLS;
		int fullRows = 3 + ModSettings.MORE_ROWS;
		ModSettings.invoSize  = fullCols*fullRows;
	 
		config.save();
		
		ModSettings.SaveToCache();
		
		InfiniteInvo.logger.log(Level.INFO, "Loaded configs...");
	}
}
