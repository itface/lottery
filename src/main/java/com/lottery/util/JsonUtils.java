package com.lottery.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonUtils {

	public static JSONObject objectToJSONObject(Object obj,String[] excludes){
		if (obj != null) {
			JsonConfig cfg = new JsonConfig();
			cfg.registerJsonValueProcessor(Date.class,
					new JsonValueProcessor() {
						private final String format = "yyyy-MM-dd HH:mm:ss";
						public Object processObjectValue(String key,Object value, JsonConfig arg2) {
							if (value == null)
								return "";
							if (value instanceof Date) {
								String str = new SimpleDateFormat(format).format((Date) value);
								return str;
							}
							return value;
						}
						public Object processArrayValue(Object value,JsonConfig arg1) {
							return value;
						}
					}
			);
			cfg.registerJsonValueProcessor(Object.class,
					new JsonValueProcessor() {
						public Object processObjectValue(String key,Object value, JsonConfig arg2) {
							if (value == null)
								return "";
							return value;
						}
						public Object processArrayValue(Object value,JsonConfig arg1) {
							return value;
						}
					}
			);
			if(excludes!=null&&excludes.length>0){
				cfg.setExcludes(excludes);
			}
			JSONObject jsonObject = JSONObject.fromObject(obj,cfg);
			return jsonObject;
			/*
			if (jsonObject != null) {				
				String jsonString = jsonObject.toString().replaceAll("null","\"\"");
				jsonObject = JSONObject.fromObject(jsonString);
				return jsonObject;
			}*/
		}
		return null;
	}
}
