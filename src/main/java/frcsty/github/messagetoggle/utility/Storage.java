package frcsty.github.messagetoggle.utility;

public class Storage
{

    private String prefix;
    String getPrefix()
    {
        return prefix;
    }
    void setPrefix(final String prefix)
    {
        this.prefix = prefix;
    }

    private String noPermission;
    public String getNoPermission()
    {
        return noPermission;
    }
    void setNoPermission(final String noPermission)
    {
        this.noPermission = noPermission;
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
