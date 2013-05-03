package andrew.powersuits.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.machinemuse.powersuits.common.ModCompatability;
import net.machinemuse.powersuits.item.ItemComponent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thermalexpansion.api.crafting.CraftingManagers;

public class AddonRecipeManager {
    public static void addRecipes() {
        ItemStack circuit = ItemComponent.wiring;
        ItemStack glass = new ItemStack(Block.glass);
        ItemStack glassPane = new ItemStack(Block.thinGlass);
        ItemStack lapis = new ItemStack(Item.dyePowder, 1, 4);
        ItemStack lapisBlock = new ItemStack(Block.blockLapis);
        ItemStack ironIngot = new ItemStack(Item.ingotIron);
        ItemStack redstoneBlock = new ItemStack(Block.blockRedstone);
        ItemStack diamond = new ItemStack(Item.diamond);
        ItemStack comparator = new ItemStack(Item.comparator);
        ItemStack goldIngot = new ItemStack(Item.ingotGold);
        ItemStack leather = new ItemStack(Item.leather);
        ItemStack rottenFlesh = new ItemStack(Item.rottenFlesh);
        ItemStack sugar = new ItemStack(Item.sugar);

        // Hehe ... a personal interest :)
        CraftingManagers.smelterManager.addRecipe(80, rottenFlesh, sugar, leather);

        if (ModCompatability.vanillaRecipesEnabled()) {

            //===========================================================================================================================
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.solarPanel, "PPP", "PLP", "PPP", 'P', glassPane, 'L', lapisBlock));
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.magnet, "III", "SSS", "III", 'I', ironIngot, 'S', ItemComponent.solenoid));
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.computerChip, "RCR", "GDG", 'R', redstoneBlock, 'C', comparator, 'G', goldIngot, 'D', diamond));
            //===========================================================================================================================
        }

        if (ModCompatability.UERecipesEnabled() && ModCompatability.isBasicComponentsLoaded()) {
            String basicCircuit = "basicCircuit";
            String advancedCircuit = "advancedCircuit";
            String eliteCircuit = "eliteCircuit";

            //===========================================================================================================================
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.solarPanel, true, "GGG", "CLC", "SSS", 'G', glass, 'C', basicCircuit, 'L', lapisBlock, 'S', "plateSteel"));
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.magnet, "ICI", "SSS", "ICI", 'I', ironIngot, 'C', advancedCircuit, 'S', ItemComponent.solenoid));
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.computerChip, "CRC", "GDG", 'C', eliteCircuit, 'R', redstoneBlock, 'G', goldIngot, 'D', diamond));
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
            ItemStack ovscanner = ModCompatability.getIC2Item("ovScanner");
            ovscanner.setItemDamage(1);
            ItemStack goldCable = ModCompatability.getIC2Item("doubleInsulatedGoldCableItem");

            //===========================================================================================================================
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.solarPanel, true, "LGL", "GLG", "CBC", 'L', lapis, 'G', glass, 'C', circuit, 'B', gen));
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.magnet, "ICI", "SSS", "ICI", 'I', ironIngot, 'C', advCircuit, 'S', ItemComponent.solenoid));
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.computerChip, "CSC", "GGG", 'C', advCircuit, 'S', ovscanner, 'G', goldCable));
            //===========================================================================================================================
        }

        if (ModCompatability.GregTechRecipesEnabled() && ModCompatability.isIndustrialCraftLoaded() && ModCompatability.isGregTechLoaded()) {
            String energyFlowCircuit = "craftingCircuitTier07";
            String superConductor = "craftingSuperconductor";
            String dataStorageCircuit = "craftingCircuitTier05";
            ItemStack reinforcedGlass = ModCompatability.getIC2Item("reinforcedGlass");
            ItemStack advCircuit = ModCompatability.getIC2Item("advancedCircuit");
            ItemStack ovscanner = ModCompatability.getIC2Item("ovScanner");
            ovscanner.setItemDamage(1);

            //===========================================================================================================================
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.solarPanel, true, "GGG", "PCP", 'G', reinforcedGlass, 'P', "plateAlloyIridium", 'C', energyFlowCircuit));
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.magnet, true, "ICI", "SSS", "ICI", 'I', "ingotTitanium", 'C', superConductor, 'S', ItemComponent.solenoid));
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.computerChip, true, "ADA", "DOD", "ADA", 'A', advCircuit, 'D', dataStorageCircuit, 'O', ovscanner));
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
            ItemStack conduitEnergy = ModCompatability.getThermexItem("energyConduitEmpty", 1);
            ItemStack teleportFrameFull = ModCompatability.getThermexItem("teleportBase", 1);
            ItemStack multimeter = ModCompatability.getThermexItem("multimeter", 1);

            //===========================================================================================================================
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.solarPanel, true, "GGG", "CLC", " E ", 'G', glass, 'L', new ItemStack(Block.blockLapis), 'C', conduitEnergy, 'E', powerCoilSilver));
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.magnet, "ICI", "SSS", "ICI", 'I', ironIngot, 'C', powerCoilGold, 'S', ItemComponent.solenoid));
            GameRegistry.addRecipe(new ShapedOreRecipe(AddonComponent.computerChip, " O ", "GMS", " E ", 'E', powerCoilElectrum, 'S', powerCoilSilver, 'G', powerCoilGold, 'M', multimeter, 'O', ItemComponent.solenoid));
            //===========================================================================================================================
        }
    }
}	
