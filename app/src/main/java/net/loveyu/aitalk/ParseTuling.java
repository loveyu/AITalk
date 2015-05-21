package net.loveyu.aitalk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by loveyu on 2015/5/21.
 * 解析图灵数据
 */
public class ParseTuling {
    private String json;
    private String result = "我不知道啊";

    public ParseTuling(String json) {
        this.json = json;
        doAction();
    }

    private void doAction() {
        JSONTokener jsonTokener = new JSONTokener(json);
        try {
            JSONObject person = (JSONObject) jsonTokener.nextValue();
            int code = person.getInt("code");
            switch (code) {
                case 100000:
                    result = person.getString("text");
                    break;
                case 305000:
                    result = parseTrain(person.getJSONArray("list"), person.getString("text"));
                    break;
                case 306000:
                    result = parseFlight(person.getJSONArray("list"), person.getString("text"));
                    break;
                case 200000:
                    result = person.getString("text") + "，打开" + person.getString("url") + "查看细节";
                    break;
                case 302000:
                    result = parseNews(person.getJSONArray("list"), person.getString("text"));
                    break;
                case 308000:
                    result = parseCook(person.getJSONArray("list"), person.getString("text"));
                    break;
                case 40001:
                    result = "key的长度错误（32位）";
                    break;
                case 40002:
                    result = "请求内容为空";
                    break;
                case 40003:
                    result = "key错误或帐号未激活";
                    break;
                case 40004:
                    result = "当天请求次数已用完";
                    break;
                case 40005:
                    result = "暂不支持该功能";
                    break;
                case 40006:
                    result = "服务器升级中";
                    break;
                case 40007:
                    result = "服务器数据格式异常";
                    break;
            }
        } catch (JSONException e) {
            result = "出错了哦。";
        }
    }

    private String parseTrain(JSONArray json, String text) throws JSONException {
        text += "，找到" + json.length() + "趟列车，";
        for (int i = 0; i < json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            text += "第" + (i + 1) + "趟为" + object.getString("trainnum") + ", 起点为" + object.getString("start") + "，";
            text += "终点为" + object.getString("terminal") + ",时间为" + object.getString("starttime") + "到" + object.getString("endtime") + "。";
        }
        return text;
    }

    private String parseFlight(JSONArray json, String text) throws JSONException {
        text += "，找到" + json.length() + "趟航班，";
        for (int i = 0; i < json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            text += "第" + (i + 1) + "趟为" + object.getString("flight") + ", 线路为" + object.getString("route") + "，";
            text += "起飞时间" + object.getString("starttime") + ",到达时间" + object.getString("endtime") + "，状态" + object.getString("state") + "。";
        }
        return text;
    }

    private String parseNews(JSONArray json, String text) throws JSONException {
        text += "，找到" + json.length() + "条相关新闻，";
        for (int i = 0; i < json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            text += "第" + (i + 1) + "条为" + object.getString("article") + "，";
        }
        return text;
    }

    private String parseCook(JSONArray json, String text) throws JSONException {
        text += "，找到" + json.length() + "份菜谱，";
        for (int i = 0; i < json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            text += "第" + (i + 1) + "条为" + object.getString("name") + "，" + object.getString("info") + "，";
        }
        return text;
    }

    public String getString() {
        return result;
    }
}
