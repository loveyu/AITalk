package net.loveyu.aitalk;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MessageHandler extends Handler {
    private MainActivity context;

    public MessageHandler(MainActivity context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Toast.makeText(context, "Message received.", Toast.LENGTH_SHORT).show();
    }
}
