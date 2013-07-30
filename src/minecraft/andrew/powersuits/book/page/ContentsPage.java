package andrew.powersuits.book.page;

import andrew.powersuits.book.BookRegistry;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by User: Andrew2448
 * 2:17 PM 7/27/13
 */
public class ContentsPage extends BookPage {
    String text;
    String[] iconText;
    ItemStack[] icons;

    @Override
    public void readPageFromXML (Element element)
    {
        NodeList nodes = element.getElementsByTagName("text");
        if (nodes != null)
            text = nodes.item(0).getTextContent();

        nodes = element.getElementsByTagName("link");
        iconText = new String[nodes.getLength()];
        icons = new ItemStack[nodes.getLength()];
        for (int i = 0; i < nodes.getLength(); i++)
        {
            NodeList children = nodes.item(i).getChildNodes();
            iconText[i] = children.item(1).getTextContent();
            icons[i] = BookRegistry.getManualIcon(children.item(3).getTextContent());
        }
    }

    @Override
    public void renderContentLayer (int localWidth, int localHeight)
    {
        if (text != null)
            gui.fonts.drawString("\u00a7n" + text, localWidth + 25 + gui.fonts.getStringWidth(text) / 2, localHeight + 4, 0);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        gui.renderitem.zLevel = 100;
        for (int i = 0; i < icons.length; i++)
        {
            gui.renderitem.renderItemIntoGUI(gui.fonts, gui.getMC().renderEngine, icons[i], localWidth + 16, localHeight + 18 * i + 18);
            int yOffset = 18;
            if (iconText[i].length() > 40)
                yOffset = 13;
            gui.fonts.drawString(iconText[i], localWidth + 38, localHeight + 18 * i + yOffset, 0);
        }
        gui.renderitem.zLevel = 0;
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    }
}
