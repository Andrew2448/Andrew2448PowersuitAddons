package andrew.powersuits.tick;

import andrew.powersuits.common.AddonConfig;
import andrew.powersuits.common.AddonUtils;
import andrew.powersuits.modules.AutoFeederModule;
import andrew.powersuits.modules.ClockModule;
import andrew.powersuits.modules.CompassModule;
import andrew.powersuits.modules.TorchPlacerModule;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.powersuits.common.Config;
import net.machinemuse.powersuits.item.ItemPowerArmorHelmet;
import net.machinemuse.powersuits.item.ItemPowerFist;
import net.machinemuse.utils.MuseItemUtils;
import net.machinemuse.utils.MuseStringUtils;
import net.machinemuse.utils.render.MuseRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.EnumSet;

public class RenderTickHandler implements ITickHandler {

    static double yBaseIcon;
    static int yBaseString;
    static {
        if (Config.useGraphicalMeters()) {
            yBaseIcon = -1.0;
            yBaseString = 4;
        }
        else {
            yBaseIcon = 17.0;
            yBaseString = 23;
        }
    }
    double yOffsetIcon = 16.0;
    int yOffsetString = 18;
    String ampm = "";

    ArrayList<String> modules;

    ItemStack food = new ItemStack(Item.beefCooked);
    ItemStack torch = new ItemStack(Block.torchWood);
    ItemStack clock = new ItemStack(Item.pocketSundial);
    ItemStack compass = new ItemStack(Item.compass);

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        modules = new ArrayList<String>();
        findInstalledModules(player);
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).equals(AutoFeederModule.MODULE_AUTO_FEEDER)) {
                int foodLevel = (int) AddonUtils.getFoodLevel(player.getCurrentArmor(3));
                String num = MuseStringUtils.formatNumberShort(foodLevel);
                if (i == 0) {
                    MuseRenderer.drawString(num, 17, yBaseString);
                    MuseRenderer.drawItemAt(-1.0, yBaseIcon, food);
                } else {
                    MuseRenderer.drawString(num, 17, yBaseString + (yOffsetString*i));
                    MuseRenderer.drawItemAt(-1.0, yBaseIcon + (yOffsetIcon*i), food);
                }
            } else if (modules.get(i).equals(TorchPlacerModule.MODULE_TORCH_PLACER)) {
                int torchLevel = AddonUtils.getTorchLevel(player.getCurrentEquippedItem());
                int maxTorchLevel = (int) ModuleManager.computeModularProperty(player.getCurrentEquippedItem(), TorchPlacerModule.MAX_TORCH_STORAGE);
                String num = MuseStringUtils.formatNumberShort(torchLevel) + "/" + MuseStringUtils.formatNumberShort(maxTorchLevel);
                if (i == 0) {
                    MuseRenderer.drawString(num, 17, yBaseString);
                    MuseRenderer.drawItemAt(-1.0, yBaseIcon, torch);
                } else {
                    MuseRenderer.drawString(num, 17, yBaseString + (yOffsetString*i));
                    MuseRenderer.drawItemAt(-1.0, yBaseIcon + (yOffsetIcon*i), torch);
                }
            } else if (modules.get(i).equals(ClockModule.MODULE_CLOCK)) {
                long time = player.worldObj.provider.getWorldTime();
                int hour = (int)((time%24000)/1000);
                if (AddonConfig.use24hClock) {
                    if (hour < 19) {
                        hour += 6;
                    } else {
                        hour -= 18;
                    }
                    ampm = "h";
                }
                else {
                    if (hour < 6) {
                        hour += 6;
                        ampm = " AM";
                    } else if (hour == 6) {
                        hour = 12;
                        ampm = " PM";
                    } else if (hour > 6 && hour < 18) {
                        hour -= 6;
                        ampm = " PM";
                    } else if (hour == 18) {
                        hour = 12;
                        ampm = " AM";
                    } else {
                        hour -= 18;
                        ampm = " AM";
                    }
                }
                if (i == 0) {
                    MuseRenderer.drawString(hour + ampm, 17, yBaseString);
                    MuseRenderer.drawItemAt(-1.0, yBaseIcon, clock);
                } else {
                    MuseRenderer.drawString(hour + ampm, 17, yBaseString + (yOffsetString*i));
                    MuseRenderer.drawItemAt(-1.0, yBaseIcon + (yOffsetIcon*i), clock);
                }
            } else if (modules.get(i).equals(CompassModule.MODULE_COMPASS)) {
                if (i == 0) {
                    MuseRenderer.drawItemAt(-1.0, yBaseIcon, compass);
                } else {
                    MuseRenderer.drawItemAt(-1.0, yBaseIcon + (yOffsetIcon*i), compass);
                }
            }
        }
    }

    public void findInstalledModules(EntityClientPlayerMP player) {
        if (player != null) {
            ItemStack tool = player.getCurrentEquippedItem();
            if (tool != null && tool.getItem() instanceof ItemPowerFist) {
                if (MuseItemUtils.itemHasActiveModule(tool, TorchPlacerModule.MODULE_TORCH_PLACER)) {
                    modules.add(TorchPlacerModule.MODULE_TORCH_PLACER);
                }
            }
            ItemStack helmet = player.getCurrentArmor(3);
            if (helmet != null && helmet.getItem() instanceof ItemPowerArmorHelmet) {
                if (MuseItemUtils.itemHasActiveModule(helmet, AutoFeederModule.MODULE_AUTO_FEEDER)) {
                    modules.add(AutoFeederModule.MODULE_AUTO_FEEDER);
                }
                if (MuseItemUtils.itemHasActiveModule(helmet, ClockModule.MODULE_CLOCK)) {
                    modules.add(ClockModule.MODULE_CLOCK);
                }
                if (MuseItemUtils.itemHasActiveModule(helmet, CompassModule.MODULE_COMPASS)) {
                    modules.add(CompassModule.MODULE_COMPASS);
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
