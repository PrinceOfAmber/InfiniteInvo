package infiniteinvo.core;

import infiniteinvo.core.proxies.EnderButtonPacket;
import infiniteinvo.core.proxies.CommonProxy; 
import infiniteinvo.core.proxies.SortButtonPacket;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = ModMutatedInventory.MODID, useMetadata=true)
public class ModMutatedInventory
{
    public static final String MODID = "infiniteinvomutated";
	public static final String NBT_PLAYER = "Player";
	public static final String NBT_WORLD = "World";
	public static final String NBT_ID = "ID";
	public static final String NBT_Settings = "Settings";
	public static final String NBT_Unlocked = "Unlocked";

	public final static int SORT_LEFT = 1;
	public final static int SORT_RIGHT = 2;
    //My fork of this mod was created on July 17, 2015 at https://github.com/PrinceOfAmber/InfiniteInvo
    //original mod source was https://github.com/Funwayguy/InfiniteInvo
	
	@Instance(MODID)
	public static ModMutatedInventory instance;
	
	@SidedProxy(clientSide = "infiniteinvo.core.proxies.ClientProxy", serverSide = "infiniteinvo.core.proxies.CommonProxy")
	public static CommonProxy proxy;
	public SimpleNetworkWrapper network ;
	public static Logger logger;
	public static Configuration config;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    	network.registerMessage(EnderButtonPacket.class, EnderButtonPacket.class, EnderButtonPacket.ID, Side.SERVER);
    	network.registerMessage(SortButtonPacket.class, SortButtonPacket.class, SortButtonPacket.ID, Side.SERVER);
    	
    	config = new Configuration(event.getSuggestedConfigurationFile(), true);
    	config.load();
    	 
    	String category = Configuration.CATEGORY_GENERAL;
		ModSettings.MORE_ROWS = config.getInt("extra_rows", category, 12, 0, 20, "How many extra rows are displayed in the inventory screen");
		ModSettings.MORE_COLS = config.getInt("extra_columns", category, 16, 0, 20, "How many extra columns are displayed in the inventory screen");
		
		
		ModSettings.fullCols = 9 + ModSettings.MORE_COLS;
		ModSettings.fullRows = 3 + ModSettings.MORE_ROWS;
		ModSettings.invoSize  = ModSettings.fullCols * ModSettings.fullRows;
		
		//(String name, String category, String defaultValue, String comment)
		ModSettings.showText = config.getBoolean("show_text",category,false,"Show or hide the 'Crafting' text in the inventory");
		ModSettings.showCharacter = config.getBoolean("show_character",category,true,"Show or hide the animated character text in the inventory");
		ModSettings.showEnderButton = config.getBoolean("button_ender_chest",category,true,"Show or hide the ender chest button");
		ModSettings.showSortButton = config.getBoolean("button_sort",category,true,"Show or hide the ender chest button");
		
		config.save();
		
		ModSettings.SaveToCache();
		
    	
    	proxy.registerHandlers();
    }
}
