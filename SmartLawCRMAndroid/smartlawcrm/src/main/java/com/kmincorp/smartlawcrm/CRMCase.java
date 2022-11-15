package com.kmincorp.smartlawcrm;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CRMCase {
    private String id = null;
    private String nextCourtAppearence;
    private String matterType;
    private String typeOfCase;
    private String charges;
    private String county;

    public String getConsultationStatus() {
        return consultationStatus;
    }

    public void setConsultationStatus(String consultationStatus) {
        this.consultationStatus = consultationStatus;
    }

    private String countyId;
    private String caseName;
    private String consultationStatus;

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    //private String additionalCurrentCharges;
    private String caseSynopsis;
    private String accountId;
    private String consultationNotes;
    private Account account;

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    /*public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getChargesId() {
        return chargesId;
    }

    public void setChargesId(String chargesId) {
        this.chargesId = chargesId;
    }*/

    //private String charges;
    //private String chargesId;

    public CRMCase(String id, String nextCourtAppearence, String matterType, String typeOfCase, String charges, String county,  String caseSynopsis,String accountId,Account account) {
        this.id = id;
        this.nextCourtAppearence = nextCourtAppearence;
        this.matterType = matterType;
        this.typeOfCase = typeOfCase;
        this.charges = charges;
        this.county = county;
        this.caseSynopsis = caseSynopsis;
        this.accountId=accountId;
        this.account = account;
    }
    public CRMCase(){
        this.id = "";
        this.nextCourtAppearence = "";
        this.matterType = "";
        this.typeOfCase = "";
        this.charges = "";
        this.county = "";
        this.caseSynopsis = "";
        this.accountId="";
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getNextCourtAppearence() {
        return nextCourtAppearence;
    }

    public void setNextCourtAppearence(String nextCoutAppearence) {
        this.nextCourtAppearence = nextCoutAppearence;
    }

    public String getMatterType() {
        return matterType;
    }

    public void setMatterType(String matterType) {
        this.matterType = matterType;
    }

    public String getTypeOfCase() {
        return typeOfCase;
    }

    public void setTypeOfCase(String typeOfCase) {
        this.typeOfCase = typeOfCase;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String primaryCharges) {
        this.charges = primaryCharges;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
    public String getAccountId() {
        return accountId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /*public String getAdditionalCurrentCharges() {
        return additionalCurrentCharges;
    }

    public void setAdditionalCurrentCharges(String additionalCurrentCharges) {
        this.additionalCurrentCharges = additionalCurrentCharges;
    }*/

    public String getCaseSynopsis() {
        return caseSynopsis;
    }

    public void setCaseSynopsis(String caseSynopsis) {
        this.caseSynopsis = caseSynopsis;
    }
    public static int count(String jsonString)throws org.json.JSONException {
        JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("entry_list");
        return jsonArray.length();
    }
    public  static ArrayList<CRMCase> jsonToList(String jsonString) throws org.json.JSONException{
        ArrayList<CRMCase> cases =null;
        try{
            JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("entry_list");
            cases = new ArrayList<>();
            JSONObject jsonObject ;
            CRMCase crmCase;
            for(int i=0;i<jsonArray.length();i++){
                crmCase = new CRMCase();
                jsonObject = new JSONObject(jsonArray.getString(i));
                crmCase.setId(jsonObject.getString("id"));
                crmCase.setCaseSynopsis(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("casesynopsis_c")).getString("value"));
                crmCase.setCountyId(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("ct_county_id_c")).getString("value"));
                crmCase.setCounty(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("county1_c")).getString("value"));
                crmCase.setMatterType(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("mattertype_c")).getString("value"));
                crmCase.setNextCourtAppearence(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("courtappearance_c")).getString("value"));
                crmCase.setCharges(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("txt_charges_c")).getString("value"));
                crmCase.setTypeOfCase(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("typeofcase_c")).getString("value"));
                crmCase.setAccountId(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("account_id")).getString("value"));
                crmCase.setConsultationNotes(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("notes_c")).getString("value"));
                crmCase.setCaseName(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("name")).getString("value"));
                crmCase.setConsultationStatus(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("consultationstatus_c")).getString("value"));
                cases.add(crmCase);
            }
        }
        catch (org.json.JSONException ex) {
            throw ex;
        }
        return  cases;
    }
}
