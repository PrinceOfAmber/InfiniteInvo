package infiniteinvo.client.inventory;

import infiniteinvo.core.ModMutatedInventory;
import infiniteinvo.core.ModSettings;
import infiniteinvo.inventory.BigContainerPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
 
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiBigInventory extends GuiInventory
{
	BigContainerPlayer container;

	public boolean redoButtons = false;
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

	private int button_id = 99;
	private int buttonID()
	{
		button_id++;
		return button_id;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
    {
		super.initGui();
		
		if(this.container != null && this.mc.playerController.isInCreativeMode() == false)
		{
			int height = 20;
			int STARTX = this.guiLeft + 240;
			int STARTY = this.guiTop + height/2;
			int enderWidth = 90;
			int sortWidth = enderWidth/2 - 5;
			int x = STARTX;
			int y = STARTY;
			int x_spacing = enderWidth/2 + 5;
			if(ModSettings.showEnderButton)
			{
				this.buttonList.add(new GuiButtonEnderChest(buttonID(), x, y ,enderWidth,height));
			}
			if(ModSettings.showSortButtons)
			{
				y += height+1;
				GuiButton sortButton;

				sortButton = new GuiButtonSort(buttonID(), x, y ,sortWidth,height, ModMutatedInventory.SORT_LEFT,"<");
				this.buttonList.add(sortButton);

				x += x_spacing;

				sortButton = new GuiButtonSort(buttonID(), x, y ,sortWidth,height, ModMutatedInventory.SORT_RIGHT,">");
				this.buttonList.add(sortButton);
				
				x = STARTX;
				y += height+1;

				sortButton = new GuiButtonSort(buttonID(), x, y ,sortWidth,height, ModMutatedInventory.SORT_LEFTALL,"<<");
				this.buttonList.add(sortButton);

				x += x_spacing;
				
				sortButton = new GuiButtonSort(buttonID(), x, y ,sortWidth,height, ModMutatedInventory.SORT_RIGHTALL,">>");
				this.buttonList.add(sortButton);
				
			}
			
			
			if(ModSettings.showFilterButton)
			{
				x = STARTX + enderWidth + 5;
				y = STARTY;

				this.buttonList.add(new GuiButtonFilter(buttonID(), x, y ,enderWidth,height));
			}
		}
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

        if(ModSettings.showCharacter)
        	drawEntityOnScreen(gLeft + 51, gTop + 75, 30, (float)(gLeft + 51) - (float)mouseX, (float)(gTop + 75 - 50) - (float)mouseY, this.mc.thePlayer);
     
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if(ModSettings.showText)
			this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 87, 32, 4210752);
		
		if(container != null)
		{
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(new ResourceLocation("infiniteinvo", "textures/gui/inventory_gui_3.png"));
			
	        int maxPos = MathHelper.ceiling_float_int((float)ModSettings.invoSize/(float)(9 + ModSettings.MORE_COLS)) - (3 + ModSettings.MORE_ROWS);
			int barPos = maxPos > 0 ? MathHelper.floor_float((float)container.scrollPos / (float)maxPos * (18F * (3F + (float)ModSettings.MORE_ROWS) - 8F)) : 0;
			
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
}
