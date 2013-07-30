package andrew.powersuits.book.page;

import andrew.powersuits.client.ManualGui;
import org.w3c.dom.Element;

/**
 * Created by User: Andrew2448
 * 12:14 AM 7/27/13
 */
public abstract class BookPage {
    protected ManualGui gui;
    protected int side;

    public void init(ManualGui gui, int side) {
        this.gui = gui;
        this.side = side;
    }

    public abstract void readPageFromXML(Element element);

    public void renderBackgroundLayer(int localwidth, int localheight) {}

    public abstract void renderContentLayer(int localwidth, int localheight);
}
