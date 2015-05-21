package net.loveyu.aitalk;

import android.content.Context;
import android.widget.Toast;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

public class Robots {
    private Context context;
    private RobotsResult call;

    public Robots(Context context, RobotsResult call) {
        this.context = context;
        this.call = call;
    }

    public void talk(String msg) {
        if (msg.length() > 1 && msg.charAt(msg.length() - 1) == '。') {
            msg = msg.substring(0, msg.length() - 1);
        }
        FinalHttp http = new FinalHttp();
        AjaxParams params = new AjaxParams();
        params.put("key", Constants.TULING_KEY);
        params.put("info", msg);
        params.put("userid", DeviceId.getId(context));
        params.put("loc", "湖北荆州长江大学东校区");
        http.get("http://www.tuling123.com/openapi/api", params, new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                ParseTuling tuling = new ParseTuling((String) o);
                call.result(tuling.getString());
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                call.result("网络请求出错");
            }
        });
    }


}
