package infiniteinvo.core.proxies;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SortButtonPacket implements IMessage , IMessageHandler<SortButtonPacket, IMessage>
{
	public SortButtonPacket() {}
	NBTTagCompound tags = new NBTTagCompound();
	public static final int ID = 2;
	
	public SortButtonPacket(NBTTagCompound ptags)
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
	public IMessage onMessage(SortButtonPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
 
		InventoryPlayer invo = p.inventory;
		
		int iEmpty = -1;
		for(int i = 0; i < invo.getSizeInventory();i++)
		{
			System.out.println(i+"");
			
			
			if(invo.getStackInSlot(i) == null)
			{
				System.out.println(i+" is empty");
				iEmpty = i;
			}
			else
			{
				//i is not empty
				
				if(iEmpty > 0)
				{
					//move i into iEmpty
					System.out.println(i+" swap to "+iEmpty);
					
					invo.setInventorySlotContents(iEmpty, invo.getStackInSlot(i));

					invo.setInventorySlotContents(i, null);
					
					iEmpty = i;					
				}
			}
		}
		
		return null;
	
	}
}
