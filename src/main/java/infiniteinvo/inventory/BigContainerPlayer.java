package infiniteinvo.inventory;

import infiniteinvo.client.inventory.GuiBigInventory;
import infiniteinvo.core.ModSettings;
import infiniteinvo.core.InfiniteInvo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;

public class BigContainerPlayer extends ContainerPlayer
{
	private int craftSize = 2;//did not exist before, was magic'd as 2 everywhere
	public int scrollPos = 0;
	public BigInventoryPlayer invo;
    public boolean isLocalWorld;
    private final EntityPlayer thePlayer;
	/**
	 * A more organised version of 'inventorySlots' that doesn't include the hotbar
	 */
	Slot[] slots = new Slot[ModSettings.invoSize];
	Slot[] hotbar = new Slot[9];
	Slot[] crafting = new Slot[craftSize*craftSize];
	Slot result;
	
	@SuppressWarnings("unchecked")
	public BigContainerPlayer(BigInventoryPlayer playerInventory, boolean isLocal, EntityPlayer player)
	{
		super(playerInventory, isLocal, player);
        this.thePlayer = player;
		inventorySlots = Lists.newArrayList();//undo everything done by super()
		craftMatrix = new InventoryCrafting(this, craftSize, craftSize);



		int shiftxOut = 9;
        int shiftyOut = 6;
        int shiftx = -7;
        int shifty = 0;
        //turn off all the shifts, if we are staying wtih a 2x2 version
        if(this.craftSize == 2)
        {
        	shiftxOut = 0;
        	shiftyOut = 0;
        	shiftx = 0;
        	shifty = 0;
        }
        

        int slotNumber = 0;//the ID for the inventory slot
        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, slotNumber, 144+shiftxOut, 36+shiftyOut));
        int i,j,cx,cy;

        for (i = 0; i < craftSize; ++i)
        {
            for (j = 0; j < craftSize; ++j)
            {
            	slotNumber = j + i * this.craftSize;
            
            	cx = 88 + j * GuiBigInventory.square + shiftx;
            	cy = 26 + i * GuiBigInventory.square + shifty;
            	
                this.addSlotToContainer(new Slot(this.craftMatrix, slotNumber, cx , cy));
                //j + i * 2, 88 + j * 18, 26 + i * 18));
            }
        }

        for (i = 0; i < 4; ++i)
        {
        	cx = 8;
        	cy = 8 + i * GuiBigInventory.square;
            final int k = i;
            slotNumber =  playerInventory.getSizeInventory() - 1 - i;
            //used to be its own class SlotArmor
            this.addSlotToContainer(new Slot(playerInventory, slotNumber, cx, cy)
            {
                private static final String __OBFID = "CL_00001755";
                /**
                 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1
                 * in the case of armor slots)
                 */
                public int getSlotStackLimit()
                {
                    return 1;
                }
                /**
                 * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
                 */
                public boolean isItemValid(ItemStack stack)
                {
                    if (stack == null) return false;
                    return stack.getItem().isValidArmor(stack, k, thePlayer);
                }
                @SideOnly(Side.CLIENT)
                public String getSlotTexture()
                {
                    return ItemArmor.EMPTY_SLOT_NAMES[k];
                }
            });
        }

        for (i = 0; i < 3; ++i)      //inventory is 3 rows by 9 columns
        {
            for (j = 0; j < 9; ++j)
            {
            	slotNumber = j + (i + 1) * 9;
            	cx = 8 + j * GuiBigInventory.square;
            	cy = 84 + i * GuiBigInventory.square;
                this.addSlotToContainer(new Slot(playerInventory, slotNumber, cx, cy));
            }
        }

        for (i = 0; i < 9; ++i)
        {
        	slotNumber = i;
        	cx = 8 + i * GuiBigInventory.square;
        	cy = 142;
            this.addSlotToContainer(new Slot(playerInventory, slotNumber, cx, cy));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
		this.invo = (BigInventoryPlayer)playerInventory;
		
		for(i = 9; i < 36; i++)
		{
			// Add all the previous inventory slots to the organised array
			 Slot os = (Slot)this.inventorySlots.get(i);
			 
			 Slot ns = new Slot(os.inventory, os.getSlotIndex(), os.xDisplayPosition, os.yDisplayPosition);
			 ns.slotNumber = os.slotNumber;
			 this.inventorySlots.set(i, ns);
			 ns.onSlotChanged();
			 slots[i - 9] = ns;
		}
		
		for( i = 36; i < 45; i++)
		{
			// Get the hotbar for repositioning
			hotbar[i - 36] = (Slot)this.inventorySlots.get(i);
		}
		
		for( i = 1; i < 5; i++)
		{
			crafting[i - 1] = (Slot)this.inventorySlots.get(i);
		}
		
		result = (Slot)this.inventorySlots.get(0);
		result.xDisplayPosition = 144;
		result.yDisplayPosition = 53;
		
		for( i = 0; i < 4; i++)
		{
			Slot hs = crafting[i];
			hs.xDisplayPosition = 88 + ((i%2) * 18);
			hs.yDisplayPosition = 43 + ((i/2) * 18);
		}
		
		for( i = 0; i < 9; i++)
		{
			Slot hs = hotbar[i];
			hs.xDisplayPosition = 8 + (i * 18);
			hs.yDisplayPosition = 142 + (18 * ModSettings.MORE_ROWS);
		}

        for ( i = 3; i < MathHelper.ceiling_float_int((float)ModSettings.invoSize/9F); ++i)
        {
            for ( j = 0; j < 9; ++j)
            {
            	if(j + (i * 9) >= ModSettings.invoSize && ModSettings.invoSize > 27)
            	{
            		break;
            	} else
            	{
            		// Moved off screen to avoid interaction until screen scrolls over the row
            		Slot ns = new Slot(playerInventory, j + (i + 1) * 9, -999, -999);
            		slots[j + (i * 9)] = ns;
            		this.addSlotToContainer(ns);
            	}
            }
        }
        
        this.UpdateScroll();
	}
	
	@Override
	public Slot getSlotFromInventory(IInventory invo, int id)
	{
		Slot slot = super.getSlotFromInventory(invo, id);
		if(slot == null)
		{
			Exception e = new NullPointerException();
			 
			InfiniteInvo.logger.log(Level.FATAL, e.getStackTrace()[1].getClassName() + "." + e.getStackTrace()[1].getMethodName() + ":" + e.getStackTrace()[1].getLineNumber() + " is requesting slot " + id + " from inventory " + invo.getName() + " (" + invo.getClass().getName() + ") and got NULL!", e);
		}
		return slot;
	}
	
	public void UpdateScroll()
	{
		if(scrollPos > MathHelper.ceiling_float_int((float)ModSettings.invoSize/(float)(9 + ModSettings.MORE_COLS)) - (3 + ModSettings.MORE_ROWS))
		{
			scrollPos = MathHelper.ceiling_float_int((float)ModSettings.invoSize/(float)(9 + ModSettings.MORE_COLS)) - (3 + ModSettings.MORE_ROWS);
		}
		
		if(scrollPos < 0)
		{
			scrollPos = 0;
		}
		
		for(int i = 0; i < MathHelper.ceiling_float_int((float)MathHelper.clamp_int(ModSettings.invoSize, 27, Integer.MAX_VALUE)/(float)(9 + ModSettings.MORE_COLS)); i++)
		{
            for (int j = 0; j < 9 + ModSettings.MORE_COLS; ++j)
            {
            	int index = j + (i * (9 + ModSettings.MORE_COLS));
            	if(index >= ModSettings.invoSize && index >= 27)
            	{
            		break;
            	} else
            	{
            		if(i >= scrollPos && i < scrollPos + 3 + ModSettings.MORE_ROWS && index < invo.getUnlockedSlots() - 9 && index < ModSettings.invoSize)
            		{
            			Slot s = slots[index];
            			s.xDisplayPosition = 8 + j * 18;
            			s.yDisplayPosition = 84 + (i - scrollPos) * 18;
            		} else
            		{
            			Slot s = slots[index];
            			s.xDisplayPosition = -999;
            			s.yDisplayPosition = -999;
            		}
            	}
            }
		}
	}

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		int vLocked = invo.getUnlockedSlots() < 36? 36 - invo.getUnlockedSlots() : 0;
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ == 0) // Crafting result
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (p_82846_2_ >= 1 && p_82846_2_ < 5) // Crafting grid
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 5 && p_82846_2_ < 9) // Armor
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, false))
                {
                    return null;
                }
            }
            else if (itemstack.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack()) // Inventory to armor
            {
                int j = 5 + ((ItemArmor)itemstack.getItem()).armorType;

                if (!this.mergeItemStack(itemstack1, j, j + 1, false))
                {
                    return null;
                }
            }
            else if ((p_82846_2_ >= 9 && p_82846_2_ < 36) || (p_82846_2_ >= 45 && p_82846_2_ < invo.getUnlockedSlots() + 9))
            {
                if (!this.mergeItemStack(itemstack1, 36, 45, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 36 && p_82846_2_ < 45) // Hotbar
            {
                if (!this.mergeItemStack(itemstack1, 9, 36 - vLocked, false) && (invo.getUnlockedSlots() - 36 <= 0 || !this.mergeItemStack(itemstack1, 45, 45 + (invo.getUnlockedSlots() - 36), false)))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 9, invo.getUnlockedSlots() + 9, false)) // Full range
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p_82846_1_, itemstack1);
        }

        return itemstack;
    }
}
