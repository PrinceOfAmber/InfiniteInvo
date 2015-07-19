package infiniteinvo.client.inventory;

import infiniteinvo.core.ModMutatedInventory;
import infiniteinvo.core.proxies.EnderButtonPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiButtonEnderChest extends GuiButton 
{
	//imported from https://github.com/PrinceOfAmber/SamsPowerups , author Lothrazar aka Sam Bassett
	private EntityPlayer player;
    public GuiButtonEnderChest(int buttonId, int x, int y, int w,int h, EntityPlayer player)
    {
    	super(buttonId, x, y, w,h, StatCollector.translateToLocal("tile.enderChest.name"));
    	this.player = player;
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
    		//tags.setInteger("world", this.player.worldObj.provider.getDimensionId());
    		//tags.setString("player", this.player.getName());
    		ModMutatedInventory.instance.network.sendToServer(new EnderButtonPacket(tags));
    	}
    	
    	return pressed;
    }
}

