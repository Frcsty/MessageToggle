package frcsty.github.messagetoggle.utility;

import frcsty.github.messagetoggle.MessagePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TabComplete implements TabCompleter
{

    private final MessagePlugin plugin;

    public TabComplete(final MessagePlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String alias, String[] args)
    {

        if (args.length > 2)
        {
            return Collections.emptyList();
        }

        if (args.length == 1)
        {
            final List<String> commands = new ArrayList<>();
            for (final Map.Entry<String, Method> commandPair : plugin.getManager().getManager().getCommands().entrySet())
            {
                if (commandPair.getKey().toLowerCase().startsWith(args[0].toLowerCase()))
                {
                    commands.add(commandPair.getKey());
                }
            }

            return commands;
        }
        else if (args.length == 2)
        {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("message-toggle");
            if (section == null)
            {
                return Collections.emptyList();
            }

            return new ArrayList<>(section.getKeys(false));
        }
        return Collections.emptyList();
    }

}
