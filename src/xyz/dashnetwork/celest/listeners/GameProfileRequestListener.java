package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.GameProfileRequestEvent;
import xyz.dashnetwork.celest.utils.profile.OfflineUser;

public final class GameProfileRequestListener {

    @Subscribe
    public void onGameProfileRequest(GameProfileRequestEvent event) {
        OfflineUser user = OfflineUser.getOfflineUser(event.getOriginalProfile());

        event.setGameProfile(user.getRealJoin());
        user.setRealJoin(null);
    }

}
