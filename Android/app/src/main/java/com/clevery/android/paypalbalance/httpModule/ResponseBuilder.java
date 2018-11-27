package com.clevery.android.paypalbalance.httpModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseBuilder {
    public JSONObject jsonObject;
    private String response;

    public ResponseBuilder(String response) {
        try {
            this.response = response;
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            jsonObject = null;
        }
    }

    public String getString(String name) {
        try {
            if (jsonObject == null || jsonObject.isNull(name))
                return "";

            return jsonObject.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public JSONObject getObject(String name) {
        try {
            if (jsonObject == null || jsonObject.isNull(name))
                return null;

            return jsonObject.getJSONObject(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getArray(String name) {
        try {
            if (jsonObject.isNull(name))
                return null;

            return jsonObject.getJSONArray(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getString(String group, String name) {
        try {
            JSONObject jsonObject = getObject(group);
            if (jsonObject == null)
                return "";

             return jsonObject.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getString(String group, String subgroup, String name) {
        try {
            JSONObject jsonObject = getObject(group);
            if (jsonObject == null)
                return "";

            jsonObject = jsonObject.getJSONObject(subgroup);
            if (jsonObject == null)
                return "";

            return jsonObject.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public int getArraySize(String array_name) {
        JSONArray jsonArray = getArray(array_name);
        if (jsonArray == null)
            return 0;

        return jsonArray.length();
    }

    public String getStringAtIndex(String array_name, String name, int index) {
        JSONArray jsonArray = getArray(array_name);
        if (jsonArray == null)
            return "";

        try {
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            if (jsonObject == null || jsonObject.isNull(name))
                return "";

            return jsonObject.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getResponse() {
        return response;
    }
}
