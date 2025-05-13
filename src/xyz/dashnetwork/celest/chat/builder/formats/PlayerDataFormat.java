package xyz.dashnetwork.celest.chat.builder.formats;

import xyz.dashnetwork.celest.chat.builder.Format;
import xyz.dashnetwork.celest.chat.builder.sections.ComponentSection;
import xyz.dashnetwork.celest.sql.data.PlayerData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PlayerDataFormat implements Format {

    private final List<ComponentSection> sections = new ArrayList<>();

    public PlayerDataFormat(PlayerData profile) {
        String username = profile.username();
        String uuid = profile.uuid().toString();

        ComponentSection section = new ComponentSection(username);
        section.hover("&6" + uuid);
        section.insertion(uuid);

        sections.add(section);
    }

    public PlayerDataFormat(Collection<PlayerData> collection, String separator) {
        for (PlayerData profile : collection) {
            if (!sections.isEmpty())
                sections.add(new ComponentSection(separator));

            sections.addAll(new PlayerDataFormat(profile).sections());
        }
    }

    @Override
    public List<ComponentSection> sections() { return sections; }

}
