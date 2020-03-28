package frcsty.github.messagetoggle.utility;

import frcsty.github.messagetoggle.MessagePlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public class StorageData
{
    private final MessagePlugin plugin;
    private final Storage storage;

    public StorageData(final MessagePlugin plugin)
    {
        this.plugin = plugin;
        this.storage = plugin.getStorage();
    }

    public void reloadMessageData()
    {
        final ConfigurationSection messages = plugin.getConfig().getConfigurationSection("messages");
        final ConfigurationSection settings = plugin.getConfig().getConfigurationSection("settings");
        final ConfigurationSection message = plugin.getConfig().getConfigurationSection("message-toggle");

        if (messages == null)
        {
            plugin.getLogger().log(Level.WARNING, "Could not reload data, missing section 'messages'!");
            return;
        }

        if (settings == null)
        {
            plugin.getLogger().log(Level.WARNING, "Could not reload data, missing section 'settings'!");
            return;
        }

        if (message == null)
        {
            plugin.getLogger().log(Level.WARNING, "Could not reload data, missing section 'message-toggle'!");
            return;
        }

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                storage.setPrefix(messages.getString("prefix"));

                storage.setNoPermission(replace(messages.getString("no-permission"), "%prefix%", storage.getPrefix()));
                storage.setInvalidArgument(replace(messages.getString("invalid-argument"), "%prefix%", storage.getPrefix()));
                storage.setReload(replace(messages.getString("reload"), "%prefix%", storage.getPrefix()));
                storage.setChangedStatus(replace(messages.getString("toggled-setting"), "%prefix%", storage.getPrefix()));

                storage.setTrueString(messages.getString("true"));
                storage.setFalseString(messages.getString("false"));
            }
        }.runTaskAsynchronously(plugin);
    }

    private String replace(final String message, final String target, final String replacement)
    {
        if (message == null || message.isEmpty())
        {
            return "null message";
        }

        if (message.contains(target))
        {
            return message.replace(target, replacement);
        }
        return message;
    }
}
