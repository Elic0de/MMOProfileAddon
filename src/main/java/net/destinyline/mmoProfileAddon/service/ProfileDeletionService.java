package net.destinyline.mmoProfile.service;

import fr.phoenixdevt.mmoprofiles.bukkit.profile.PlayerProfileImpl;
import net.destinyline.mmoProfile.MmoProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * プレイヤーの削除候補プロファイルを保持し、最終削除処理を実行
 */
public class ProfileDeletionService {

    // 一時的に削除候補（プレイヤーUUID → 選択プロファイル）を保持するマップ
    private final Map<UUID, PlayerProfileImpl> deletionCandidates = new HashMap<>();

    /**
     * プレイヤーの削除候補を登録
     *
     * @param playerUUID プレイヤーUUID
     * @param profile    選択されたプロファイル
     */
    public void addDeletionCandidate(UUID playerUUID, PlayerProfileImpl profile) {
        deletionCandidates.put(playerUUID, profile);
    }

    /**
     * プレイヤーの削除候補プロファイルを取得し、実際に削除処理を実行
     *
     * @param playerUUID プレイヤーUUID
     */
    public void deleteProfile(UUID playerUUID) {
        PlayerProfileImpl profile = deletionCandidates.remove(playerUUID);
        if (profile != null) {
            MmoProfile.getInstance().getProfiles().getPlayerData(playerUUID).removeProfile(profile);
        }
    }
}