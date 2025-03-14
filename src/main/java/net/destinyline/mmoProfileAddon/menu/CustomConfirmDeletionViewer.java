package net.destinyline.mmoProfileAddon.menu;

import fr.phoenixdevt.mmoprofiles.bukkit.gui.ConfirmDeletionViewer;
import fr.phoenixdevt.mmoprofiles.bukkit.gui.objects.item.InventoryItem;
import fr.phoenixdevt.mmoprofiles.bukkit.gui.objects.item.SimpleItem;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.destinyline.mmoProfileAddon.manage.InventoryManager;
import org.jetbrains.annotations.NotNull;

public class CustomConfirmDeletionViewer extends ConfirmDeletionViewer {

    public CustomConfirmDeletionViewer() {
        super();
    }

    // loadItem()メソッドをオーバーライドし、"profile"の処理をカスタムクラスに差し替え
    @Override
    public InventoryItem loadItem(String function, ConfigurationSection section) {
        if (function.equals("confirm-yes")) {
            return new YesItem(section);
        }
        // その他の処理は親クラスの実装をそのまま利用
        return super.loadItem(function, section);
    }

    public class YesItem extends SimpleItem<Generated> {
        public YesItem(ConfigurationSection var2) {
            super(var2);
        }

        public void onClick(@NotNull ConfirmDeletionViewer.Generated var1, @NotNull InventoryClickEvent var2) {
            final Player player = (Player) var2.getWhoClicked();

            InventoryManager.ADDITIONAL_CONFIRM_DELETION_VIEWER.generate(var1).open();
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);

        }
    }

}
