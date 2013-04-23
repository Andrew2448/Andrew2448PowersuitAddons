package andrew.powersuits.modules;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.moduletrigger.IBlockBreakingModule;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.ElectricItemUtils;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.List;

/**
 * Created by User: Andrew2448
 * 7:13 PM 4/21/13
 */
public class LeafBlowerModule extends PowerModuleBase implements IBlockBreakingModule {
    private static final String MODULE_LEAF_BLOWER = "Leaf Blower";
    private static final String LEAF_BLOWER_ENERGY_CONSUMPTION = "Leaf Blower Energy Consumption";
    private static final String PLANT_RADIUS = "Plant Radius";
    private static final String LEAF_RADIUS = "Leaf Radius";

    public LeafBlowerModule(List<IModularItem> validItems) {
        super(validItems);
        addInstallCost(new ItemStack(Item.ingotIron, 3));
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.solenoid, 1));
        addBaseProperty(LEAF_BLOWER_ENERGY_CONSUMPTION, 100, "J");
        addBaseProperty(PLANT_RADIUS, 1, "m");
        addBaseProperty(LEAF_RADIUS, 1, "m");
        addIntTradeoffProperty(PLANT_RADIUS, PLANT_RADIUS, 2, "m", 1, 0);
        addIntTradeoffProperty(LEAF_RADIUS, LEAF_RADIUS, 2, "m", 1, 0);
    }

    public PowerModuleBase addIntTradeoffProperty(String tradeoffName, String propertyName, double multiplier, String unit, int roundTo, int offset) {
        units.put(propertyName, unit);
        return addPropertyModifier(propertyName, new PropertyModifierIntLinearAdditive(tradeoffName, multiplier, roundTo, offset));
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_TOOL;
    }

    @Override
    public String getName() {
        return MODULE_LEAF_BLOWER;
    }

    @Override
    public String getDescription() {
        return "Create a torrent of air to knock plants out of the ground and leaves off of trees.";
    }

    @Override
    public String getTextureFile() {
        //return "sickle";
        return "bluestar";
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, Block block, int meta, EntityPlayer player) {
        if (block instanceof BlockLeaves || block instanceof BlockFlower) {
            if (ElectricItemUtils.getPlayerEnergy(player) > ModuleManager.computeModularProperty(stack, LEAF_BLOWER_ENERGY_CONSUMPTION)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, int blockID, int x, int y, int z, EntityPlayer player) {
        boolean b = false;
        Block block = Block.blocksList[blockID];
        int plant = (int)ModuleManager.computeModularProperty(stack, PLANT_RADIUS);
        int leaf = (int)ModuleManager.computeModularProperty(stack, LEAF_RADIUS);
        int totalEnergyDrain = 0;

        // Leaves
        if (block != null && block.isLeaves(world, x, y, z)) {
            for (int i = -leaf; i < leaf; i++) {
                for (int j = -leaf; j < leaf; j++) {
                    for (int k = -leaf; k < leaf; k++) {
                        int id = world.getBlockId(x+i, y+j, z+k);
                        int meta = world.getBlockMetadata(x + i, y + j, z + k);
                        Block tempBlock = Block.blocksList[id];
                        if (tempBlock != null && tempBlock.isLeaves(world, x+i, y+j, z+k)) {
                            if (block.canHarvestBlock(player, meta)) {
                                block.harvestBlock(world, player, x+i, y+j, z+k, meta);
                                totalEnergyDrain += ModuleManager.computeModularProperty(stack, LEAF_BLOWER_ENERGY_CONSUMPTION);
                            }
                            world.setBlockToAir(x+i, y+j, z+k);
                            b = true;
                        }
                    }
                }
            }
        }

        for (int i = -plant; i < plant; i++) {
            for (int j = -plant; j < plant; j++) {
                for (int k = -plant; k < plant; k++) {
                    int id = world.getBlockId(x + i, y + j, z + k);
                    int meta = world.getBlockMetadata(x + i, y + j, z + k);
                    Block tempBlock = Block.blocksList[id];
                    if (tempBlock != null && tempBlock instanceof BlockFlower) {
                        if (block.canHarvestBlock(player, meta)) {
                            block.harvestBlock(world, player, x + i, y + j, z + k, meta);
                            totalEnergyDrain += ModuleManager.computeModularProperty(stack, LEAF_BLOWER_ENERGY_CONSUMPTION);
                        }
                        world.setBlockToAir(x + i, y + j, z + k);
                        b = true;
                    }
                }
            }
        }
        ElectricItemUtils.drainPlayerEnergy(player, totalEnergyDrain);
        return b;
    }

    @Override
    public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
    }
}
