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
    private final File folder;

    public FileManager(final MessagePlugin plugin)
    {
        this.plugin = plugin;
        this.folder = plugin.getDataFolder();
    }

    private File getDataFile()
    {
        return new File(folder, "data.yml");
    }

    private final FileConfiguration data = YamlConfiguration.loadConfiguration(getDataFile());

    public void saveDataFile()
    {
        try
        {
            data.save(getDataFile());
        }
        catch (IOException ex)
        {
            plugin.getLogger().log(Level.WARNING, "Failed to save file 'data.yml' ", ex);
        }
    }

    public void createDataFile()
    {
        if (!getDataFile().exists())
        {
            try
            {
                getDataFile().createNewFile();
            }
            catch (IOException ex)
            {
                plugin.getLogger().log(Level.WARNING, "Failed to create file 'data.yml' ", ex);
            }
        }
        else
        {
            plugin.getLogger().log(Level.WARNING, "Failed to create file 'data.yml'");
        }
        saveDataFile();
    }

    public void createFileSection()
    {
        if (!data.isSet("users"))
        {
            data.createSection("users");
        }
       saveDataFile();
    }

    public void saveFileAsynchronous()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    data.save(getDataFile());
                }
                catch (IOException ex)
                {
                    plugin.getLogger().log(Level.SEVERE, "Failed to save file 'data.yml' ", ex);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void setUserToggleStatus(final String status, final Player player, final boolean newStatus)
    {
        data.set("users." + player.getUniqueId() + "." + status, newStatus);
    }

    public boolean getUserToggleStatus(final String status, final Player player)
    {
        return data.getBoolean("users." + player.getUniqueId() + "." + status);
    }

}

