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
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }

        return result;
    }
}
