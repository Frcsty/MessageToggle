package frcsty.github.messagetoggle.manager;

import com.codeitforyou.lib.api.command.CommandManager;
import com.codeitforyou.lib.api.general.StringUtil;
import frcsty.github.messagetoggle.MessagePlugin;
import frcsty.github.messagetoggle.commands.ReloadCommand;
import frcsty.github.messagetoggle.commands.ToggleCommand;
import frcsty.github.messagetoggle.utility.TabComplete;

import java.util.Collections;

public class CommandsManager
{

    private final CommandManager        manager;
    private final CommandManager.Locale locale;
    private final MessagePlugin         plugin;
    private final TabComplete           tabComplete;

    public CommandsManager(final MessagePlugin plugin)
    {
        this.plugin = plugin;

        this.manager = new CommandManager(Collections.singletonList(
                ToggleCommand.class
        ), plugin.getConfig().getString("settings.base-command"), plugin);

        this.locale = manager.getLocale();
        this.tabComplete = new TabComplete(plugin);
    }

    public void registerCommand()
    {
        this.createCommandAttributes();

        this.manager.register();

        final String command = plugin.getConfig().getString("settings.base-command");
        if (command != null)
        {
            plugin.getCommand(command).setTabCompleter(tabComplete);
        }
    }

    private void createCommandAttributes()
    {
        this.manager.setMainCommand(ReloadCommand.class);

        for (final String cmd : plugin.getConfig().getStringList("settings.alias"))
        {
            this.manager.addAlias(cmd);
        }

        this.locale.setUsage(getDefaultMessage(plugin.getConfig().getString("messages.usage")));
        this.locale.setUnknownCommand(getDefaultMessage(plugin.getConfig().getString("messages.unknown-command")));
        this.locale.setPlayerOnly(getDefaultMessage(plugin.getConfig().getString("messages.player-only")));
        this.locale.setNoPermission(getDefaultMessage(plugin.getConfig().getString("messages.no-permission")));
    }

    private String getDefaultMessage(final String message)
    {
        return message == null ? StringUtil.translate("&8[&9MessageToggle&8] &cNo default message specified in the config!") : StringUtil.translate(message);
    }

    public CommandManager getManager()
    {
        return manager;
    }

}
