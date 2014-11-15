package ca.bcit.comp3717.applesauce;

public class Ignored {
    private long id;
    private String appname;
    private long ignored;

    public long getId() {
        return id;
    }

    public void setId(final long i) {
        id = i;
    }

    public String getAppName() {
        return appname;
    }

    public void setAppName(final String appname) {
        this.appname = appname;
    }

    public long getIgnored() {
        return ignored;
    }

    public void setIgnored(final long ignored) {
        this.ignored = ignored;
    }

    // For debugging only
    @Override
    public String toString() {
        return (appname + ", " + ignored);
    }

}
