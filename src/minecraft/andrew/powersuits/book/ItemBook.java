package andrew.powersuits.book;

import andrew.powersuits.common.GuiHandler;
import andrew.powersuits.common.ModularPowersuitsAddons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.machinemuse.powersuits.common.Config;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by User: Andrew2448
 * 11:54 PM 7/26/13
 */
public class ItemBook extends Item {

    public ItemBook(int i) {
        super(i);
        setMaxStackSize(1);
        setCreativeTab(Config.getCreativeTab());
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(ModularPowersuitsAddons.INSTANCE, GuiHandler.manualGuiID, world, 0, 0, 0);
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("PowersuitAddons:mpsManual");
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return "item.mpsManual";
    }

}
