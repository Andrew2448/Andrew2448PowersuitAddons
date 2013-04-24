package andrew.powersuits.modules;

import andrew.powersuits.common.AddonUtils;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.moduletrigger.IPlayerTickModule;
import net.machinemuse.api.moduletrigger.IRightClickModule;
import net.machinemuse.api.moduletrigger.IToggleableModule;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.ElectricItemUtils;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class TorchPlacerModule extends PowerModuleBase implements IToggleableModule, IPlayerTickModule, IRightClickModule {
    public static final String MODULE_TORCH_PLACER = "Torch Placer";
    public static final String TORCH_ENERGY_CONSUMPTION = "Torch Placement Energy Consumption";
    public static final String MAX_TORCH_STORAGE = "Maximum Storage Amount";

    public TorchPlacerModule(List<IModularItem> validItems) {
        super(validItems);
        addBaseProperty(TORCH_ENERGY_CONSUMPTION, 50);
        addBaseProperty(MAX_TORCH_STORAGE, 64);
        addTradeoffProperty("Storage", MAX_TORCH_STORAGE, 192);
        addTradeoffProperty("Storage", TORCH_ENERGY_CONSUMPTION, 450);
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
        return MODULE_TORCH_PLACER;
    }

    @Override
    public String getDescription() {
        return "Stores torches in an internal storage and places them in the world on use.";
    }

    @Override
    public void onPlayerTickActive(EntityPlayer player, ItemStack item) {
        IInventory inv = player.inventory;
        int torchesNeeded = (int) ModuleManager.computeModularProperty(item, MAX_TORCH_STORAGE) - AddonUtils.getTorchLevel(item);
        if (torchesNeeded > 0) {
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if (stack != null && stack.itemID == Block.torchWood.blockID) {
                    int loopTimes = torchesNeeded < stack.stackSize ? torchesNeeded : stack.stackSize;
                    for (int i2 = 0; i2 < loopTimes; i2++) {
                        AddonUtils.setTorchLevel(item, AddonUtils.getTorchLevel(item) + 1);
                        player.inventory.decrStackSize(i, 1);
                        if (stack.stackSize == 0) {
                            player.inventory.setInventorySlotContents(i, null);
                        }
                    }
                    player.sendChatToPlayer("[MPSA] Ate some torches. Torch level: " + AddonUtils.getTorchLevel(item) + "/" + (int) ModuleManager.computeModularProperty(item, MAX_TORCH_STORAGE));
                }
            }
        }
    }

    @Override
    public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {
    }

    @Override
    public void onRightClick(EntityPlayer playerClicking, World world, ItemStack item) {
    }

    @Override
    public void onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (AddonUtils.getTorchLevel(itemStack) > 0) {
            if ((player.canPlayerEdit(x, y, z, side, itemStack)) && (Block.blocksList[Block.torchWood.blockID].canPlaceBlockAt(world, x, y, z))) {
                int block = world.getBlockId(x, y, z);
                if ((block != Block.vine.blockID) && (block != Block.tallGrass.blockID) && (block != Block.deadBush.blockID) && ((Block.blocksList[block] == null) || (!Block.blocksList[block].isBlockReplaceable(world, x, y, z)))) {
                    x += (side == 5 ? 1 : side == 4 ? -1 : 0);
                    y += (side == 1 ? 1 : side == 0 ? -1 : 0);
                    z += (side == 3 ? 1 : side == 2 ? -1 : 0);
                }
                world.setBlock(x, y, z, Block.torchWood.blockID, getMetaForTorch(world, x, y, z, side), 2);
                Block.blocksList[Block.torchWood.blockID].onBlockAdded(world, x, y, z);
                AddonUtils.setTorchLevel(itemStack, AddonUtils.getTorchLevel(itemStack) - 1);
                ElectricItemUtils.drainPlayerEnergy(player, ModuleManager.computeModularProperty(itemStack, TORCH_ENERGY_CONSUMPTION));
            }
        } else {
            player.sendChatToPlayer("[MPSA] No torches!");
        }
    }

    @Override
    public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int par4) {
    }

    public int getMetaForTorch(World world, int x, int y, int z, int side) {
        return Block.blocksList[Block.torchWood.blockID].onBlockPlaced(world, x, y, z, side, x, y, z, 0);
    }
}
