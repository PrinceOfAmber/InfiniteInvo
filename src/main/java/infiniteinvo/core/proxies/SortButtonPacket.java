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

	public static final String NBT_SORT = "sort";

	@Override
	public IMessage onMessage(SortButtonPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
 
		InventoryPlayer invo = p.inventory;
		int sortType = message.tags.getInteger(NBT_SORT);
		
		switch(sortType)
		{
		case ModMutatedInventory.SORT_LEFT:
			shiftLeftOne(invo);
			break;
		case ModMutatedInventory.SORT_RIGHT:
			shiftRightOne(invo);
			break;
		case ModMutatedInventory.SORT_LEFTALL:
			break;
		case ModMutatedInventory.SORT_RIGHTALL:
			break;
		}
	 

		/*
		int iEmpty = -1;
		ItemStack item = null;
		
		System.out.println("# columns = "+ModSettings.fullCols);
		
		for(int i = 9; i < invo.getSizeInventory()-4;i++)//388-4 384
		{
			if((i-9) % ModSettings.fullCols == 0)
			{

				item = invo.getStackInSlot(i);
				if(item != null)
				{
					System.out.println("this is end slate = "+i+"__" +item.getDisplayName());
					
				}
			}
		}*/
		
		return null;
	
	}
	int hotbarSize = 9;
	int armorSize = 4;
	private void shiftRightOne(InventoryPlayer invo) 
	{
		int iEmpty = -1;
		ItemStack item = null;
		//0 to 8 is crafting
		//armor is 384-387
		for(int i = invo.getSizeInventory() - (armorSize + 1); i >= hotbarSize;i--)//388-4 384
		{
			item = invo.getStackInSlot(i);
			if(item == null)
			{
				iEmpty = i;
			}
			else
			{
				if(iEmpty > 0)
				{
					//move i into iEmpty
					invo.setInventorySlotContents(iEmpty, invo.getStackInSlot(i));

					invo.setInventorySlotContents(i, null);
					
					iEmpty = i;					
				}
			}
		}
	}
	private void shiftLeftOne(InventoryPlayer invo) 
	{
		int iEmpty = -1;
		ItemStack item = null;
		//0 to 8 is crafting
		//armor is 384-387
		for(int i = hotbarSize; i < invo.getSizeInventory() - armorSize;i++)//388-4 384
		{
		
			item = invo.getStackInSlot(i);
			if(item == null)
			{
				iEmpty = i;
			}
			else
			{
				//i is not empty
				
				if(iEmpty > 0)
				{
					//move i into iEmpty
					invo.setInventorySlotContents(iEmpty, invo.getStackInSlot(i));

					invo.setInventorySlotContents(i, null);
					
					iEmpty = i;					
				}
			}
		}
	}
}
