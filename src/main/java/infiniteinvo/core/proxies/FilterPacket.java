package infiniteinvo.core.proxies;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FilterPacket implements IMessage , IMessageHandler<FilterPacket, IMessage>
{
	public FilterPacket() {}
	NBTTagCompound tags = new NBTTagCompound();
	public static final int ID = 3;
	
	public FilterPacket(NBTTagCompound ptags)
	{
		tags = ptags;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		tags = ByteBufUtils.readTag(buf);

	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeTag(buf, this.tags);
	}


	@Override
	public IMessage onMessage(FilterPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;

		System.out.println("filter");
		//p.displayGUIChest(p.getInventoryEnderChest());
		
		return null;
	
	}
}
