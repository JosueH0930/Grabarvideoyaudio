package com.example.grabarvideoyaudio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import android.widget.Toast;

import java.io.IOException;

public class grab_audio extends AppCompatActivity {

    private MediaRecorder miGrabacion;
    private String outputFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_audio);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(grab_audio.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }


    public void grabar(View view) {
        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/Grabacion.3gp";
        miGrabacion = new MediaRecorder();
        miGrabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
        miGrabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        miGrabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);//Codificador de audio
        miGrabacion.setOutputFile(outputFile);
        try {
            miGrabacion.prepare();
            miGrabacion.start();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "La grabación comenzó", Toast.LENGTH_LONG).show();
    }
    public void detener(View view) {
        if (miGrabacion != null) {
            miGrabacion.stop();
            miGrabacion.release();
            miGrabacion = null;
            Toast.makeText(getApplicationContext(), "El audio  grabado con éxito", Toast.LENGTH_LONG).show();
        }
    }
    public void reproducir(View view) {
        MediaPlayer m = new MediaPlayer();
        try {
            m.setDataSource(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            m.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        m.start();
        Toast.makeText(getApplicationContext(), "reproducción de audio", Toast.LENGTH_LONG).show();
    }
}
