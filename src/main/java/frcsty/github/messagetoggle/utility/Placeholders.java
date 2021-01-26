package frcsty.github.messagetoggle.utility;

import frcsty.github.messagetoggle.MessagePlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Placeholders extends PlaceholderExpansion {

    MessagePlugin plugin;
    
    public Placeholders(MessagePlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean persist()
    {
        return true;
    }

    @Override
    public boolean canRegister()
    {
        return true;
    }

    @Override
    public @NotNull String getIdentifier()
    {
        return "messagetoggle";
    }

    @Override
    public @NotNull String getAuthor()
    {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion()
    {
        return plugin.getDescription().getVersion();
    }


    public String onPlaceholderRequest(Player player, String params)
    {
        if (player == null)
        {
            return null;
        }
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("message-toggle");
        if (section == null)
        {
            return null;
        }
        Set<String> settings = section.getKeys(true);
        if (settings.contains(params))
        {
            return Boolean.toString(plugin.getFileManager().getUserToggleStatus(params, player));
        }
        return null;
    }
}