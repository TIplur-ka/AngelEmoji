package ru.angelitex.angelemoji.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import ru.angelitex.angelemoji.AngelEmoji;

public class ResourcePackListener implements Listener {

    private final ConfigurationSection settingsSection;

    private final Component declinedMessage;
    private final Component downloadErrorMessage;

    public ResourcePackListener(AngelEmoji plugin) {
        this.settingsSection = plugin.getConfig().getConfigurationSection("settings.resourcePack");

        this.declinedMessage = getComponent("declined");
        this.downloadErrorMessage = getComponent("download-error");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (settingsSection.getBoolean("force")) e.getPlayer().setResourcePack(
                settingsSection.getString("url"),
                settingsSection.getString("sha1"));
    }

    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent e) {
        Player p = e.getPlayer();

        switch (e.getStatus()) {
            case DECLINED -> p.kick(declinedMessage);
            case FAILED_DOWNLOAD -> p.kick(downloadErrorMessage);
        }
    }

    private Component getComponent(String path) {
        return Component.text(settingsSection.getString("messages." + path));
    }

}
