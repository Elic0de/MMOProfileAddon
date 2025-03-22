package net.destinyline.mmoProfileAddon;


import fr.phoenixdevt.mmoprofiles.bukkit.MMOProfiles;
import fr.phoenixdevt.mmoprofiles.bukkit.profile.PlayerProfileImpl;
import fr.phoenixdevt.mmoprofiles.bukkit.profile.ProfileListImpl;
import io.lumine.mythic.lib.api.event.SynchronizedDataLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import net.destinyline.mmoProfileAddon.manage.InventoryManager;
import net.destinyline.mmoProfileAddon.service.ProfileDeletionService;

public final class MMOProfileAddon extends JavaPlugin implements Listener {

    private static MMOProfileAddon instance;

    private MMOProfiles profiles;
    private ProfileDeletionService service;

    @Override
    public void onEnable() {
        instance = this;
        service = new ProfileDeletionService();
        // register event
        Bukkit.getPluginManager().registerEvents(this, this);
        // load profile instance
        loadMmoProfilesInstance();
        // todo hard code
        InventoryManager.reload();
    }

    @EventHandler
    public void onDataLoad(SynchronizedDataLoadEvent event) {
        if (event.getHolder() instanceof ProfileListImpl profileList) {
            (new PlayerProfileImpl(profileList.getPlayer().getLocation(), "Temporary Profile")).applyToPlayer(profileList);
            InventoryManager.PROFILE_LIST.generate(profileList).open();
        }
    }

    private void loadMmoProfilesInstance() {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("MMOProfiles")) {
            profiles = MMOProfiles.plugin;
        }
    }

    public static MMOProfileAddon getInstance() {
        return instance;
    }

    public MMOProfiles getProfiles() {
        return profiles;
    }

    public ProfileDeletionService getService() {
        return service;
    }
}
