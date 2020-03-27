package frcsty.github.messagetoggle.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.lib.api.general.StringUtil;
import frcsty.github.messagetoggle.MessagePlugin;
import frcsty.github.messagetoggle.manager.FileManager;
import frcsty.github.messagetoggle.utility.Storage;
import org.bukkit.entity.Player;

public class ToggleCommand
{

    @Command(permission = "toggle.reload", aliases = {"toggle"}, usage = "toggle <prestige/announcer>", requiredArgs = 1)
    public static void execute(final Player sender, final MessagePlugin plugin, final String[] args)
    {
        final Storage storage = plugin.getStorage();
        final FileManager manager = plugin.getFileManager();
        final String argument = args[0];

        switch (argument)
        {
            case "prestige":
                final String prestigePerm = plugin.getConfig().getString("message-toggle.prestige.permission");
                if (prestigePerm != null && sender.hasPermission(prestigePerm))
                {
                    if (manager.getUserToggleStatus("prestige", sender))
                    {
                        manager.setUserToggleStatus("prestige", sender, false);
                        sender.sendMessage(StringUtil.translate(storage.getChangedStatus()
                        .replace("%status%", storage.getFalseString())
                        .replace("%toggle%", argument))
                        );
                    }
                    else
                    {
                        manager.setUserToggleStatus("prestige", sender, true);
                        sender.sendMessage(StringUtil.translate(storage.getChangedStatus()
                        .replace("%status%", storage.getTrueString())
                        .replace("%toggle%", argument))
                        );
                    }
                }
                else
                {
                    sender.sendMessage(StringUtil.translate(storage.getNoPermission()));
                }
                break;
            case "announcer":
                final String announcerPerm = plugin.getConfig().getString("message-toggle.announcer.permission");
                if (announcerPerm != null && sender.hasPermission(announcerPerm))
                {
                    if (manager.getUserToggleStatus("announcer", sender))
                    {
                        manager.setUserToggleStatus("announcer", sender, false);
                        sender.sendMessage(StringUtil.translate(storage.getChangedStatus()
                        .replace("%status%", storage.getFalseString())
                        .replace("%toggle%", argument))
                        );
                    }
                    else
                    {
                        manager.setUserToggleStatus("announcer", sender, true);
                        sender.sendMessage(StringUtil.translate(storage.getChangedStatus()
                        .replace("%status%", storage.getTrueString())
                        .replace("%toggle%", argument))
                        );
                    }
                }
                else
                {
                    sender.sendMessage(StringUtil.translate(storage.getNoPermission()));
                }
                break;
            default:
                sender.sendMessage(StringUtil.translate(storage.getInvalidArgument()));
                break;
        }
        manager.saveFileAsynchronous();
    }

}
