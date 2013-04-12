package andrew.powersuits.modules;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;

import andrew.powersuits.common.AddonUtils;
import andrew.powersuits.common.ModularPowersuitsAddons;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.api.moduletrigger.IRightClickModule;
import net.machinemuse.powersuits.common.ModCompatability;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class EUReaderModule extends PowerModuleBase implements IRightClickModule {
	
	public static final String MODULE_EU_READER = "EU Reader";
	
	public EUReaderModule(List<IModularItem> validItems) {
		super(validItems);
		addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
		addInstallCost(ModCompatability.getIC2Item("ecMeter"));
	}

	@Override
	public String getTextureFile() {
		return "portablecrafting";
	}

	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_TOOL;
	}

	@Override
	public String getName() {
		return MODULE_EU_READER;
	}

	@Override
	public String getDescription() {
		return "A version of the IC2 EU Reader integrated in your power tool.";
	}

	@Override
	public void onRightClick(EntityPlayer player, World world, ItemStack item) {
		
	}

	@Override
	public void onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
	}

	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

	    if ((((tileEntity instanceof IEnergySource)) || ((tileEntity instanceof IEnergyConductor)) || ((tileEntity instanceof IEnergySink))) && (!FMLCommonHandler.instance().getEffectiveSide().isClient())) {
	      NBTTagCompound nbtData = AddonUtils.getNBTTag(itemStack);

	      long currentTotalEnergyEmitted = EnergyNet.getForWorld(world).getTotalEnergyEmitted(tileEntity);
	      long currentTotalEnergySunken = EnergyNet.getForWorld(world).getTotalEnergySunken(tileEntity);
	      long currentMeasureTime = world.getWorldTime();

	      if ((nbtData.getInteger("lastMeasuredTileEntityX") != x) || (nbtData.getInteger("lastMeasuredTileEntityY") != y) || (nbtData.getInteger("lastMeasuredTileEntityZ") != z))
	      {
	        nbtData.setInteger("lastMeasuredTileEntityX", x);
	        nbtData.setInteger("lastMeasuredTileEntityY", y);
	        nbtData.setInteger("lastMeasuredTileEntityZ", z);

	        player.sendChatToPlayer("Starting new measurement");
	      } 
	      else {
	        long measurePeriod = currentMeasureTime - nbtData.getLong("lastMeasureTime");
	        if (measurePeriod < 1L) measurePeriod = 1L;

	        double deltaEmitted = (currentTotalEnergyEmitted - nbtData.getLong("lastTotalEnergyEmitted")) / measurePeriod;
	        double deltaSunken = (currentTotalEnergySunken - nbtData.getLong("lastTotalEnergySunken")) / measurePeriod;

	        DecimalFormat powerFormat = new DecimalFormat("0.##");

	        player.sendChatToPlayer("Measured power [EU/t]: " + powerFormat.format(deltaSunken) + " in " + powerFormat.format(deltaEmitted) + " out " + powerFormat.format(deltaSunken - deltaEmitted) + " gain" + " (avg. over " + measurePeriod + " ticks)");
	      }

	      nbtData.setLong("lastTotalEnergyEmitted", currentTotalEnergyEmitted);
	      nbtData.setLong("lastTotalEnergySunken", currentTotalEnergySunken);
	      nbtData.setLong("lastMeasureTime", currentMeasureTime);

	      return true;
	    }

	    return false;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int par4) {
		
	}
}
