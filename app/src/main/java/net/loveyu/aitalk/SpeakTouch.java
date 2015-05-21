package net.loveyu.aitalk;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class SpeakTouch implements View.OnTouchListener {
    private MainActivity context;
    private MediaRecorder recorder;
    private String tmp_file;
    private boolean start = false;

    public SpeakTouch(MainActivity context) {
        this.context = context;
        tmp_file = context.getCacheDir() + "/tmp.amr";
        Log.d("test", tmp_file);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            File file = new File(tmp_file);
            if (file.exists()) {
                if (!file.delete()) {
                    Toast.makeText(context, "Temp file delete error.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(file.getPath());
            try {
                recorder.prepare();
                recorder.start();
                start = true;
            } catch (IOException e) {
                Toast.makeText(context, "录音失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Toast.makeText(context, "DOWN", Toast.LENGTH_SHORT).show();
        } else if (action == MotionEvent.ACTION_UP) {
            Toast.makeText(context, "UP", Toast.LENGTH_SHORT).show();
            if (start) {
                recorder.stop();
                recorder.reset();
                recorder.release();
                //TODO send on network
            }
        }
        return false;
    }
}
