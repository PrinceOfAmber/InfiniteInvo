package infiniteinvo.core.proxies;

import infiniteinvo.core.ModMutatedInventory;
import infiniteinvo.core.ModSettings;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
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
		ItemStack item = null;
		
		if(message.tags.getInteger("sort") == ModMutatedInventory.SORT_LEFT)
		{
			//0 to 8 is crafting
			//armor is 384-387
			for(int i = 9; i < invo.getSizeInventory()-4;i++)//388-4 384
			{
				System.out.println(i+"");
				
				item = invo.getStackInSlot(i);
				if(item == null)
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
						System.out.println(i+" swap to "+iEmpty +"__" +item.getDisplayName());
						
						invo.setInventorySlotContents(iEmpty, invo.getStackInSlot(i));

						invo.setInventorySlotContents(i, null);
						
						iEmpty = i;					
					}
				}
			}
		}
		else if(message.tags.getInteger("sort") == ModMutatedInventory.SORT_RIGHT)
		{
			System.out.println("# columns = "+ModSettings.fullCols);
			
			for(int i = 9; i < invo.getSizeInventory()-4;i++)//388-4 384
			{
				if(i % ModSettings.fullCols == 0)
				{

					System.out.println("this is end slate = "+i);
					item = invo.getStackInSlot(i);
					if(item != null)
					{
						System.out.println("__" +item.getDisplayName());
						
					}
				}
			}
		}
		
		
		return null;
	
	}
}
