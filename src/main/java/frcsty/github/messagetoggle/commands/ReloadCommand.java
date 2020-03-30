package frcsty.github.messagetoggle.commands;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.lib.api.general.PAPIUtil;
import com.codeitforyou.lib.api.general.StringUtil;
import frcsty.github.messagetoggle.MessagePlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ReloadCommand
{

    @Command(permission = "toggle.reload")
    public static void execute(final CommandSender sender, final MessagePlugin plugin, final String[] args)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                plugin.reloadConfig();
                plugin.getStorageData().reloadMessageData();
                plugin.getFileManager().saveFileAsync(true);
            }
        }.runTaskAsynchronously(plugin);
        if (sender instanceof Player)
        {
            sender.sendMessage(PAPIUtil.parse((Player) sender, plugin.getStorage().getReload()));
        }
        else
        {
            sender.sendMessage(StringUtil.translate(plugin.getStorage().getReload()));
        }
    }

}
