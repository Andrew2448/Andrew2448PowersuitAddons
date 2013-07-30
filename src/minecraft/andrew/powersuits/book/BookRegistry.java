package andrew.powersuits.book;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User: Andrew2448
 * 2:25 PM 7/27/13
 */
public class BookRegistry {

    public static Map<String, ItemStack> manualIcons = new HashMap<String, ItemStack>();
    public static ItemStack defaultStack = new ItemStack(Item.ingotIron);

    public static void registerManualIcon (String name, ItemStack stack) {
        manualIcons.put(name, stack);
    }

    public static ItemStack getManualIcon (String textContent) {
        ItemStack stack = manualIcons.get(textContent);
        if (stack != null)
            return stack;
        return defaultStack;
    }
}
