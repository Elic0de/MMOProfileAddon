package net.destinyline.mmoProfile;


import fr.phoenixdevt.mmoprofiles.bukkit.MMOProfiles;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import net.destinyline.mmoProfile.listeners.ProfileInvListener;
import net.destinyline.mmoProfile.manage.InventoryManager;
import net.destinyline.mmoProfile.service.ProfileDeletionService;

public final class MmoProfile extends JavaPlugin implements Listener {

    private static MmoProfile instance;

    private MMOProfiles profiles;
    private ProfileDeletionService service;

    @Override
    public void onEnable() {
        instance = this;
        service = new ProfileDeletionService();

        // register event
        Bukkit.getPluginManager().registerEvents(new ProfileInvListener(), this);
        // load profile instance
        loadMmoProfilesInstance();
        // todo hard code
        InventoryManager.reload();
    }

    private void loadMmoProfilesInstance() {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("MMOProfiles")) {
            profiles = MMOProfiles.plugin;
        }
    }

    public static MmoProfile getInstance() {
        return instance;
    }

    public MMOProfiles getProfiles() {
        return profiles;
    }

    public ProfileDeletionService getService() {
        return service;
    }
}
