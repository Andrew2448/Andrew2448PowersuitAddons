package andrew.powersuits.modules;

import java.util.ArrayList;
import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.moduletrigger.IRightClickModule;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class OreScannerModule extends PowerModuleBase implements IRightClickModule {
	
	public static final String MODULE_ORE_SCANNER = "Ore Scanner";
	public static final String ORE_SCANNER_ENERGY_CONSUMPTION = "Scanning Energy Consumption Per Block";
	public static final String ORE_SCANNER_RADIUS_X = "X Radius";
	public static final String ORE_SCANNER_RADIUS_Y = "Y Radius";
	public static final String ORE_SCANNER_RADIUS_Z = "Z Radius";
	String name;
	String[] oreNames = {"oreCopper", "oreTin", "oreSilver", "oreLead", "oreNickel", "orePlatinum", "oreZinc", "oreApatite", "oreUranium"};
	int[] values = {1, 1, 1, 1, 1, 1, 1, 1, 1};
	ArrayList<ArrayList<ItemStack>> ores = new ArrayList<ArrayList<ItemStack>>(); 
	
	public OreScannerModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(ORE_SCANNER_ENERGY_CONSUMPTION, 50);
		addBaseProperty(ORE_SCANNER_RADIUS_X, 1);
		addBaseProperty(ORE_SCANNER_RADIUS_Y, 1);
		addBaseProperty(ORE_SCANNER_RADIUS_Z, 1);
		addIntTradeoffProperty("X Radius", ORE_SCANNER_RADIUS_X, 3, "", 1, 0);
		addIntTradeoffProperty("Y Radius", ORE_SCANNER_RADIUS_Y, 3, "", 1, 0);
		addIntTradeoffProperty("Z Radius", ORE_SCANNER_RADIUS_Z, 3, "", 1, 0);
		//addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
	    //addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.servoMotor, 2));
		
		for (int i = 0; i < oreNames.length; i++) {
			ores.set(i, OreDictionary.getOres(oreNames[i]));
		}
	}
	
	public PowerModuleBase addIntTradeoffProperty(String tradeoffName, String propertyName, double multiplier, String unit, int roundTo, int offset) {
		units.put(propertyName, unit);
		return addPropertyModifier(propertyName, new PropertyModifierIntLinearAdditive(tradeoffName, multiplier, roundTo, offset));
	}
	
	public void searchForValuables(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side) {
		int xRadius = (int)ModuleManager.computeModularProperty(itemStack, ORE_SCANNER_RADIUS_X);
		int yRadius = (int)ModuleManager.computeModularProperty(itemStack, ORE_SCANNER_RADIUS_Y);
		int zRadius = (int)ModuleManager.computeModularProperty(itemStack, ORE_SCANNER_RADIUS_Z);
		
		int totalValue = 0, totalBlocks = 0;
		ForgeDirection fdSide = ForgeDirection.getOrientation(side).getOpposite();
		int cX = x + (fdSide.offsetX * xRadius);
		int cY = y + (fdSide.offsetY * yRadius);
		int cZ = z + (fdSide.offsetZ * zRadius);
		
//		for (String s : OreDictionary.getOreNames()) {
//			if (s.contains("ore")) {
//				System.out.println(s);
//			}
//		}
		
		for (int sX = cX - xRadius; sX <= cX + xRadius; sX++) {
			for (int sY = cY - yRadius; sY <= cY + yRadius; sY++) {
				for (int sZ = cZ - zRadius; sZ <= cZ + zRadius; sZ++) {
					totalBlocks++;
					name = Block.blocksList[world.getBlockId(sX, sY, sZ)].getUnlocalizedName();
					System.out.println("Name: "+name);
					totalValue += getValue(name, world.getBlockId(sX, sY, sZ), world.getBlockMetadata(sX, sY, sZ));
				}
			}
		}
		System.out.println("Total Blocks: "+totalBlocks);
	}
	
	public int getValue(String name, int blockID, int meta) {
//		for (int a = 0; a < ores.size(); a++) {
//			for (int b = 0; b < ores.get(a).size(); b++) {
//				if (ores.get(a).get(b).itemID == blockID) {
//					if (name.equals("tile.thermalexpansion.ore")) {
//						return values[meta];
//					}
//					else {
//						return values[a];
//					}
//				}
//			}
//		}
		return 0;
	}
	
	@Override
	public String getTextureFile() {
		return "torchplacer";
	}

	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_TOOL;
	}

	@Override
	public String getName() {
		return MODULE_ORE_SCANNER;
	}

	@Override
	public String getDescription() {
		return "DON'T USE THIS RIGHT NOW IT WILL DO BAD THINGS";//"A way to see how valuable the land around you is.";
	}

	@Override
	public void onRightClick(EntityPlayer playerClicking, World world, ItemStack item) {}

	@Override
	public void onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		searchForValuables(itemStack, player, world, x, y, z, side);
	}

	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		return false;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int par4) {}
}
