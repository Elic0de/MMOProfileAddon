package net.destinyline.mmoProfile.util;

import fr.phoenixdevt.mmoprofiles.bukkit.MMOProfiles;
import net.destinyline.mmoProfile.MmoProfile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

public class Utils {

    public static void loadDefaultFile(String subDirectory, String fileName) {
        // todo
        File dataFolder = MMOProfiles.plugin.getDataFolder();

        // サブディレクトリが指定されていれば、そのディレクトリを対象とし、なければ dataFolder をそのまま利用する
        File targetDir = subDirectory.isEmpty() ? dataFolder : new File(dataFolder, subDirectory);

        // ディレクトリが存在しなければ作成（必要なら親ディレクトリも作成）
        if (!targetDir.exists() && !targetDir.mkdirs()) {
            MMOProfiles.plugin.getLogger().severe("Could not create directory: " + targetDir.getAbsolutePath());
            return;
        }

        // 対象ファイルを生成
        File targetFile = new File(targetDir, fileName);

        // ファイルが存在しない場合はデフォルトリソースからコピーする
        if (!targetFile.exists()) {
            try (InputStream resourceStream = MmoProfile.getInstance().getResource(fileName)) {
                if (resourceStream == null) {
                    MMOProfiles.plugin.getLogger().warning("Resource not found: " + fileName);
                    return;
                }
                Files.copy(resourceStream, targetFile.toPath());
            } catch (IOException e) {
                MMOProfiles.plugin.getLogger().log(Level.SEVERE,
                        "Error copying resource " + fileName + " to " + targetFile.getAbsolutePath(), e);
            }
        }
    }
}
