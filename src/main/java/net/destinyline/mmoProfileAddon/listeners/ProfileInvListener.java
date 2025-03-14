package net.destinyline.mmoProfile.listeners;

import fr.phoenixdevt.mmoprofiles.bukkit.profile.ProfileListImpl;
import io.lumine.mythic.lib.api.event.SynchronizedDataLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import net.destinyline.mmoProfile.manage.InventoryManager;

public class ProfileInvListener implements Listener {

    @EventHandler
    public void onDataLoad(SynchronizedDataLoadEvent event) {
        final ProfileListImpl profileList = (ProfileListImpl) event.getHolder();
        InventoryManager.PROFILE_LIST.generate(profileList).open();
    }
}
