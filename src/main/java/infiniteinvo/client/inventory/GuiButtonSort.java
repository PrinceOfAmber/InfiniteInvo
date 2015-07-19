package infiniteinvo.client.inventory;

import infiniteinvo.core.ModMutatedInventory;
import infiniteinvo.core.proxies.EnderButtonPacket;
import infiniteinvo.core.proxies.SortButtonPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiButtonSort extends GuiButton 
{
	//imported from https://github.com/PrinceOfAmber/SamsPowerups , author Lothrazar aka Sam Bassett
	private EntityPlayer player;
	private int sortType;
    public GuiButtonSort(int buttonId, int x, int y, int w,int h, EntityPlayer player, int sort, String text)
    {
    	super(buttonId, x, y, w,h, text );//StatCollector.translateToLocal(ModMutatedInventory.MODID+".sort")
    	this.player = player;
    	sortType = sort;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
    	boolean pressed = super.mousePressed(mc, mouseX, mouseY);
    	
    	if(pressed)
    	{
    		//do what the button is meant to do
    	
    		//send packet to server from client (this) makes sense
    		NBTTagCompound tags = new NBTTagCompound();
  
    		tags.setInteger(SortButtonPacket.NBT_SORT, sortType);
    		ModMutatedInventory.instance.network.sendToServer(new SortButtonPacket(tags));
    	}
    	
    	return pressed;
    }
}

