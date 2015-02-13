package com.xiayule.getll.utils;


import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tan on 14-7-20.
 */
public class JsonUtils {
    public static Map<String, String> jsonToHash(JSONObject jsonObject) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();

        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            String value = jsonObject.getString(key);

            map.put(key, value);
        }

        return map;
    }

    public static Map<String, String> jsonToHash(String json) {
        JSONObject jsonObject = stringToJson(json);
        return jsonToHash(jsonObject);
    }

    public static JSONObject stringToJson(String content) throws JSONException {
        JSONObject jsonObject = JSONObject.fromObject(content);
        return jsonObject;
    }
}
