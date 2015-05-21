package net.loveyu.aitalk;

import android.content.Context;
import android.media.AudioManager;
import android.widget.Toast;

import com.baidu.speechsynthesizer.SpeechSynthesizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by loveyu on 2015/5/21.
 * 说话
 */
public class Speech {
    SpeechSynthesizer speechSynthesizer;

    private List<Thread> threadList;

    public Speech(Context context) {
        threadList = new ArrayList<>();
        speechSynthesizer = new SpeechSynthesizer(context, "holder", new SpeechListener(context));
        speechSynthesizer.setApiKey(Constants.SPEECH_API_KEY, Constants.SPEECH_SECRET_KEY);
        speechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE, SpeechSynthesizer.AUDIO_ENCODE_AMR);
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE, SpeechSynthesizer.AUDIO_BITRATE_AMR_15K85);
    }

    public void say(String say) {
        emptyCheck();
        Thread thread = new Thread(new Run(speechSynthesizer, say));
        threadList.add(thread);
        thread.start();
    }

    public void destroy() {
        speechSynthesizer.cancel();
        Iterator<Thread> iterator = threadList.iterator();
        while (iterator.hasNext()) {
            Thread thread = iterator.next();
            if (thread.isAlive()) {
                thread.interrupt();
            }
            iterator.remove();
        }
    }

    private void emptyCheck() {
        Iterator<Thread> iterator = threadList.iterator();
        while (iterator.hasNext()) {
            Thread thread = iterator.next();
            if (!thread.isAlive()) {
                iterator.remove();
            }
        }
    }
}

class Run implements Runnable {
    private SpeechSynthesizer speechSynthesizer;
    private String say;

    Run(SpeechSynthesizer speechSynthesizer, String say) {
        this.speechSynthesizer = speechSynthesizer;
        this.say = say;
    }

    @Override
    public void run() {
        speechSynthesizer.speak(say);
    }
}