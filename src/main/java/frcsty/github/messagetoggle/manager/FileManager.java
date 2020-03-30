package frcsty.github.messagetoggle.manager;

import frcsty.github.messagetoggle.MessagePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class FileManager
{

    private final MessagePlugin plugin;
    private File file;
    private FileConfiguration config;

    public FileManager(final MessagePlugin plugin)
    {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "data.yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    private File getDataFile()
    {
        if (this.file == null) {
            this.file = new File(plugin.getDataFolder(), "data.yml");
        }
        return this.file;
    }

    public void createConfigFile()
    {
        if (!getDataFile().exists())
        {
            try
            {
                getDataFile().createNewFile();
            }
            catch (IOException ex)
            {
                plugin.getLogger().log(Level.WARNING, "Plugin failed to create a new file! 'data.yml' ", ex);
            }
        }
        this.saveFileAsync(true);
    }

    public void createFileSection()
    {
        if (!getDataConfig().isSet("users"))
        {
            getDataConfig().createSection("users");
        }
        this.saveFileAsync(true);
    }

    public void saveFileAsync(final boolean async)
    {
        if (!async)
        {
            try
            {
                getDataConfig().save(getDataFile());
            }
            catch (IOException ex)
            {
                plugin.getLogger().log(Level.SEVERE, "Plugin failed to save a file! 'data.yml' ", ex);
            }
        }
        else
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        getDataConfig().save(getDataFile());
                    }
                    catch (IOException ex)
                    {
                        plugin.getLogger().log(Level.SEVERE, "Plugin failed to save a file! 'data.yml' ", ex);
                    }
                }
            }.runTaskAsynchronously(this.plugin);
        }
    }

    public FileConfiguration getDataConfig()
    {
        if (this.config == null) {
            this.config = YamlConfiguration.loadConfiguration(getDataFile());
        }
        return this.config;
    }

    public void setUserToggleStatus(final String status, final Player player, final boolean newStatus)
    {
        getDataConfig().set("users." + player.getUniqueId() + "." + status, newStatus);
    }

    public boolean getUserToggleStatus(final String status, final Player player)
    {
        return getDataConfig().getBoolean("users." + player.getUniqueId() + "." + status);
    }

}

