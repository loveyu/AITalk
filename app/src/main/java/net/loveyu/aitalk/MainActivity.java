package net.loveyu.aitalk;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    public Handler handler;
    public Button button;
    public ListView listView;

    private TalkAdapter talkAdapter;

    private static final String TAG = MainActivity.class.getSimpleName();

    private BaiduASRDigitalDialog mDialog = null;

    private DialogRecognitionListener mRecognitionListener;

    private int mCurrentTheme = Config.DIALOG_THEME;

    private Robots robots;

    private int speakStatus = R.string.stop_speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.speak);
        button.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView);
        handler = new MessageHandler(this);
        talkAdapter = new TalkAdapter(this);
        Talk.initList();
        listView.setAdapter(talkAdapter);
        robots = new Robots(this, new RobotsResult() {
            @Override
            public void result(String msg) {
                setRobotsResult(msg);
            }
        });
        mRecognitionListener = new DialogRecognitionListener() {

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> rs = results != null ? results
                        .getStringArrayList(RESULTS_RECOGNITION) : null;
                if (rs != null && rs.size() > 0) {
                    setVoiceResult(rs.get(0));
                }
            }
        };
    }

    public void setVoiceResult(String msg) {
        Talk.add(msg, Talk.ME);
        robots.talk(msg);
        setListViewChange();
    }

    public void setRobotsResult(String msg) {
        Talk.add(msg, Talk.Robots);
        if (speakStatus == R.string.stop_speak) {
            Speech.say(this, msg);
        }
        setListViewChange();
    }

    private void setListViewChange() {
        talkAdapter.notifyDataSetChanged();
        listView.setSelection(Talk.selfList.size() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem search = menu.findItem(R.id.action_speak_status);
        search.setTitle(speakStatus);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_exit:
                finish();
                return true;
            case R.id.action_speak_status:
                if (speakStatus == R.string.start_speak) {
                    speakStatus = R.string.stop_speak;
                } else {
                    speakStatus = R.string.start_speak;
                }
                getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speak:
                //if (mDialog == null || mCurrentTheme != Config.DIALOG_THEME) {
                mCurrentTheme = Config.DIALOG_THEME;
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                Bundle params = new Bundle();
                params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, Constants.API_KEY);
                params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, Constants.SECRET_KEY);
                params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, Config.DIALOG_THEME);
                mDialog = new BaiduASRDigitalDialog(this, params);
                mDialog.setDialogRecognitionListener(mRecognitionListener);
                //}
                mDialog.getParams().putInt(BaiduASRDigitalDialog.PARAM_PROP, Config.CURRENT_PROP);
                mDialog.getParams().putString(BaiduASRDigitalDialog.PARAM_LANGUAGE,
                        Config.getCurrentLanguage());
                Log.e("DEBUG", "Config.PLAY_START_SOUND = " + Config.PLAY_START_SOUND);
                mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_START_TONE_ENABLE, Config.PLAY_START_SOUND);
                mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_END_TONE_ENABLE, Config.PLAY_END_SOUND);
                mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_TIPS_TONE_ENABLE, Config.DIALOG_TIPS_SOUND);
                mDialog.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        super.onDestroy();
    }
}
