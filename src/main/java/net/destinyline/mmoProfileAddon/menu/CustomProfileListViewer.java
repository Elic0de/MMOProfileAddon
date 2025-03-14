package net.destinyline.mmoProfile.menu;


import fr.phoenixdevt.mmoprofiles.bukkit.MMOProfiles;
import fr.phoenixdevt.mmoprofiles.bukkit.gui.ProfileListViewer;
import fr.phoenixdevt.mmoprofiles.bukkit.profile.PlayerProfileImpl;
import fr.phoenixdevt.mmoprofiles.bukkit.utils.ChatInput;
import fr.phoenixdevt.mmoprofiles.bukkit.utils.message.Message;
import io.lumine.mythic.lib.UtilityMethods;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import net.destinyline.mmoProfile.MmoProfile;
import net.destinyline.mmoProfile.manage.InventoryManager;
import org.jetbrains.annotations.NotNull;
import fr.phoenixdevt.mmoprofiles.bukkit.gui.objects.item.InventoryItem;

import java.util.List;

public class CustomProfileListViewer extends ProfileListViewer {

    private String removalName;
    private static final NamespacedKey EMPTY_SLOT_KEY;
    private static final NamespacedKey SLOT_NUMBER_KEY;

    public CustomProfileListViewer() {
        super();
    }

    static {
        EMPTY_SLOT_KEY = new NamespacedKey(MMOProfiles.plugin, "empty_slot");
        SLOT_NUMBER_KEY = new NamespacedKey(MMOProfiles.plugin, "slot_number");
    }

    // loadItem()メソッドをオーバーライドし、"profile"の処理をカスタムクラスに差し替え
    @Override
    public InventoryItem loadItem(String function, ConfigurationSection section) {
        if (function.equals("profile")) {
            return new CustomProfileItem(section);
        }
        // その他の処理は親クラスの実装をそのまま利用
        return super.loadItem(function, section);
    }

    // カスタムのProfileItemクラス
    public class CustomProfileItem extends ProfileItem {

        public CustomProfileItem(ConfigurationSection section) {
            super(section);
        }

        // クリック処理をカスタマイズする例
        @Override
        public void onClick(ProfileListViewer.Generated gen, @NotNull InventoryClickEvent event) {
            PersistentDataContainer dataContainer = event.getCurrentItem().getItemMeta().getPersistentDataContainer();

            if (dataContainer.has(EMPTY_SLOT_KEY)) {
                handleEmptySlot(gen);
            } else {
                handleProfileSlot(gen, event, dataContainer);
            }
        }

        private void handleEmptySlot(ProfileListViewer.Generated profileUI) {
            profileUI.getNaviguator().unblockClosing();

            if (MMOProfiles.plugin.configManager.profileNames) {
                Message.SET_PROFILE_NAME_ASK.format().send(profileUI.getPlayer());
                new ChatInput(profileUI.getProfileList(), profileUI, (inventory, inputName) -> {
                    if (isDuplicateProfileName(profileUI.getProfileList().getProfiles(), inputName)) {
                        Message.PROFILE_WITH_SAME_NAME.format().send(profileUI.getPlayer());
                        return false;
                    }
                    inventory.killInventory();
                    profileUI.getProfileList().createProfile(inputName)
                            .thenAccept(UtilityMethods.sync(MMOProfiles.plugin, createdProfile -> {
                                profileUI.getProfileList().applyProfile(createdProfile);
                            }));
                    return true;
                });
            } else {
                String autoProfileName = "Profile n" + (profileUI.getProfileList().getProfiles().size() + 1);
                profileUI.getProfileList().createProfile(autoProfileName)
                        .thenAccept(UtilityMethods.sync(MMOProfiles.plugin, createdProfile -> {
                            profileUI.getProfileList().applyProfile(createdProfile);
                        }));
            }
        }

        /**
         * プロファイル名の重複があるかどうかをチェックする
         */
        private boolean isDuplicateProfileName(List<PlayerProfileImpl> profiles, String profileName) {
            for (PlayerProfileImpl profile : profiles) {
                if (profile.getName().equals(profileName)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 空スロット以外の場合の処理
         */
        private void handleProfileSlot(ProfileListViewer.Generated profileUI, InventoryClickEvent event, PersistentDataContainer dataContainer) {
            Integer slotNumber = dataContainer.get(SLOT_NUMBER_KEY, PersistentDataType.INTEGER);
            if (slotNumber != null) {
                List<PlayerProfileImpl> profiles = profileUI.getProfileList().getProfiles();
                PlayerProfileImpl selectedProfile = profiles.get(slotNumber);
                if ((event.getClick() == ClickType.RIGHT)) {
                    MmoProfile.getInstance().getService().addDeletionCandidate(profileUI.getPlayer().getUniqueId(), selectedProfile);
                    InventoryManager.CONFIRM_DELETION.generate(profileUI, selectedProfile).open();
                } else if (event.getClick() == ClickType.LEFT) {
                    profileUI.getProfileList().applyProfile(selectedProfile);

                }
            }
        }
    }
}
