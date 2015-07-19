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
    public GuiButtonSort(int buttonId, int x, int y, int w,int h, EntityPlayer player)
    {
    	super(buttonId, x, y, w,h, StatCollector.translateToLocal(ModMutatedInventory.MODID+".sort"));
    	this.player = player;
    }
    /*
    @SideOnly(Side.CLIENT)
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
    	//we could add stuff to the button here dyanmically if we want
    	// this.displayString += "button text";
    	 
    	 //
    	 
    	 super.drawButton(mc, mouseX, mouseY);
    }
    */

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
    		tags.setInteger("world", this.player.worldObj.provider.getDimensionId());
    		tags.setString("player", this.player.getName());
    		ModMutatedInventory.instance.network.sendToServer(new SortButtonPacket(tags));
    	}
    	
    	return pressed;
    }
}

