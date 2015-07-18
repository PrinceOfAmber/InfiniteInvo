package infiniteinvo.core;

import infiniteinvo.core.proxies.ButtonPacket;
import infiniteinvo.core.proxies.CommonProxy; 
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
    	network.registerMessage(ButtonPacket.class, ButtonPacket.class, ButtonPacket.ID, Side.SERVER);
    	
    	config = new Configuration(event.getSuggestedConfigurationFile(), true);
    	config.load();
    	 
		ModSettings.MORE_ROWS = config.getInt("Extra Rows", Configuration.CATEGORY_GENERAL, 12, 0, 20, "How many extra rows are displayed in the inventory screen");
		ModSettings.MORE_COLS = config.getInt("Extra Columns", Configuration.CATEGORY_GENERAL, 16, 0, 20, "How many extra columns are displayed in the inventory screen");
		
		
		ModSettings.fullCols = 9 + ModSettings.MORE_COLS;
		ModSettings.fullRows = 3 + ModSettings.MORE_ROWS;
		ModSettings.invoSize  = ModSettings.fullCols * ModSettings.fullRows;
	 
		config.save();
		
		ModSettings.SaveToCache();
		
    	
    	proxy.registerHandlers();
    }
}
