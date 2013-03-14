package andrew.powersuits.modules;

import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.IRightClickModule;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.general.gui.MuseIcon;
import net.machinemuse.powersuits.common.ModularPowersuits;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.modules.PowerModuleBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RedstoneControlModule extends PowerModuleBase implements IRightClickModule {
	public RedstoneControlModule(List<IModularItem> validItems) {
		super(validItems);
		addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
		addInstallCost(new ItemStack(Item.redstone, 1));
	}

	@Override
	public MuseIcon getIcon(ItemStack item) {
		return new MuseIcon(Block.torchRedstoneActive.getTextureFile(), 61);
	}

	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_TOOL;
	}

	@Override
	public String getName() {
		return "Redstone Control";
	}

	@Override
	public String getDescription() {
		return "A way to activate redstone on any block on the go just by right clicking on it.";
	}

	@Override
	public void onRightClick(EntityPlayer player, World world, ItemStack item) {
		//player.
	}

}
