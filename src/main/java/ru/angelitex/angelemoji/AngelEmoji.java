package ru.angelitex.angelemoji;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.angelitex.angelemoji.emoji.EmojiConfig;
import ru.angelitex.angelemoji.emoji.EmojiSuggestionMode;
import ru.angelitex.angelemoji.listeners.EmojiFormatListener;
import ru.angelitex.angelemoji.listeners.ResourcePackListener;
import ru.angelitex.angelemoji.listeners.SuggestionListener;

import java.util.Arrays;

public final class AngelEmoji extends JavaPlugin {

    private EmojiConfig emojiConfig;
    private EmojiSuggestionMode emojiSuggestionMode;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        emojiConfig = new EmojiConfig(this);
        emojiSuggestionMode = switch (getConfig().getString("settings.suggestion-mode").toLowerCase()) {
            case "placeholder" -> EmojiSuggestionMode.PLACEHOLDER;
            case "emoji" -> EmojiSuggestionMode.EMOJI;
            default -> EmojiSuggestionMode.DISABLED;
        };

        registerListeners(new SuggestionListener(this), new ResourcePackListener(this), new EmojiFormatListener(this));
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    public EmojiConfig getEmojiConfig() {
        return emojiConfig;
    }

    public EmojiSuggestionMode getEmojiSuggestionMode() {
        return emojiSuggestionMode;
    }

}