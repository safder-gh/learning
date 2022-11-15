package com.example.hammadhanif.availabilityapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hammadhanif.availabilityapplication.Utilities.BaseActivity;
import com.example.hammadhanif.availabilityapplication.Utilities.Common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.hammadhanif.availabilityapplication.Utilities.Common.hasPermissions;

public class VoiceRecorderActivity extends BaseActivity {

    private static final int RECORD_REQUEST_CODE = 101;
    private boolean isRecording = false;
    private boolean isPlaying = false;
    private String audioFilePath;
    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;
    ImageButton btnRecording,btnPlay,btnSave;
private final String FILE_NAME="myaudio";
    private ProgressBar mProgressBar;

    private Chronometer chronometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recorder);
        super.setSubTitle("Voice Recorder");


        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.INTERNET
        };
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        btnRecording = findViewById(R.id.btnRecording);
        btnPlay = findViewById(R.id.btnPlay);
        btnSave = findViewById(R.id.btnSave);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnRecording.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                if(!isRecording) {
                    btnPlay.setEnabled(false);
                    btnSave.setEnabled(false);
                    chronometer.start();
                    mProgressBar.setVisibility(View.VISIBLE);
                    btnRecording.setImageDrawable(getDrawable( R.drawable.ic_mic_red_48dp));
                    isRecording = true;
                    startRecording(v);
                }
                else
                {
                    btnPlay.setEnabled(true);
                    btnSave.setEnabled(true);
                    chronometer.stop();
                    mProgressBar.setVisibility(View.INVISIBLE);
                    btnRecording.setImageDrawable(getDrawable( R.drawable.ic_mic_green_48dp));
                    isRecording = false;
                    stopRecording(v);
                }
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                File file = new File(String.format(audioFilePath,FILE_NAME));
                if(!file.exists()){
                    Toast.makeText(VoiceRecorderActivity.this,"Save an audio file first.",Toast.LENGTH_LONG).show();
                    return;
                }
                chronometer.setBase(SystemClock.elapsedRealtime());
                if(!isPlaying)
                {
                    btnRecording.setEnabled(false);
                    btnSave.setEnabled(false);
                    btnPlay.setImageDrawable(getDrawable( R.drawable.ic_stop_red_48dp));
                    mProgressBar.setVisibility(View.VISIBLE);
                    isPlaying = true;
                    chronometer.start();
                    try
                    {
                    playAudio(v);
                    }
                    catch (IOException ex)
                    {
                        Toast.makeText( VoiceRecorderActivity.this,ex.getMessage(),Toast.LENGTH_LONG);
                    }
                }
                else
                {
                    btnRecording.setEnabled(true);
                    btnSave.setEnabled(true);
                    btnPlay.setImageDrawable(getDrawable( R.drawable.ic_play_arrow_black_24dp));
                    mProgressBar.setVisibility(View.GONE);
                    isPlaying = false;
                    chronometer.stop();
                    stopAudio(v);
                }

            }
        });
        btnSave.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.save_file, null);
                final EditText nameEditText =  (EditText) view.findViewById(R.id.etSaveFile);
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(VoiceRecorderActivity.this);
                inputDialog.setView(view);
                inputDialog.setTitle("Save Audio");
                inputDialog.setPositiveButton("Save Audio", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameEditText.getText().toString().trim();
                        if (name.length() != 0){
                            File tempFile = new File(String.format(audioFilePath,FILE_NAME));
                            File newFile = new File(String.format(audioFilePath,name));
                            tempFile.renameTo(newFile);
                        } else {
                            Toast message = Toast.makeText(VoiceRecorderActivity.this, "Provide a file name", Toast.LENGTH_SHORT);
                            message.setGravity(Gravity.CENTER, message.getXOffset() / 2, message.getYOffset() / 2);
                            message.show(); // display the Toast
                        }
                    }
                });
                inputDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });
                inputDialog.show();
            }
        });
        if(!hasMicrophone())
        {
            super.setSubTitle("Microphone not available.");
            btnRecording.setEnabled(false);
        }
        audioFilePath =  this.getPath();
    }
    public void startRecording (View view)
    {
        try {
            //File dir = new File(String.format(audioFilePath,FILE_NAME));
            //if(!dir.exists())
                //dir.mkdirs();
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            mediaRecorder.setOutputFormat(
                    MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setOutputFile(String.format(audioFilePath,FILE_NAME));
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setAudioChannels(1);
            mediaRecorder.setAudioSamplingRate(44100);
            mediaRecorder.setAudioEncodingBitRate(96000);
            mediaRecorder.prepare();

        } catch (FileNotFoundException e){
            Toast message = Toast.makeText(VoiceRecorderActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
            message.setGravity(Gravity.CENTER, message.getXOffset() / 2, message.getYOffset() / 2);
            message.show();
            return;
        }
        catch (Exception e) {
            Toast message = Toast.makeText(VoiceRecorderActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
            message.setGravity(Gravity.CENTER, message.getXOffset() / 2, message.getYOffset() / 2);
            message.show();
            return;
        }

        mediaRecorder.start();
    }
    public void stopRecording (View view)
    {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }
    public void playAudio (View view) throws IOException
    {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(String.format(audioFilePath,"myaudio"));
        mediaPlayer.prepare();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                btnRecording.setEnabled(true);
                btnSave.setEnabled(true);
                btnPlay.setImageDrawable(getDrawable( R.drawable.ic_play_arrow_black_24dp));
                mProgressBar.setVisibility(View.GONE);
                isPlaying = false;
                chronometer.stop();
            }

        });
        mediaPlayer.start();
    }
    public void stopAudio (View view)
    {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
    @Override
    protected void refresh() {

    }
    protected boolean hasMicrophone() {
        PackageManager pmanager = this.getPackageManager();
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }
    /*private void setupPermissions() {
        int permissionRecordAudio = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        int permissionWriteExternalStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (permissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale( this,Manifest.permission.RECORD_AUDIO))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Permission to access the microphone is required for this app to record audio.");
                builder.setTitle("Permission required");
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                makeRequest(Manifest.permission.RECORD_AUDIO);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                makeRequest(Manifest.permission.RECORD_AUDIO);
            }
        }
        if (permissionWriteExternalStorage != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale( this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Permission to write external storage .");
                builder.setTitle("Permission required");
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                makeRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                makeRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
    }
  private void makeRequest(String permission) {
        ActivityCompat.requestPermissions(this,
                new String[]{permission},
                RECORD_REQUEST_CODE);
    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RECORD_REQUEST_CODE: {
                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this,"Permission has been denied by user",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this,"Permission has been granted by user",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    private String getPath(){
        //return Environment.getDataDirectory()+File.separator+ "%s.mp3";
        return Common.filePath(this).getAbsolutePath()+File.separator+ "%s.mp3";
    }
}
