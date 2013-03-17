package andrew.powersuits.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import andrew.powersuits.client.PortableCraftingGui;
import andrew.powersuits.container.PortableCraftingContainer;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case 0:
			return new PortableCraftingContainer(player.inventory, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case 0:
			return new PortableCraftingGui(player, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		default:
			return null;
		}
	}

}
