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
import frcsty.github.messagetoggle.utility.Storage;
import frcsty.github.messagetoggle.utility.StorageData;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.ChatColor;
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

        fileManager.createDataFile();
        fileManager.createFileSection();
        fileManager.saveFileAsynchronous();

        storageData.reloadMessageData();

        manager.registerCommand();

        getServer().getPluginManager().registerEvents(this, this);

        protocolManager = ProtocolLibrary.getProtocolManager();

        try
        {
            protocolManager.addPacketListener(
                    new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT)
                    {
                        @Override
                        public void onPacketSending(PacketEvent event)
                        {
                            if (event.getPacketType() == PacketType.Play.Server.CHAT)
                            {
                                final PacketContainer packet = event.getPacket();
                                final WrappedChatComponent component = packet.getChatComponents().read(0);
                                final String message = ChatColor.stripColor(TextComponent.toLegacyText(ComponentSerializer.parse(component.getJson())));

                                for (String messages : plugin.getConfig().getConfigurationSection("message-toggle").getKeys(false))
                                {
                                    String msg = plugin.getConfig().getString("message-toggle." + messages + ".message");

                                    if (msg == null)
                                    {
                                        return;
                                    }

                                    if (message.contains(msg))
                                    {
                                        if (fileManager.getUserToggleStatus(messages, event.getPlayer()))
                                        {
                                            event.setCancelled(true);
                                        }
                                    }
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

    @Override
    public void onDisable()
    {
        reloadConfig();
        fileManager.saveDataFile();
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

}
