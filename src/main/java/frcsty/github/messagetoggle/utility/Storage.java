package frcsty.github.messagetoggle.utility;

public class Storage
{

    private String reload;
    private String prefix;
    private String noPermission;
    private String invalidArgument;
    private String changedStatus;
    private String trueString;
    private String falseString;

    String getPrefix()
    {
        return prefix;
    }

    void setPrefix(final String prefix)
    {
        this.prefix = prefix;
    }

    public String getNoPermission()
    {
        return noPermission;
    }

    void setNoPermission(final String noPermission)
    {
        this.noPermission = noPermission;
    }

    public String getInvalidArgument()
    {
        return invalidArgument;
    }

    void setInvalidArgument(final String invalidArgument)
    {
        this.invalidArgument = invalidArgument;
    }

    public String getReload()
    {
        return reload;
    }

    void setReload(final String reload)
    {
        this.reload = reload;
    }

    public String getChangedStatus()
    {
        return changedStatus;
    }

    void setChangedStatus(final String changedStatus)
    {
        this.changedStatus = changedStatus;
    }


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
