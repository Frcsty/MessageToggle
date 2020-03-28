package frcsty.github.messagetoggle.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.lib.api.general.PAPIUtil;
import frcsty.github.messagetoggle.MessagePlugin;
import frcsty.github.messagetoggle.manager.FileManager;
import frcsty.github.messagetoggle.utility.Storage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

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
            sender.sendMessage(PAPIUtil.parse(sender, storage.getInvalidArgument()));
            return;
        }

        if (!sender.hasPermission(permission))
        {
            sender.sendMessage(PAPIUtil.parse(sender, storage.getNoPermission()));
            return;
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

        manager.saveFileAsynchronous();
    }

}
