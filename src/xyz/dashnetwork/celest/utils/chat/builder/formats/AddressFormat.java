package xyz.dashnetwork.celest.utils.chat.builder.formats;

import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.sections.ComponentSection;
import xyz.dashnetwork.celest.utils.connection.Address;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class AddressFormat implements Format {

    private final List<ComponentSection> sections = new ArrayList<>();

    public AddressFormat(Address address) {
        String string = address.getString();

        ComponentSection section = new ComponentSection(string);
        section.filter(User::showAddress);

        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            if (!builder.isEmpty())
                builder.append(".");

            builder.append(random.nextInt(254));
        }

        ComponentSection filtered = new ComponentSection("&k" + builder + "&f");
        filtered.filter(each -> !each.showAddress());

        sections.add(section);
        sections.add(filtered);
    }

    @Override
    public List<ComponentSection> sections() { return sections; }

}
