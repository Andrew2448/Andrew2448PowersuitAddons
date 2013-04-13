package andrew.powersuits.common;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.MuseItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AddonUtils {
	public static boolean canItemFitInInventory(EntityPlayer player, ItemStack itemstack)
	{
		for(int i = 0; i < player.inventory.getSizeInventory() - 4; i++)
		{
			if(player.inventory.getStackInSlot(i) == null)
			{
				return true;
			}
		}
		if(!itemstack.isItemDamaged())
        {
	        if(itemstack.getMaxStackSize() == 1)return false;//would need to go in empty slot
	        
	        for(int i = 0; i < player.inventory.getSizeInventory(); i++)
	        {
	        	ItemStack invstack = player.inventory.getStackInSlot(i);
	            if(invstack != null && invstack.itemID == itemstack.itemID && invstack.isStackable() && invstack.stackSize < invstack.getMaxStackSize() && invstack.stackSize < player.inventory.getInventoryStackLimit() && (!invstack.getHasSubtypes() || invstack.getItemDamage() == itemstack.getItemDamage()))
	            {
	                return true;
	            }
	        }	        
        }
		return false;
	}
	
	public static double getFoodLevel(ItemStack stack) {
		if (stack != null && stack.getItem() instanceof IModularItem) {
			NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
			Double foodLevel = itemTag.getDouble("Food");
			if (foodLevel != null) {
				return foodLevel;
			}
		}
		return 0.0;
	}

	public static void setFoodLevel(ItemStack stack, double d) {
		if (stack != null && stack.getItem() instanceof IModularItem) {
			NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
			itemTag.setDouble("Food", d);
		}
	}

	public static double getSaturationLevel(ItemStack stack) {
		if (stack != null && stack.getItem() instanceof IModularItem) {
			NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
			Double saturationLevel = itemTag.getDouble("Saturation");
			if (saturationLevel != null) {
				return saturationLevel;
			}
		}
		return 0.0F;
	}

	public static void setSaturationLevel(ItemStack stack, double d) {
		if (stack != null && stack.getItem() instanceof IModularItem) {
			NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
			itemTag.setDouble("Saturation", d);
		}
	}
	
	public static int getTorchLevel(ItemStack stack) {
		if (stack != null && stack.getItem() instanceof IModularItem) {
			NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
			Integer torchLevel = itemTag.getInteger("Torch");
			if (torchLevel != null) {
				return torchLevel;
			}
		}
		return 0;
	}
	
	public static void setTorchLevel(ItemStack stack, int i) {
		if (stack != null && stack.getItem() instanceof IModularItem) {
			NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
			itemTag.setInteger("Torch", i);
		}
	}
	
	public static NBTTagCompound getNBTTag(ItemStack itemStack) {
	    NBTTagCompound tag = itemStack.getTagCompound();

	    if (tag == null) {
	      tag = new NBTTagCompound("tag");
	      itemStack.setTagCompound(tag);
	    }

	    return tag;
	}
	
	public static boolean isClientWorld(World world) {
		return world.isRemote;
	}
	
	public static boolean isServerWorld(World world) {
		return !world.isRemote;
	}
}
