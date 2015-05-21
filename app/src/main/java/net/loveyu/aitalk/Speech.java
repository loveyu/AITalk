package net.loveyu.aitalk;

import android.content.Context;
import android.media.AudioManager;

import com.baidu.speechsynthesizer.SpeechSynthesizer;

/**
 * Created by loveyu on 2015/5/21.
 * 说话
 */
public class Speech {
    public static void say(Context context, String say) {
        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(context, "holder", new SpeechListener(context));
        new Thread(new Run(speechSynthesizer, say)).start();
    }
}

class Run implements Runnable {
    private SpeechSynthesizer speechSynthesizer;
    private String say;

    Run(SpeechSynthesizer speechSynthesizer, String say) {
        this.speechSynthesizer = speechSynthesizer;
        this.speechSynthesizer.setApiKey(Constants.SPEECH_API_KEY, Constants.SPEECH_SECRET_KEY);
        this.speechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.say = say;
    }

    @Override
    public void run() {
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE, SpeechSynthesizer.AUDIO_ENCODE_AMR);
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE, SpeechSynthesizer.AUDIO_BITRATE_AMR_15K85);

        speechSynthesizer.speak(say);
    }
}