package infiniteinvo.client.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiButtonSam extends GuiButton 
{
	//imported from https://github.com/PrinceOfAmber/SamsPowerups , author Lothrazar aka Sam Bassett
	private EntityPlayer player;
    public GuiButtonSam(int buttonId, int x, int y, int w,int h,String buttonText, EntityPlayer player)
    {
    	super(buttonId, x, y, w,h, buttonText);
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
    	//	System.out.println("client side btn pressed");
    		//send packet to server from client (this) makes sense
    		NBTTagCompound tags = new NBTTagCompound();
    		tags.setInteger("world", this.player.worldObj.provider.getDimensionId());
    		tags.setString("player", this.player.getName());
    		//ModInvCrafting.instance.network.sendToServer(new ButtonPacket(tags));
    	}
    	
    	return pressed;
    }
}

