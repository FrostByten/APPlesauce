package ca.bcit.comp3717.applesauce;

import android.graphics.drawable.Drawable;

public class AppInfo
{
    private String appname = "";
    private String pname = "";
    private String versionname = "";
    private int versioncode = 0;
    private Drawable icon;

    public AppInfo(String appname, String pname, String versionname, int versioncode, Drawable icon)
    {
        this.appname = appname;
        this.pname = pname;
        this.versionname = versionname;
        this.versioncode = versioncode;
        this.icon = icon;
    }

    public String getName()
    {
        return this.appname;
    }

    public String getPName()
    {
        return this.pname;
    }

    public String getVersionName()
    {
        return this.versionname;
    }

    public int getVersionCode()
    {
        return this.versioncode;
    }

    public Drawable getIcon()
    {
        return this.icon;
    }
}
