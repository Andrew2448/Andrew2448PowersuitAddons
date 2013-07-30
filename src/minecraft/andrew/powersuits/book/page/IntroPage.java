package andrew.powersuits.book.page;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by User: Andrew2448
 * 2:16 PM 7/27/13
 */
public class IntroPage extends BookPage {
    String text;

    @Override
    public void readPageFromXML (Element element)
    {
        NodeList nodes = element.getElementsByTagName("text");
        if (nodes != null)
            text = nodes.item(0).getTextContent();
    }

    @Override
    public void renderContentLayer (int localWidth, int localHeight)
    {
        gui.fonts.drawSplitString(text, localWidth, localHeight, 178, 0);
    }
}
