package andrew.powersuits.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.machinemuse.powersuits.common.ModCompatability;
import net.machinemuse.powersuits.item.ItemComponent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class AddonRecipeManager {
	public static void addRecipes() {
		ItemStack circuit = ItemComponent.wiring;
		ItemStack glass = new ItemStack(Block.glass);
		ItemStack glassPane = new ItemStack(Block.thinGlass);
		ItemStack lapis = new ItemStack(Item.dyePowder, 1, 4);
		ItemStack lapisBlock = new ItemStack(Block.blockLapis);
		
		if (ModCompatability.vanillaRecipesEnabled()) {
			
			//===========================================================================================================================
			GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.solarPanel, "PPP", "PLP", "PPP", 'P', glassPane, 'L', lapisBlock));
			//===========================================================================================================================
		}
		
		if (ModCompatability.UERecipesEnabled() && ModCompatability.isBasicComponentsLoaded()) {
			String basicCircuit = "basicCircuit";
			String advancedCircuit = "advancedCircuit";
			String eliteCircuit = "eliteCircuit";
			
			//===========================================================================================================================
			GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.solarPanel, true, "GGG", "CLC", "SSS", 'G', glass, 'C', basicCircuit, 'L', lapisBlock, 'S', "plateSteel"));
			//===========================================================================================================================
		}
		
		if (ModCompatability.IC2RecipesEnabled() && ModCompatability.isIndustrialCraftLoaded()) {
			circuit = ModCompatability.getIC2Item("electronicCircuit");
			ItemStack advCircuit = ModCompatability.getIC2Item("advancedCircuit");
			String refIron = "ingotRefinedIron";
			String tin = "ingotTin";
			String copper = "ingotCopper";
			ItemStack reBattery = ModCompatability.getIC2Item("reBattery");
			ItemStack fullBattery = ModCompatability.getIC2Item("chargedReBattery");
			ItemStack energyCrystal = ModCompatability.getIC2Item("energyCrystal");
			ItemStack lapotronCrystal = ModCompatability.getIC2Item("lapotronCrystal");
			ItemStack iridiumOre = ModCompatability.getIC2Item("iridiumOre");
			ItemStack carbonPlate = ModCompatability.getIC2Item("carbonPlate");
			ItemStack machine = ModCompatability.getIC2Item("machine");
			ItemStack advMachine = ModCompatability.getIC2Item("advancedMachine");
			ItemStack gen = ModCompatability.getIC2Item("generator");
			
			//===========================================================================================================================
			GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.solarPanel, true, "LGL", "GLG", "CBC", 'L', lapis, 'G', glass, 'C', circuit, 'B', gen));
			//===========================================================================================================================
		}
		
		if (ModCompatability.GregTechRecipesEnabled() && ModCompatability.isIndustrialCraftLoaded() && ModCompatability.isGregTechLoaded()) {
			String energyFlowCircuit = "circuitTier07";
			ItemStack reinforcedGlass = ModCompatability.getIC2Item("reinforcedGlass");
			
			//===========================================================================================================================
			GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.solarPanel, true, "GGG", "PCP", 'G', reinforcedGlass, 'P', "plateIridium", 'C', energyFlowCircuit));
			//===========================================================================================================================
		}
		
		if (ModCompatability.ThermalExpansionRecipesEnabled() && ModCompatability.isThermalExpansionLoaded()) {
			ItemStack pneumaticServo = ModCompatability.getThermexItem("pneumaticServo", 1);
			ItemStack machineFrame = ModCompatability.getThermexItem("machineFrame", 1);
			ItemStack powerCoilGold = ModCompatability.getThermexItem("powerCoilGold", 1);
			ItemStack powerCoilSilver = ModCompatability.getThermexItem("powerCoilSilver", 1);
			ItemStack powerCoilElectrum = ModCompatability.getThermexItem("powerCoilElectrum", 1);
			ItemStack gearCopper = ModCompatability.getThermexItem("gearCopper", 1);
			ItemStack gearTin = ModCompatability.getThermexItem("gearTin", 1);
			ItemStack gearInvar = ModCompatability.getThermexItem("gearInvar", 1);
			ItemStack compressedSawdust = ModCompatability.getThermexItem("sawdustCompressed", 1);
			ItemStack energyFrameFull = ModCompatability.getThermexItem("energyFrameFull", 1);
			ItemStack conduitEnergy = ModCompatability.getThermexItem("energyConduit", 1);
			ItemStack teleportFrameFull = ModCompatability.getThermexItem("teleportBase", 1);
			
			//===========================================================================================================================
			GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.solarPanel, true, "GGG", "CLC", " E ", 'G', glass, 'L', new ItemStack(Block.blockLapis), 'C', conduitEnergy, 'E', powerCoilSilver));
			//===========================================================================================================================
		}
	}
}	
