package andrew.powersuits.tick;

import java.awt.Point;
import java.util.ArrayList;
import java.util.EnumSet;

import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.general.MuseRenderer;
import net.machinemuse.general.MuseStringUtils;
import net.machinemuse.powersuits.item.ItemPowerArmorHelmet;
import net.machinemuse.powersuits.item.ItemPowerGauntlet;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import andrew.powersuits.common.AddonUtils;
import andrew.powersuits.modules.AutoFeederModule;
import andrew.powersuits.modules.TorchPlacerModule;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class RenderTickHandler implements ITickHandler {
	
	double yBaseIcon = 10.0;
	int yBaseString = 14;
	double yOffsetIcon = 12.0;
	int yOffsetString = 15;
	
	ArrayList<String> modules;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		/*EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		modules = new ArrayList<String>();
		findInstalledModules(player);
		for (int i = 0; i < modules.size(); i++) {
			if (modules.get(i).equals(AutoFeederModule.MODULE_AUTO_FEEDER)) {
				int foodLevel = (int)AddonUtils.getFoodLevel(player.getCurrentArmor(3));
				String num = MuseStringUtils.formatNumberShort(foodLevel);
				if (i == 0) {
					MuseRenderer.drawString(num, 17, yBaseString);
					MuseRenderer.drawItemAt(-1.0, yBaseIcon, new ItemStack(Item.beefCooked));
				}
				else {
					MuseRenderer.drawString(num, 17, yOffsetString*(i+1));
					MuseRenderer.drawItemAt(-1.0, yOffsetIcon*(i+1), new ItemStack(Item.beefCooked));
				}
			}
			else if (modules.get(i).equals(TorchPlacerModule.MODULE_TORCH_PLACER)) {
				int torchLevel = AddonUtils.getTorchLevel(player.getCurrentEquippedItem());
				int maxTorchLevel = (int)ModuleManager.computeModularProperty(player.getCurrentEquippedItem(), TorchPlacerModule.MAX_TORCH_STORAGE);
				String num = MuseStringUtils.formatNumberShort(torchLevel)+"/"+MuseStringUtils.formatNumberShort(maxTorchLevel);
				if (i == 0) {
					MuseRenderer.drawString(num, 17, yBaseString);
					MuseRenderer.drawItemAt(-1.0, yBaseIcon, new ItemStack(Block.torchWood));
				}
				else {
					MuseRenderer.drawString(num, 17, yOffsetString*(i+1));
					MuseRenderer.drawItemAt(-1.0, yOffsetIcon*(i+1), new ItemStack(Block.torchWood));
				}
			}
		}*/
	}
	
	public void findInstalledModules(EntityClientPlayerMP player) {
		if (player != null) {
			ItemStack helmet = player.getCurrentArmor(3);
			if (helmet != null && helmet.getItem() instanceof ItemPowerArmorHelmet) {
				if (MuseItemUtils.itemHasActiveModule(helmet, AutoFeederModule.MODULE_AUTO_FEEDER)) {
					modules.add(AutoFeederModule.MODULE_AUTO_FEEDER);
				}
			}
			ItemStack tool = player.getCurrentEquippedItem();
			if (tool != null && tool.getItem() instanceof ItemPowerGauntlet) {
				if (MuseItemUtils.itemHasActiveModule(tool, TorchPlacerModule.MODULE_TORCH_PLACER)) {
					modules.add(TorchPlacerModule.MODULE_TORCH_PLACER);
				}
			}
		}
	}
	
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return "MPSA: Render Tick";
	}
}
