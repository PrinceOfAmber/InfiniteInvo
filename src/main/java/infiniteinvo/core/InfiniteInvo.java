package infiniteinvo.core;

import infiniteinvo.core.proxies.CommonProxy;
import infiniteinvo.handlers.ConfigHandler; 
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = InfiniteInvo.MODID, useMetadata=true/*, version = InfiniteInvo.VERSION, name = InfiniteInvo.NAME, guiFactory = "infiniteinvo.handlers.ConfigGuiFactory"*/)
public class InfiniteInvo
{
    public static final String MODID = "infiniteinvomutated";
    public static final String NAME = "InfiniteInvo";
    
    //My fork of this mod was created on July 17, 2015 at https://github.com/PrinceOfAmber/InfiniteInvo
    //original mod source was https://github.com/Funwayguy/InfiniteInvo
	
	@Instance(MODID)
	public static InfiniteInvo instance;
	
	@SidedProxy(clientSide = "infiniteinvo.core.proxies.ClientProxy", serverSide = "infiniteinvo.core.proxies.CommonProxy")
	public static CommonProxy proxy;
	public SimpleNetworkWrapper network ;
	public static Logger logger;

    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    	
    	ConfigHandler.config = new Configuration(event.getSuggestedConfigurationFile(), true);
    	ConfigHandler.initConfigs();
    	
    	proxy.registerHandlers();
    }
}
