package andrew.powersuits.modules;

import andrew.powersuits.common.AddonComponent;
import andrew.powersuits.common.AddonUtils;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.moduletrigger.IRightClickModule;
import net.machinemuse.general.geometry.Colour;
import net.machinemuse.powersuits.common.ModCompatability;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.ElectricItemUtils;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OreScannerModule extends PowerModuleBase implements IRightClickModule {
	
	public static final String MODULE_ORE_SCANNER = "Ore Scanner";
	public static final String ORE_SCANNER_ENERGY_CONSUMPTION = "Scanning Energy Consumption Per Block";
	public static final String ORE_SCANNER_RADIUS_X = "X Radius";
	public static final String ORE_SCANNER_RADIUS_Y = "Y Radius";
	public static final String ORE_SCANNER_RADIUS_Z = "Z Radius";
	
	private static String[] oreNames = {"oreCopper", "oreTin", "oreSilver", "oreLead", "oreNickel", "orePlatinum", "oreZinc", "oreApatite", "oreUranium"};
	private static ArrayList<ArrayList<ItemStack>> ores = new ArrayList<ArrayList<ItemStack>>(); 
	private static HashMap<List, String> oreMap = new HashMap();
	private static HashMap<String, Integer> valueMap = new HashMap();
	
	public OreScannerModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(ORE_SCANNER_ENERGY_CONSUMPTION, 50);
		addBaseProperty(ORE_SCANNER_RADIUS_X, 1);
		addBaseProperty(ORE_SCANNER_RADIUS_Y, 1);
		addBaseProperty(ORE_SCANNER_RADIUS_Z, 1);
		addIntTradeoffProperty("X Radius", ORE_SCANNER_RADIUS_X, 3, "m", 1, 0);
		addIntTradeoffProperty("Y Radius", ORE_SCANNER_RADIUS_Y, 3, "m", 1, 0);
		addIntTradeoffProperty("Z Radius", ORE_SCANNER_RADIUS_Z, 3, "m", 1, 0);
        if (ModCompatability.isIndustrialCraftLoaded()) {
            addInstallCost(ModCompatability.getIC2Item("ovScanner"));
            addInstallCost(MuseItemUtils.copyAndResize(AddonComponent.computerChip, 1));
        }
        else {
            addInstallCost(MuseItemUtils.copyAndResize(AddonComponent.computerChip, 1));
            addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 2));
        }
		for (int i = 0; i < oreNames.length; i++) {
			ores.add(i, OreDictionary.getOres(oreNames[i]));
	    }
		
		fillMap();
	}
	
	public PowerModuleBase addIntTradeoffProperty(String tradeoffName, String propertyName, double multiplier, String unit, int roundTo, int offset) {
		units.put(propertyName, unit);
		return addPropertyModifier(propertyName, new PropertyModifierIntLinearAdditive(tradeoffName, multiplier, roundTo, offset));
	}
	
	public void searchForValuables(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side) {
		int xRadius = (int)ModuleManager.computeModularProperty(itemStack, ORE_SCANNER_RADIUS_X);
		int yRadius = (int)ModuleManager.computeModularProperty(itemStack, ORE_SCANNER_RADIUS_Y);
		int zRadius = (int)ModuleManager.computeModularProperty(itemStack, ORE_SCANNER_RADIUS_Z);
		
		int totalValue = 0, totalEnergy = 0;
		// int totalBlocks = 0;
		ForgeDirection fdSide = ForgeDirection.getOrientation(side).getOpposite();
		int cX = x + (fdSide.offsetX * xRadius);
		int cY = y + (fdSide.offsetY * yRadius);
		int cZ = z + (fdSide.offsetZ * zRadius);
		
		for (int sX = cX - xRadius; sX <= cX + xRadius; sX++) {
			for (int sY = cY - yRadius; sY <= cY + yRadius; sY++) {
				for (int sZ = cZ - zRadius; sZ <= cZ + zRadius; sZ++) {
					//totalBlocks++;
					totalValue += getValue(world.getBlockId(sX, sY, sZ), world.getBlockMetadata(sX, sY, sZ));
					ElectricItemUtils.drainPlayerEnergy(player, ModuleManager.computeModularProperty(itemStack, ORE_SCANNER_ENERGY_CONSUMPTION));
					totalEnergy += ModuleManager.computeModularProperty(itemStack, ORE_SCANNER_ENERGY_CONSUMPTION);
				}
			}
		}
        ElectricItemUtils.drainPlayerEnergy(player, totalEnergy);
        if (AddonUtils.isClientSide()) {
            getColour(totalValue);
        }
		if (AddonUtils.isServerWorld(world)) {
			//System.out.println("Total Energy: "+totalEnergy);
			//System.out.println("Total Blocks: "+totalBlocks);
			//System.out.println(oreMap);
			//System.out.println("Total value: "+totalValue);
		}
	}
	
	public static int getValue(int blockID, int meta) {
		if ((oreMap.containsKey(Arrays.asList(blockID, meta))) && (valueMap.containsKey(oreMap.get(Arrays.asList(blockID, meta))))) {
			return valueMap.get(oreMap.get(Arrays.asList(blockID, meta)));
		}
		return 0;
	}
	
	public static void fillMap() {
		for (int a = 0; a < ores.size(); a++) {
			for (int b = 0; b < ores.get(a).size(); b++) {
				oreMap.put(Arrays.asList(ores.get(a).get(b).itemID, ores.get(a).get(b).getItemDamage()), oreNames[a]);
			}
		}
		oreMap.put(Arrays.asList(Block.oreCoal.blockID, 0), "oreCoal");
		oreMap.put(Arrays.asList(Block.oreIron.blockID, 0), "oreIron");
		oreMap.put(Arrays.asList(Block.oreGold.blockID, 0), "oreGold");
		oreMap.put(Arrays.asList(Block.oreRedstone.blockID, 0), "oreRedstone");
		oreMap.put(Arrays.asList(Block.oreDiamond.blockID, 0), "oreDiamond");
		oreMap.put(Arrays.asList(Block.oreEmerald.blockID, 0), "oreEmerald");
		oreMap.put(Arrays.asList(Block.oreLapis.blockID, 0), "oreLapis");
		oreMap.put(Arrays.asList(Block.oreNetherQuartz.blockID, 0), "oreNetherQuartz");
		valueMap.put("oreCoal", 1);
		valueMap.put("oreIron", 4);
		valueMap.put("oreGold", 6);
		valueMap.put("oreRedstone", 3);
		valueMap.put("oreDiamond", 16);
		valueMap.put("oreEmerald", 18);
		valueMap.put("oreLapis", 12);
		valueMap.put("oreNetherQuartz", 8);
		valueMap.put("oreCopper", 4);
		valueMap.put("oreTin", 5);
		valueMap.put("oreSilver", 5);
		valueMap.put("oreLead", 6);
		valueMap.put("oreNickel", 14);
		valueMap.put("orePlatinum", 8);
		valueMap.put("oreZinc", 1);
		valueMap.put("oreApatite", 2);
		valueMap.put("oreUranium", 14);
		valueMap.put("oreXychorium", 2);
		valueMap.put("oreNaturalAluminum", 3);
		valueMap.put("oreCertusQuartz", 5);
	}

    public Colour getColour(int value) {
        if (value > 0 && value <= 8) {
            return new Colour(0.4, 0.4, 0.4, 1.0);
        } else if (value > 8 && value <= 16) {
            return Colour.WHITE;
        } else if (value > 16 && value <= 24) {
            return new Colour(0.14, 0.64, 0.12, 1.0);
        } else if (value > 24 && value <= 32) {
            return Colour.DARKBLUE;
        } else if (value > 32 && value <= 40) {
            return new Colour(0.3, 0.0, 0.5, 1.0);
        } else if (value > 40) {
            return Colour.YELLOW;
        }
        return Colour.BLACK;
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
