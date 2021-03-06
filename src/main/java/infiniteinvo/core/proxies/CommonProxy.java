package infiniteinvo.core.proxies;

import infiniteinvo.core.EventHandler;
import infiniteinvo.core.ModMutatedInventory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void registerHandlers()
	{
		EventHandler handler = new EventHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);
	//	FMLCommonHandler.instance().bus().register(new II_UpdateNotification());
    	ModMutatedInventory.instance.network.registerMessage(InvoPacket.HandleServer.class, InvoPacket.class, InvoPacket.ID, Side.SERVER);
	}
}
