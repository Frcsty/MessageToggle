package frcsty.github.messagetoggle;

import com.codeitforyou.lib.api.general.StringUtil;
import frcsty.github.messagetoggle.manager.CommandsManager;
import frcsty.github.messagetoggle.manager.FileManager;
import frcsty.github.messagetoggle.utility.Storage;
import frcsty.github.messagetoggle.utility.StorageData;
import io.netty.channel.*;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MessagePlugin extends JavaPlugin implements Listener
{

    private final Storage         storage     = new Storage();
    private final StorageData     storageData = new StorageData(this);
    private final CommandsManager manager     = new CommandsManager(this);
    private final FileManager     fileManager = new FileManager(this);

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        fileManager.createDataFile();
        fileManager.createFileSection();

        storageData.reloadMessageData();

        manager.registerCommand();

        getServer().getPluginManager().registerEvents(this, this);
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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        injectPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        removePlayer(event.getPlayer());
    }

    private void removePlayer(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(player.getName());
            return null;
        });
    }

    private void injectPlayer(Player player)
    {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {

            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception{
                super.channelRead(channelHandlerContext, packet);
            }

            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {

                if(packet instanceof PacketPlayInChat){
                    PacketPlayInChat chatPacket = (PacketPlayInChat) packet;

                    Bukkit.getServer().broadcastMessage(StringUtil.translate("&cBLOCKED PACKET: " + chatPacket.toString()));
                    return;
                }
                super.write(channelHandlerContext, packet, channelPromise);
            }
        };

        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
    }
}
