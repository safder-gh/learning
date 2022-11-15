package com.example.hammadhanif.availabilityapplication.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;

import com.example.hammadhanif.availabilityapplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Common {
    public enum SearchType {
        PendingConsult,
        MyConsults,
        UpdateConsults
    }
    public static boolean fcmTokenRefreshed = false;
    public static final int AUDIO_FILE_REQUEST = 1;
    public static String[] caseFields = {"id","name","courtappearance_c","mattertype_c","typeofcase_c","county1_c","ct_county_id_c","addtionalcharges_c","casesynopsis_c","account_id","txt_charges_c","ch12_charges_id_c","notes_c","consultationstatus_c"};
    public static String[] accountsFields = {"id","name","cumiddlename_c","cuslastname_c","phone_office","phonework_c","email1","workphone_c"};
    public static String[] chargesFields = {"id","name","chargename_c"};
    public static String[] countyFields = {"id","name","description"};
    public static String[] availabilityFields = {"id","available","availableinoffice_c"};
    public static String[] km_attorneyavailability_usersFields = {"id","first_name","last_name"};
    public static Map<String,String> caseConsultationStatus = null;
    public static String md5(String s) throws NoSuchAlgorithmException {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes(StandardCharsets.UTF_8));
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            if ((result.length() % 2) != 0) {
                result = "0" + result;
            }
        }
        catch(NoSuchAlgorithmException ex){
            throw ex;
        }
        return result;
    }
    public static int count(String jsonString)throws org.json.JSONException {
        Integer returnVal = 0;
        if(jsonString == null){
            returnVal = 0;
        }else{
            returnVal = Integer.parseInt(new JSONObject(jsonString).get("result_count").toString());
        }
        return returnVal;
    }
    public static boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, 9000).show();
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setTitle(activity.getResources().getString(R.string.app_name));
                alert.setCancelable(false);
                alert.setMessage(R.string.play_services_warning);
                alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
            }
            return false;
        }
        return true;
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }
    public  static String encodeFileToBase64Binary(File file)
            throws IOException {
        byte[] bytes = loadFile(file);
        String encodedString = android.util.Base64.encodeToString(bytes,Base64.NO_WRAP);
        return encodedString;//"SXQgaXMganVzdCB0ZXN0Lg==";
    }
    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }
    public static String getBase64FromPath(File file ) {
        String base64 = "";
        try {/*from w  w w.j a v  a2 s  .  c  om*/

            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }
    public static long getTimeDiffrence(Timestamp timestamp){
        return ((new Timestamp(System.currentTimeMillis()).getTime()/1000)-(timestamp.getTime())/1000)/60;
    }
    public static File filePath(Activity activity){
        String myfolder= Environment.getExternalStorageDirectory()+"/SmartLawCRM";
        File file = new File(myfolder);
        if(!file.exists()){
            if(!file.mkdir()){
                File folder = activity.getFilesDir();
                file = new File(folder, "SmartLawCRM");
                if(!file.exists()){
                    file.mkdir();
                }

            }
        }
        return file;
    }
    public static Map<String,String> parseJSONObjectToMap(JSONObject jsonObject) throws JSONException {
        Map<String, String> mapData = new HashMap<String, String>();
        Iterator<String> keysItr = jsonObject.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            String value = (String)jsonObject.get(key);
            mapData.put(key, value);
        }
        return mapData;
    }
    public static int getIndexByKey(Map<String, String> mapData,String key){
        List keys = new ArrayList(mapData.keySet());
        int index = -1;
        for(int i=0;i<keys.size();i++){
            if(key.equalsIgnoreCase((String)keys.get(i))){
                index = i;
                break;
            }
        }
        return index;
    }
}
