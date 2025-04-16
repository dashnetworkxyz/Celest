package xyz.dashnetwork.celest.chat.builder;

import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import xyz.dashnetwork.celest.connection.User;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Section {

    Section hover(String text);

    Section hover(String text, Consumer<Section> subsection);

    Section hover(char translate, String text);

    Section hover(char translate, String text, Consumer<Section> subsection);

    Section hover(Section section);

    Section click(ClickEvent event);

    Section insertion(String text);

    Section color(TextColor color);

    Section colorIfAbsent(TextColor color);

    Section obfuscate();

    Section obfuscate(boolean obfuscate);

    Section bold();

    Section bold(boolean bold);

    Section strikethrough();

    Section strikethrough(boolean strikethrough);

    Section underline();

    Section underline(boolean underline);

    Section italic();

    Section italic(boolean italic);

    Section ifUser(Predicate<User> predicate);

}
