package ca.bcit.comp3717.applesauce;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Sample {

    public static JSONObject getSample() {
        JSONObject result = new JSONObject();
        List<String> list = new ArrayList<String>();

        list.add("com.i6.FlightSimulatorAirplane3D");
        list.add("com.ludia.dragons");
        list.add("es.socialpoint.DragonCity");
        list.add("com.miniclip.plagueinc");
        list.add("minecraft.steve.kill");
        list.add("it.rortos.extremelandings");
        list.add("com.mgo.hunter2014");
        list.add("com.guanggao.SpeedSniperDeath.Wgame5");
        list.add("com.gametronic.gun.shot.simulator");
        list.add("it.rortos.f18carrierlandingii");
        list.add("air.com.oranginalplan.weaphonesvol2free");
        list.add("com.mp.militaryparking");
        list.add("com.bitofgame.snipertraining3d2");
        list.add("com.giantssoftware.fs14");
        list.add("com.movingames.dinosaurassassinhd");
        list.add("es.socialpoint.MonsterLegends");
        list.add("com.hotheadgames.google.free.valor");
        list.add("com.hotheadgames.google.free.bigwinbasketball");
        list.add("com.hotheadgames.google.free.bigwinfootball");
        list.add("com.hotheadgames.google.free.bigwinsoccer");
        list.add("com.hotheadgames.google.free.rawspace");
        list.add("com.hotheadgames.google.free.bigwinstockcar");
        list.add("com.hotheadgames.android.seastars");
        list.add("com.hotheadgames.google.free.valorcmd");
        list.add("com.hotheadgames.google.free.bigwinhockey");
        list.add("com.hotheadgames.google.free.bigwinbaseball");
        list.add("com.hotheadgames.google.free.bigleaguebaseball");
        list.add("com.hotheadgames.google.free.bigleaguehockey");
        list.add("com.hotheadgames.google.free.spywars");
        list.add("com.hotheadgames.google.free.zombie_ace");

        JSONArray array = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            array.put(list.get(i));
        }

        try
        {
            result.put("packageID","com.hotheadgames.google.free.rawsniper");
            result.put("appName", "Kill Shot");
            result.put("logo", "https://lh4.ggpht.com/glwlcnYFPE0fuS58CrAZQjj7TPYOP2vqu9faGZBYmYzThARe_ztXqvDqzplUh2KsvEo=w300");
            result.put("playStoreUrl", "https://play.google.com/store/apps/details?id=com.hotheadgames.google.free.rawsniper");
            result.put("recommendedApps", array);
        }
        catch(JSONException e)
        {
            Log.d("getSample", e.getLocalizedMessage());
        }

        return result;
    }

    public static ArrayList<JSONObject> getSampleRecApps()
    {

        ArrayList<JSONObject> result = new ArrayList<JSONObject>();

        try
        {
            JSONObject app = new JSONObject();
            app.put("packageID","com.hotheadgames.google.free.rawsniper");
            app.put("appName", "Kill Shot");
            app.put("logo", "https://lh4.ggpht.com/glwlcnYFPE0fuS58CrAZQjj7TPYOP2vqu9faGZBYmYzThARe_ztXqvDqzplUh2KsvEo=w300");
            app.put("playStoreUrl", "https://play.google.com/store/apps/details?id=com.hotheadgames.google.free.rawsniper");

            result.add(app);

            app = new JSONObject();
            app.put("packageID","com.kixeye.vegaconflict");
            app.put("appName", "VEGA Conflict");
            app.put("logo", "https://lh3.ggpht.com/jg3rQDHtVrHmY2lh7Qd9s-TGQydBrUAAvcA_Dw4LyhTQOdBFSaxhjWKvxunACPBnYvk=w300");
            app.put("playStoreUrl", "https://play.google.com/store/apps/details?id=com.kixeye.vegaconflict");

            result.add(app);

            app = new JSONObject();
            app.put("packageID","com.mobage.ww.a1903.SWTD_Android");
            app.put("appName", "Star Wars ™: Galactic Defense");
            app.put("logo", "https://lh5.ggpht.com/mEsUA2RHhZvA6UE7YcBmsYyOlRX4pzgIDMxtDbMbHXPhtY-E3XomURaalcqCSxNog5X4=w300");
            app.put("playStoreUrl", "https://play.google.com/store/apps/details?id=com.mobage.ww.a1903.SWTD_Android");

            result.add(app);

            app = new JSONObject();
            app.put("packageID","com.kabam.metalskies.android");
            app.put("appName", "Metal Skies");
            app.put("logo", "https://lh3.ggpht.com/kebXmCS-LkyTH2yuKdthseNr0Q6dmycWGzR9zCRHYLRm5PayBbxwgOM9CYYnpeaz2b-H=w300");
            app.put("playStoreUrl", "https://play.google.com/store/apps/details?id=com.kabam.metalskies.android");

            result.add(app);

            app = new JSONObject();
            app.put("packageID","jp.co.ponos.battlecatsen");
            app.put("appName", "The Battle Cats");
            app.put("logo", "https://lh3.ggpht.com/P4otz7aGNuiiR4DlYgM7Mk0Se2VjrhAoIRwOG2c6nj_gP_fVHxaj1wVNPr6nPgWmkg=w300");
            app.put("playStoreUrl", "https://play.google.com/store/apps/details?id=jp.co.ponos.battlecatsen");

            result.add(app);

            app = new JSONObject();
            app.put("packageID","com.supercell.clashofclans");
            app.put("appName", "Clash of Clans");
            app.put("logo", "https://lh6.ggpht.com/1eVPA6Iukw-F4i5xq1ZWicaKBzmprLGw98YhdG20E-wlsHHg3PcKJqbY_fWLdJeGRw=w300");
            app.put("playStoreUrl", "https://play.google.com/store/apps/details?id=com.supercell.clashofclans");

            result.add(app);

            app = new JSONObject();
            app.put("packageID","com.ea.game.simpsons4_na");
            app.put("appName", "The Simpsons™: Tapped Out");
            app.put("logo", "https://lh3.ggpht.com/nEpv1ZTbzVeQIlHJOdl_I072IMtEIUh70n_n81WC8lgw-uKy1a-gL8aN1O2MOEiOIg=w300");
            app.put("playStoreUrl", "https://play.google.com/store/apps/details?id=com.ea.game.simpsons4_na");

            result.add(app);
        }
        catch (JSONException e)
        {
            Log.d("getSampleRecApps", e.getLocalizedMessage());
        }

        return result;
    }
}
