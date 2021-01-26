package frcsty.github.messagetoggle;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import frcsty.github.messagetoggle.manager.CommandsManager;
import frcsty.github.messagetoggle.manager.FileManager;
import frcsty.github.messagetoggle.utility.Placeholders;
import frcsty.github.messagetoggle.utility.Storage;
import frcsty.github.messagetoggle.utility.StorageData;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MessagePlugin extends JavaPlugin implements Listener
{

    private final Storage         storage     = new Storage();
    private final StorageData     storageData = new StorageData(this);
    private final CommandsManager manager     = new CommandsManager(this);
    private final FileManager     fileManager = new FileManager(this);

    private ProtocolManager protocolManager;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        reloadConfig();

        fileManager.createConfigFile();
        fileManager.createFileSection();

        storageData.reloadMessageData();

        manager.registerCommand();

        getServer().getPluginManager().registerEvents(this, this);

        protocolManager = ProtocolLibrary.getProtocolManager();
        registerNewPacket();

        fileManager.saveFileAsync(true);

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new Placeholders(this).register();
        }
    }

    @Override
    public void onDisable()
    {
        reloadConfig();
        fileManager.saveFileAsync(false);
    }

    public Storage getStorage()
    {
        return storage;
    }

    public StorageData getStorageData()
    {
        return storageData;
    }

    public FileManager getFileManager()
    {
        return fileManager;
    }

    public CommandsManager getManager()
    {
        return manager;
    }

    private void registerNewPacket()
    {
        try
        {
            protocolManager.addPacketListener(
                    new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT)
                    {
                        @Override
                        public void onPacketSending(PacketEvent event)
                        {
                            if (event.getPacketType() != PacketType.Play.Server.CHAT)
                            {
                                return;
                            }

                            final PacketContainer packet = event.getPacket();
                            final WrappedChatComponent component = packet.getChatComponents().read(0);
                            if (component == null)
                            {
                                return;
                            }

                            final String json = component.getJson();
                            if (json == null)
                            {
                                return;
                            }
                            final BaseComponent[] baseComponent = ComponentSerializer.parse(json);
                            if (baseComponent == null)
                            {
                                return;
                            }
                            final String message = ChatColor.stripColor(TextComponent.toLegacyText(baseComponent));

                            final ConfigurationSection section = plugin.getConfig().getConfigurationSection("message-toggle");

                            if (section == null)
                            {
                                return;
                            }

                            for (String messages : section.getKeys(false))
                            {
                                final String msg = plugin.getConfig().getString("message-toggle." + messages + ".message");
                                final String type = plugin.getConfig().getString("message-toggle." + messages + ".type");

                                if (msg == null)
                                {
                                    continue;
                                }

                                if (type == null)
                                {
                                    continue;
                                }

                                if (!fileManager.getUserToggleStatus(messages, event.getPlayer()))
                                {
                                    continue;
                                }

                                switch (type)
                                {
                                    case "equals":
                                        if (message.equals(msg))
                                        {
                                            event.setCancelled(true);
                                        }
                                        break;
                                    case "equalsIgnoreCase":
                                        if (message.equalsIgnoreCase(msg))
                                        {
                                            event.setCancelled(true);
                                        }
                                        break;
                                    case "startsWith":
                                        if (message.startsWith(msg))
                                        {
                                            event.setCancelled(true);
                                        }
                                        break;
                                    case "startsWithIgnoreCase":
                                        if (message.toLowerCase().startsWith(msg.toLowerCase()))
                                        {
                                            event.setCancelled(true);
                                        }
                                        break;
                                    case "contains":
                                        if (message.contains(msg))
                                        {
                                            event.setCancelled(true);
                                        }
                                        break;
                                }
                            }
                        }
                    }
            );
        }
        catch (IllegalStateException ex)
        {
            getLogger().log(Level.WARNING, "Error executing packets!", ex);
        }
    }

}
