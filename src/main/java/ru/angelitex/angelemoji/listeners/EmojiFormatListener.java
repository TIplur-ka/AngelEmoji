package ru.angelitex.angelemoji.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import ru.angelitex.angelemoji.AngelEmoji;

import java.util.HashMap;
import java.util.Map;

public class EmojiFormatListener implements Listener {

    private final HashMap<String, String> emojis;

    public EmojiFormatListener(AngelEmoji plugin) {
        emojis = plugin.getEmojiConfig().getEmojis();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent e) {
        e.message(formatComponent(e.message()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String message = e.getMessage();

        for (Map.Entry<String, String> entry : emojis.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }

        e.setMessage(message);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAnvil(PrepareAnvilEvent e) {
        ItemStack result = e.getResult();
        if (result == null) return;

        result.editMeta(meta -> meta.displayName(formatComponent(meta.displayName())));
    }

    @EventHandler
    public void onSign(SignChangeEvent e) {
        for (int i = 0; i < e.lines().size(); i++) {
            e.line(i, formatComponent(e.line(i)));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBookEdit(PlayerEditBookEvent e) {
        if (!e.isSigning()) return;

        BookMeta book = e.getNewBookMeta();

        for (int i = 1; i <= book.getPageCount(); i++) {
            book.page(i, formatComponent(book.page(i)));
        }

        e.setNewBookMeta(book);
    }

    private Component formatComponent(Component component) {
        for (Map.Entry<String, String> entry : emojis.entrySet()) {
            String placeholder = entry.getKey();
            String unicode = entry.getValue();

            component = component.replaceText(replaceAndFixColor(unicode, unicode)).replaceText(replaceAndFixColor(placeholder, unicode));
        }

        return component;
    }

    private TextReplacementConfig replaceAndFixColor(String match, String replacement) {
        return TextReplacementConfig.builder().match(match).replacement(Component.text(replacement).color(NamedTextColor.WHITE)).build();
    }

}
