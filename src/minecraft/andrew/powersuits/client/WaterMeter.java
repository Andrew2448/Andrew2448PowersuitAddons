package andrew.powersuits.client;

import net.machinemuse.general.gui.HeatMeter;
import net.machinemuse.utils.render.MuseRenderer;
import net.minecraft.block.Block;
import net.minecraft.util.Icon;

/**
 * Created by User: Andrew2448
 * 4:30 PM 6/21/13
 */
public class WaterMeter extends HeatMeter {
    public void draw(double xpos, double ypos, double value) {
        MuseRenderer.pushTexture(MuseRenderer.BLOCK_TEXTURE_QUILT);
        MuseRenderer.blendingOn();
        MuseRenderer.on2D();
        Icon icon = Block.waterStill.getIcon(0, 0);
        drawFluid(xpos, ypos, value, icon);
        drawGlass(xpos, ypos);
        MuseRenderer.off2D();
        MuseRenderer.blendingOff();
        MuseRenderer.popTexture();
    }
}
