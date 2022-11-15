package com.kmincorp.smartlawcrm.Utilities;

import android.os.Build;

import com.kmincorp.smartlawcrm.Charges;
import com.kmincorp.smartlawcrm.County;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Field;

public class Common {
    public static  String getOsInfo(){
        StringBuilder builder = new StringBuilder();
        builder.append("android : ").append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(" : ").append(fieldName).append(" : ");
                builder.append("sdk=").append(fieldValue);
            }
        }
        return builder.toString();
    }
    public static String toInitCap(String param) {
        if (param != null && param.length() > 0) {
            char[] charArray = param.toCharArray();
            charArray[0] = Character.toUpperCase(charArray[0]);
            // set capital letter to first position
            return new String(charArray);
            // return desired output
        } else {
            return "";
        }
    }
    public static String getFields(String[] fields)
    {
        if(fields == null) return "";
        StringBuilder sb = new StringBuilder();
        for(String field:fields){
            sb.append('"');
            sb.append(field);
            sb.append('"');
            sb.append(',');
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
    public static String getJsonArrayString(HashMap<String,String> params )
    {

        if(params == null || params.size() == 0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String format = "{\"name\":\"%s\",\"value\":\"%s\"},";
        for(Map.Entry<String,String> entry:params.entrySet())
             sb.append(String.format(format,entry.getKey(),entry.getValue()));
        return "["+sb.toString().substring(0, sb.toString().length() - 1)+"]";
    }
    public static int getChrgesPositionById(ArrayList<Charges> arrayList,String id)
    {
        int position = -1;
        for(int i=0;i < arrayList.size();i++){
            if(arrayList.get(i).getId().equalsIgnoreCase(id)){
                position = i;
                break;
            }
        }
        return position;
    }
    public static int getCountyPositionById(ArrayList<County> arrayList, String id)
    {
        int position = -1;
        for(int i=0;i < arrayList.size();i++){
            if(arrayList.get(i).getId().equalsIgnoreCase(id)){
                position = i;
                break;
            }
        }
        return position;
    }
    public enum RequestType{
        LOGIN,
        LOGOUT,
        GETENTRYLIST,
        SETENTRY,
        SETNOTEATTACHMENT,
        ENTRY,
        GETUSERID
    }
    public enum ModuleName{
        Accounts,
        Cases,
        CH12_charges,
        ct_county,
        Notes,
        km_AttorneyAvailability,
        km_attorneyavailability_users,
        FCM
    }

}
