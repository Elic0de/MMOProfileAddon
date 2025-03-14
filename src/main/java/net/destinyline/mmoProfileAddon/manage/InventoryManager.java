package net.destinyline.mmoProfile.manage;

import fr.phoenixdevt.mmoprofiles.bukkit.MMOProfiles;
import fr.phoenixdevt.mmoprofiles.bukkit.gui.objects.EditableInventory;
import fr.phoenixdevt.mmoprofiles.bukkit.utils.ConfigFile;
import net.destinyline.mmoProfile.menu.AdditionalConfirmDeletionViewer;
import net.destinyline.mmoProfile.menu.CustomConfirmDeletionViewer;
import net.destinyline.mmoProfile.menu.CustomProfileListViewer;
import net.destinyline.mmoProfile.util.Utils;

import java.util.List;
import java.util.logging.Level;

/**
 * インベントリ管理を行うユーティリティクラス。
 */
public final class InventoryManager {

    // 定数定義：GUI関連のディレクトリパス
    private static final String LANGUAGE_GUI_DIRECTORY = "language/gui";
    private static final String CONFIG_FILE_DIRECTORY = "/language/gui";

    // 各インベントリビューアのインスタンス
    public static final CustomProfileListViewer PROFILE_LIST = new CustomProfileListViewer();
    public static final CustomConfirmDeletionViewer CONFIRM_DELETION = new CustomConfirmDeletionViewer();
    public static final AdditionalConfirmDeletionViewer ADDITIONAL_CONFIRM_DELETION_VIEWER = new AdditionalConfirmDeletionViewer();

    // 編集可能なインベントリのリスト（変更不可）
    public static final List<EditableInventory> INVENTORIES = List.of(PROFILE_LIST, CONFIRM_DELETION, ADDITIONAL_CONFIRM_DELETION_VIEWER);

    // インスタンス化を防ぐためのprivateコンストラクタ
    private InventoryManager() {
    }

    /**
     * 全てのインベントリの設定を再読み込みします。
     */
    public static void reload() {
        for (EditableInventory inventory : INVENTORIES) {
            String inventoryId = inventory.getId();

            // デフォルトのGUI言語ファイルをロード
            // todo
            try {
                MMOProfiles.plugin.configManager.loadDefaultFile(LANGUAGE_GUI_DIRECTORY, inventoryId + ".yml");
            } catch (NullPointerException e) {
                // 自身のresourcesからymlを読み込んでMMOProfiles/language/guiに保存する
                Utils.loadDefaultFile(LANGUAGE_GUI_DIRECTORY, inventoryId + ".yml");
            }

            try {
                ConfigFile configFile = new ConfigFile(CONFIG_FILE_DIRECTORY, inventoryId);
                inventory.reload(configFile.getConfig());
            } catch (IllegalArgumentException e) {
                MMOProfiles.plugin.getLogger().log(
                        Level.WARNING,
                        "Could not load inventory " + inventoryId + ": " + e.getMessage()
                );
            }
        }
    }
}

