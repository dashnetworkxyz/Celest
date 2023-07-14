package xyz.dashnetwork.celest.utils.chat;

import com.google.common.annotations.Beta;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;

@Beta
public final class StyleUtils {

    public static boolean hasColor(Style style) {
        if (style.color() != null)
            return true;

        if (style.hasDecoration(TextDecoration.OBFUSCATED))
            return true;

        if (style.hasDecoration(TextDecoration.BOLD))
            return true;

        if (style.hasDecoration(TextDecoration.STRIKETHROUGH))
            return true;

        if (style.hasDecoration(TextDecoration.UNDERLINED))
            return true;

        return style.hasDecoration(TextDecoration.ITALIC);
    }

}
