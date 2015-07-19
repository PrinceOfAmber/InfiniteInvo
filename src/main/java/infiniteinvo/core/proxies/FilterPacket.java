package infiniteinvo.core.proxies;

import java.util.ArrayList;

import infiniteinvo.core.ModMutatedInventory;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
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

		ArrayList<BlockPos> b = ModMutatedInventory.findBlocks(p, Blocks.chest, 12);
		
		for(BlockPos pos : b)
		{
			if(p.worldObj.getTileEntity(pos) instanceof TileEntityChest)
				ModMutatedInventory.sortFromPlayerToChestEntity(p.worldObj, (TileEntityChest)p.worldObj.getTileEntity(pos), p);
			
		}
		
		
		return null;
	
	}
	
	
	
  	
	
}
