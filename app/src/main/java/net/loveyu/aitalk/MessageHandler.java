package net.loveyu.aitalk;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MessageHandler extends Handler {
    private MainActivity context;
    public final static int Speak_Finish = 1;
    public static Handler handler;

    public MessageHandler(MainActivity context) {
        handler = this;
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Speak_Finish:
                context.openSpeakDialog();
                break;
            default:
                break;
        }
    }
}
