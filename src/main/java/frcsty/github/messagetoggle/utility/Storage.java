package frcsty.github.messagetoggle.utility;

import java.util.List;
import java.util.Map;

public class Storage
{

    private String       baseCommand;
    private List<String> alias;

    public String getBaseCommand()
    {
        return baseCommand;
    }
    void setBaseCommand(final String baseCommand)
    {
        this.baseCommand = baseCommand;
    }
    public List<String> getAlias()
    {
        return alias;
    }
    void setAlias(final List<String> alias)
    {
        this.alias = alias;
    }

    private String prefix;
    String getPrefix()
    {
        return prefix;
    }
    void setPrefix(final String prefix)
    {
        this.prefix = prefix;
    }

    private String usage;
    private String noPermission;
    private String playerOnly;
    private String unknownCommand;

    public String getUsage()
    {
        return usage;
    }
    void setUsage(final String usage)
    {
        this.usage = usage;
    }
    public String getNoPermission()
    {
        return noPermission;
    }
    void setNoPermission(final String noPermission)
    {
        this.noPermission = noPermission;
    }
    public String getPlayerOnly()
    {
        return playerOnly;
    }
    void setPlayerOnly(final String playerOnly)
    {
        this.playerOnly = playerOnly;
    }
    public String getUnknownCommand()
    {
        return unknownCommand;
    }
    void setUnknownCommand(final String unknownCommand)
    {
        this.unknownCommand = unknownCommand;
    }

    private Map<String, String> messages;
    public Map<String, String> getMessages()
    {
        return messages;
    }
    void setMessages(final Map<String, String> messages)
    {
        this.messages = messages;
    }

    private String invalidArgument;
    public String getInvalidArgument()
    {
        return invalidArgument;
    }
    void setInvalidArgument(final String invalidArgument)
    {
        this.invalidArgument = invalidArgument;
    }

    private String reload;
    public String getReload()
    {
        return reload;
    }
    void setReload(final String reload)
    {
        this.reload = reload;
    }

    private String changedStatus;
    public String getChangedStatus()
    {
        return changedStatus;
    }
    void setChangedStatus(final String changedStatus)
    {
        this.changedStatus = changedStatus;
    }

    private String trueString;
    private String falseString;
    public String getTrueString()
    {
        return trueString;
    }
    void setTrueString(final String trueString)
    {
        this.trueString = trueString;
    }
    public String getFalseString()
    {
        return falseString;
    }
    void setFalseString(final String falseString)
    {
        this.falseString = falseString;
    }

}
