package net.destinyline.mmoProfile.menu;

import fr.phoenixdevt.mmoprofiles.bukkit.MMOProfiles;
import fr.phoenixdevt.mmoprofiles.bukkit.gui.objects.EditableInventory;
import fr.phoenixdevt.mmoprofiles.bukkit.gui.objects.GeneratedInventory;
import fr.phoenixdevt.mmoprofiles.bukkit.gui.objects.Navigator;
import fr.phoenixdevt.mmoprofiles.bukkit.gui.objects.item.InventoryItem;
import fr.phoenixdevt.mmoprofiles.bukkit.gui.objects.item.SimpleItem;
import fr.phoenixdevt.mmoprofiles.bukkit.utils.ProfileUtils;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.destinyline.mmoProfile.MmoProfile;
import org.jetbrains.annotations.NotNull;

public class AdditionalConfirmDeletionViewer extends EditableInventory {
    public AdditionalConfirmDeletionViewer() {
        super("additional-confirm-deletion");
    }

    public InventoryItem loadItem(String var1, ConfigurationSection var2) {
        if (var1.equals("yes")) {
            return new YesItem(var2);
        } else {
            return var1.equals("back") ? new BackItem(var2) : null;
        }
    }

    @NotNull
    public Generated generate(@NotNull GeneratedInventory var1) {
        return new Generated(var1.getNaviguator());
    }

    public class YesItem extends SimpleItem<Generated> {
        public YesItem(ConfigurationSection var2) {
            super(var2);
        }

        public void onClick(@NotNull Generated var1, @NotNull InventoryClickEvent var2) {
            MmoProfile.getInstance().getService().deleteProfile(var2.getWhoClicked().getUniqueId());
            ProfileUtils.castScript(var1.getProfileList().getMMOPlayerData(), MMOProfiles.plugin.configManager.profileDeleteScript);
        }
    }

    public class BackItem extends SimpleItem<Generated> {
        public BackItem(ConfigurationSection var2) {
            super(var2);
        }

        public void onClick(@NotNull Generated var1, @NotNull InventoryClickEvent var2) {
            var1.getNaviguator().popOpen();
        }
    }

    public class Generated extends GeneratedInventory {

        public Generated(Navigator var2) {
            super(var2, AdditionalConfirmDeletionViewer.this);
        }

        public String applyNamePlaceholders(String var1) {
            return MythicLib.plugin.getPlaceholderParser().parse(this.player, var1);
        }
    }
}

