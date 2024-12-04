package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.GameProfileRequestEvent;
import com.velocitypowered.api.util.GameProfile;
import xyz.dashnetwork.celest.utils.profile.OfflineUser;

public final class GameProfileRequestListener {

    @Subscribe
    public void onGameProfileRequest(GameProfileRequestEvent event) {
        OfflineUser user = OfflineUser.getOfflineUser(event.getOriginalProfile());
        GameProfile profile = user.getRealJoin();

        OfflineUser.getOfflineUser(profile).disableSaving();

        event.setGameProfile(profile);
        user.setRealJoin(null);
    }

}
