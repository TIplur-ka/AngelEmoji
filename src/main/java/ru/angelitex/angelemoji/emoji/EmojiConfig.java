package ru.angelitex.angelemoji.emoji;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.angelitex.angelemoji.AngelEmoji;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class EmojiConfig {

    private final File emojiFile;
    private FileConfiguration emojiConfiguration;

    private final HashMap<String, String> emojis = new HashMap<>();

    public EmojiConfig(AngelEmoji plugin) {
        emojiFile = new File(plugin.getDataFolder(), "emojis.yml");
        if (!emojiFile.exists()) {
            emojiFile.getParentFile().mkdirs();
            plugin.saveResource("emojis.yml", false);
        }

        reloadEmojis();
    }

    public void reloadEmojis() {
        emojiConfiguration = YamlConfiguration.loadConfiguration(emojiFile);
        Set<String> keys = emojiConfiguration.getKeys(false);

        if (keys.isEmpty()) return;
        keys.forEach(key -> emojis.put(key, emojiConfiguration.getString(key)));
    }

    public Collection<String> getEmojisBySuggestionMode(EmojiSuggestionMode emojiSuggestionMode) {
        return switch (emojiSuggestionMode) {
            case DISABLED -> Collections.emptyList();
            case PLACEHOLDER -> emojis.keySet();
            case EMOJI -> emojis.values();
        };
    }

    public HashMap<String, String> getEmojis() {
        return emojis;
    }

}
