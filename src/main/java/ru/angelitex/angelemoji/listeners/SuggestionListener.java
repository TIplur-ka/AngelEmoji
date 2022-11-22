package ru.angelitex.angelemoji.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.angelitex.angelemoji.AngelEmoji;
import ru.angelitex.angelemoji.emoji.EmojiConfig;
import ru.angelitex.angelemoji.emoji.EmojiSuggestionMode;

public class SuggestionListener implements Listener {

    private EmojiSuggestionMode emojiSuggestionMode;
    private EmojiConfig emojisConfig;

    public SuggestionListener(AngelEmoji plugin) {
        this.emojiSuggestionMode = plugin.getEmojiSuggestionMode();
        this.emojisConfig = plugin.getEmojiConfig();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().addAdditionalChatCompletions(emojisConfig.getEmojisBySuggestionMode(emojiSuggestionMode));
    }

}
