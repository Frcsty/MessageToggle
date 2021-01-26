package frcsty.github.messagetoggle.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.lib.api.general.PAPIUtil;
import frcsty.github.messagetoggle.MessagePlugin;
import frcsty.github.messagetoggle.manager.FileManager;
import frcsty.github.messagetoggle.utility.Storage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Set;

public class ToggleCommand
{

    @Command(permission = "toggle.reload", aliases = {"toggle"}, usage = "toggle <prestige/announcer>", requiredArgs = 1)
    public static void execute(final Player sender, final MessagePlugin plugin, final String[] args)
    {
        final Storage storage = plugin.getStorage();
        final FileManager manager = plugin.getFileManager();
        final String argument = args[0];

        String permission = plugin.getConfig().getString("message-toggle." + argument + ".permission");
        String message = plugin.getConfig().getString("message-toggle." + argument + ".message");

        if (permission == null || message == null)
        {
            sender.sendMessage(PAPIUtil.parse(sender, storage.getInvalidArgument().replace("{usage}", "/")));
            return;
        }

        if (!sender.hasPermission(permission))
        {
            final String command = plugin.getConfig().getString("settings.base-command");
            if (command == null)
            {
                sender.sendMessage(PAPIUtil.parse(sender, storage.getNoPermission()));
                return;
            }
            final ConfigurationSection section = plugin.getConfig().getConfigurationSection("message-toggle");
            if (section == null)
            {
                sender.sendMessage(PAPIUtil.parse(sender, storage.getNoPermission()));
                return;
            }
            final Set<String> messages = section.getKeys(false);
            StringBuilder usage = new StringBuilder("<");
            for (String msg : messages) {
                usage.append(msg).append("/");
            }
            sender.sendMessage(PAPIUtil.parse(sender, storage.getNoPermission().replace("{usage}", command + usage.substring(0, usage.length()) + ">")));
            return;
        }

        if (!manager.getDataConfig().isSet("users"))
        {
            manager.createFileSection();
        }

        if (manager.getUserToggleStatus(argument, sender))
        {
            sender.sendMessage(PAPIUtil.parse(sender, storage.getChangedStatus()
                                              .replace("%status%", storage.getFalseString())
                                              .replace("%toggle%", StringUtils.capitalize(argument))));
            manager.setUserToggleStatus(argument, sender, false);
        }
        else
        {
            sender.sendMessage(PAPIUtil.parse(sender, storage.getChangedStatus()
                               .replace("%status%", storage.getTrueString())
                               .replace("%toggle%", StringUtils.capitalize(argument))));
            manager.setUserToggleStatus(argument, sender, true);
        }

        manager.saveFileAsync(true);
    }

}
