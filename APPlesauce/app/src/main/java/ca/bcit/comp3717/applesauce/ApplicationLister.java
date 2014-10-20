package ca.bcit.comp3717.applesauce;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class ApplicationLister
{
    private ApplicationLister()
    {}

    public static ArrayList<AppInfo> getInstalledApps(PackageManager pacman)
    {
        ArrayList<AppInfo> out = new ArrayList<AppInfo>();
        List<PackageInfo> packs = pacman.getInstalledPackages(0);

        for(PackageInfo p : packs)
        {
            if((p.applicationInfo.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) == 1)
                continue;
            AppInfo newApp = new AppInfo(p.applicationInfo.loadLabel(pacman).toString(),
                    p.packageName, p.versionName, p.versionCode,
                    p.applicationInfo.loadIcon(pacman));
            out.add(newApp);
        }

        return out;
    }
}
