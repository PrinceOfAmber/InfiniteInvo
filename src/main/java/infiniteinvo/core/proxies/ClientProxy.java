package infiniteinvo.core.proxies;

import infiniteinvo.core.InfiniteInvo;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{
	@Override
	public boolean isClient()
	{
		return true;
	}
	
	@Override
	public void registerHandlers()
	{
		super.registerHandlers();
    	
    	InfiniteInvo.instance.network.registerMessage(InvoPacket.HandleClient.class, InvoPacket.class, 0, Side.CLIENT);
	}
}
