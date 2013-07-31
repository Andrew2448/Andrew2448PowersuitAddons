package andrew.powersuits.common;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by User: Andrew2448
 * 1:34 PM 7/30/13
 */
public class Localization {
    public static final String LANG_PATH = "/mods/PowersuitAddons/lang/";
    public static String extractedLanguage = "";

    public static String getCurrentLanguage() {
        return StringTranslate.getInstance().getCurrentLanguage();
    }

    public static void loadCurrentLanguage() {
        if (getCurrentLanguage() != extractedLanguage) {
            extractedLanguage = getCurrentLanguage();
        }
        try {
            InputStream inputStream = ModularPowersuitsAddons.INSTANCE.getClass().getResourceAsStream(LANG_PATH + extractedLanguage + ".lang");
            Properties langPack = new Properties();
            langPack.load(new InputStreamReader(inputStream, Charsets.UTF_8));
            LanguageRegistry.instance().addStringLocalization(langPack, extractedLanguage);
        } catch (Exception e) {
            e.printStackTrace();
            AddonLogger.logError("Couldn't read MPSA localizations for language " + extractedLanguage + " :(");
        }
    }

    public static String translate(String str) {
        loadCurrentLanguage();
        return StatCollector.translateToLocal(str);
    }
}
