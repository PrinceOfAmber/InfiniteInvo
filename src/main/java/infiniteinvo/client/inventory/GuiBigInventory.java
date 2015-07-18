package infiniteinvo.client.inventory;

import java.io.IOException;

import infiniteinvo.core.ModSettings;
import infiniteinvo.core.InfiniteInvo;
import infiniteinvo.inventory.BigContainerPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiBigInventory extends GuiInventory
{
	BigContainerPlayer container;

	public boolean redoButtons = false;
	public boolean showText = false;
	public boolean showCharacter = true;
	int xStart = 169;
	int yStart = 137;
	public static final int square = 18;
	public GuiBigInventory(EntityPlayer player)
	{
		super(player);
		container = player.inventoryContainer instanceof BigContainerPlayer? (BigContainerPlayer)player.inventoryContainer : null;
		this.xSize = xStart + (square * ModSettings.MORE_COLS) + 15;
		this.ySize = yStart + (square * ModSettings.MORE_ROWS) + 29;

	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("infiniteinvo", "textures/gui/inventory_gui_3.png"));
        int gLeft = this.guiLeft;
        int gTop = this.guiTop;
        this.drawTexturedModalRect(gLeft, gTop, 0, 0, xStart, yStart);
        
        for(int i = 0; i < ModSettings.MORE_COLS; i++)
        {
            this.drawTexturedModalRect(gLeft + xStart + (i * square), gTop, xStart, 0, square, yStart);
        }
        
        for(int i = 0; i < ModSettings.MORE_ROWS; i++)
        {
            this.drawTexturedModalRect(gLeft, gTop + yStart + (i * square), 0, 119, xStart, square);
        }
        
        for(int i = 0; i < ModSettings.MORE_COLS; i++)
        {
        	for(int j = 0; j < ModSettings.MORE_ROWS; j++)
        	{
                this.drawTexturedModalRect(gLeft + xStart + (i * square), gTop + yStart + (j * square), 7, 83, square, square);
        	}
        }
        
        int barW = (ModSettings.MORE_COLS + 9) * (ModSettings.MORE_ROWS + 3) < ModSettings.invoSize? 0 : 8;

        this.drawTexturedModalRect(gLeft + xStart + (ModSettings.MORE_COLS * square), gTop, 187, 0, 2, 119); // Scroll top
        this.drawTexturedModalRect(gLeft + xStart + (ModSettings.MORE_COLS * square) + 2, gTop, 189 + barW, 0, 13 - barW, 119); // Scroll top
        
        for(int i = 0; i < ModSettings.MORE_ROWS; i++)
        {
            this.drawTexturedModalRect(gLeft + xStart + (ModSettings.MORE_COLS * square), gTop + 119 + (i * square), 187, 101, 2, square); // Scroll middle
            this.drawTexturedModalRect(gLeft + xStart + (ModSettings.MORE_COLS * square) + 2, gTop + 119 + (i * square), 189 + barW, 101, 13 - barW, square); // Scroll middle
        }
        
        this.drawTexturedModalRect(gLeft + xStart + (ModSettings.MORE_COLS * square), gTop + 119 + (ModSettings.MORE_ROWS * square), 187, 119, 2, square); // Scroll bottom
        this.drawTexturedModalRect(gLeft + xStart + (ModSettings.MORE_COLS * square) + 2, gTop + 119 + (ModSettings.MORE_ROWS * square), 189 + barW, 119, 13 - barW, square); // Scroll bottom
        
        this.drawTexturedModalRect(gLeft, gTop + yStart + (ModSettings.MORE_ROWS * square), 0, yStart, xStart, 29);
        
        for(int i = 0; i < ModSettings.MORE_COLS; i++)
        {
            this.drawTexturedModalRect(gLeft + xStart + (i * square), gTop + yStart + (ModSettings.MORE_ROWS * square), xStart, yStart, square, 29);
        }
        
        this.drawTexturedModalRect(gLeft + xStart + (ModSettings.MORE_COLS * square), gTop + yStart + (ModSettings.MORE_ROWS * square), 187 + barW, yStart, 16 - barW, 29);

        if(showCharacter)
        	drawEntityOnScreen(gLeft + 51, gTop + 75, 30, (float)(gLeft + 51) - (float)mouseX, (float)(gTop + 75 - 50) - (float)mouseY, this.mc.thePlayer);
     
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
	{
		if(showText)
			this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 87, 32, 4210752);
		
		if(container != null)
		{
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(new ResourceLocation("infiniteinvo", "textures/gui/inventory_gui_3.png"));
			
	        int maxPos = MathHelper.ceiling_float_int((float)ModSettings.invoSize/(float)(9 + ModSettings.MORE_COLS)) - (3 + ModSettings.MORE_ROWS);
			int barPos = maxPos > 0? MathHelper.floor_float((float)container.scrollPos / (float)maxPos * (18F * (3F + (float)ModSettings.MORE_ROWS) - 8F)) : 0;
			
			if((ModSettings.MORE_COLS + 9) * (ModSettings.MORE_ROWS + 3) < ModSettings.invoSize)
			{
				this.drawTexturedModalRect(this.xSize - 13, 83 + barPos, 60, 166, 8, 8);
			}
	        
	        // Draw the empty/locked slot icons
	        for(int j = 0; j < 3 + ModSettings.MORE_ROWS; j++)
	        {
	        	for(int i = 0; i < 9 + ModSettings.MORE_COLS; i++)
	        	{
	        		if(i + (j + container.scrollPos) * (9 + ModSettings.MORE_COLS) >= ModSettings.invoSize)
	        		{
	        			this.drawTexturedModalRect(7 + i * square, 83 + j * square, 0, 166, square, square);
	        		} else if(i + (j + container.scrollPos) * (9 + ModSettings.MORE_COLS) >= container.invo.getUnlockedSlots() - 9)
	        		{
	        			this.drawTexturedModalRect(7 + i * square, 83 + j * square, square, 166, square, square);
	        		}
	        	}
	        }
		}
	}
    

	/**
	 * -1 = Dragging outside scroll, 0 = Not dragging, 1 = Dragging from scroll
	 */
	public int dragging = 0;
	
    public void handleMouseInput() throws IOException
    {
    	super.handleMouseInput();
    
    	if(container != null)
    	{
        	int scrollDir = (int)Math.signum(Mouse.getDWheel());
        	
        	if(container.scrollPos - scrollDir < 0)
        	{
        		container.scrollPos = 0;
        	} else if(scrollDir != 0)
        	{
        		container.scrollPos -= scrollDir;
        	} else if(Mouse.isButtonDown(0))
        	{
                final ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                int i = scaledresolution.getScaledWidth();
                int j = scaledresolution.getScaledHeight();
                int mouseX = Mouse.getX() * i / this.mc.displayWidth;
                int mouseY = height - Mouse.getY() * j / this.mc.displayHeight - 1;
        		int sx = this.guiLeft + xStart + (ModSettings.MORE_COLS * square);
        		int sy = this.guiTop + 83;
        		
        		boolean flag = mouseX >= sx && mouseY >= sy && mouseX < sx + 8 && mouseY < sy + (square * (3 + ModSettings.MORE_ROWS));
        		
        		if((flag || dragging == 1) && dragging != -1)
        		{
        			dragging = 1;
        			int maxScroll = MathHelper.ceiling_float_int((float)ModSettings.invoSize/(float)(9 + ModSettings.MORE_COLS)) - (3 + ModSettings.MORE_ROWS);
        			container.scrollPos = MathHelper.clamp_int(Math.round((float)(mouseY - sy) / (float)(square * (3 + ModSettings.MORE_ROWS)) * (float)maxScroll), 0, maxScroll);
        		} else
        		{
        			dragging = -1;
        		}
        	} else
        	{
        		dragging = 0;
        	}
        	
        	container.updateScroll();
    	}
    }
}
