package andrew.powersuits.modules;

import ic2.api.IWrenchable;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;

import java.util.List;

import thermalexpansion.api.tileentity.IReconfigurableFacing;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.api.electricity.ElectricItemUtils;
import net.machinemuse.api.moduletrigger.IRightClickModule;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import andrew.powersuits.common.AddonUtils;
import buildcraft.api.tools.IToolWrench;

public class OmniWrenchModule extends PowerModuleBase implements IRightClickModule, IToolWrench {
	
	public static final String MODULE_OMNI_WRENCH = "Omni Wrench";
	public static final String OMNI_WRENCH_ENERGY_CONSUMPTION = "Omni Wrench Energy Consumption";
	public static final int[] SIDE_OPPOSITE = {1, 0, 3, 2, 5, 4};
	
	public OmniWrenchModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(OMNI_WRENCH_ENERGY_CONSUMPTION, 100);
		addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
	    addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.servoMotor, 2));
	}
	
	@Override
	public String getTextureFile() {
		return "torchplacer";
	}

	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_SPECIAL;
	}

	@Override
	public String getName() {
		return MODULE_OMNI_WRENCH;
	}

	@Override
	public String getDescription() {
		return "A wrench which can interact with almost every mod.";
	}

	@Override
	public void onRightClick(EntityPlayer playerClicking, World world, ItemStack item) {}

	@Override
	public void onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {}

	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		int blockID = world.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if ((tile instanceof IWrenchable) && ElectricItemUtils.getPlayerEnergy(player) > 99)
	    {
	      IWrenchable wrenchTile = (IWrenchable)tile;

	      if (player.isSneaking()) {
	        side = SIDE_OPPOSITE[side];
	      }

	      if ((side == wrenchTile.getFacing()) && (wrenchTile.wrenchCanRemove(player))) {
	        ItemStack dropBlock = wrenchTile.getWrenchDrop(player);

	        if (dropBlock != null)
	        {
	          world.setBlock(x, y, z, 0);

	          if (AddonUtils.isServerWorld(world))
	          {
	            float f = 0.7F;
	            double i = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
	            double j = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
	            double k = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
	            EntityItem entity = new EntityItem(world, i + x, j + y, k + z, dropBlock);
	            entity.delayBeforeCanPickup = 10;
	            world.spawnEntityInWorld(entity);
	          }
	        }
	        
	        ElectricItemUtils.drainPlayerEnergy(player, ModuleManager.computeModularProperty(itemStack, OMNI_WRENCH_ENERGY_CONSUMPTION));

	        return AddonUtils.isServerWorld(world);
	      }

	      if (AddonUtils.isServerWorld(world))
	      {
	        if ((side == 0) || (side == 1))
	        {
	          if (((wrenchTile instanceof IEnergySource)) && ((wrenchTile instanceof IEnergySink))) {
	            wrenchTile.setFacing((short)side);
	          }
	        }
	        else {
	          wrenchTile.setFacing((short)side);
	        }
	        ElectricItemUtils.drainPlayerEnergy(player, ModuleManager.computeModularProperty(itemStack, OMNI_WRENCH_ENERGY_CONSUMPTION));
	      }
	      return AddonUtils.isServerWorld(world);
	    }
		
		if ((tile instanceof IReconfigurableFacing)) {
		      if (AddonUtils.isServerWorld(world)) {
		    	ElectricItemUtils.drainPlayerEnergy(player, ModuleManager.computeModularProperty(itemStack, OMNI_WRENCH_ENERGY_CONSUMPTION));
		        return ((IReconfigurableFacing)tile).rotateBlock();
		      }
		      return false;
		}
		
		return false;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int par4) {}

	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		return true;
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {}
	
}
