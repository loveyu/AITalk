package net.loveyu.aitalk;

import android.content.Context;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;

public class SpeechListener implements SpeechSynthesizerListener {
    private Context context;

    public SpeechListener(Context context) {
        this.context = context;
    }

    @Override
    public void onStartWorking(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSpeechStart(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onNewDataArrive(SpeechSynthesizer speechSynthesizer, byte[] bytes, boolean b) {

    }

    @Override
    public void onBufferProgressChanged(SpeechSynthesizer speechSynthesizer, int i) {

    }

    @Override
    public void onSpeechProgressChanged(SpeechSynthesizer speechSynthesizer, int i) {

    }

    @Override
    public void onSpeechPause(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSpeechResume(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onCancel(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSynthesizeFinish(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSpeechFinish(SpeechSynthesizer speechSynthesizer) {
    }

    @Override
    public void onError(SpeechSynthesizer speechSynthesizer, SpeechError speechError) {

    }
}
