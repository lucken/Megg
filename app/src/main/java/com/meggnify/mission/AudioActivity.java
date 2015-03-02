package com.meggnify.mission;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.meggnify.R;
import com.meggnify.model.Mission;
import com.meggnify.model.Task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioActivity extends ActionBarActivity {

    private static final int CAPTURE_AUDIO_ACTIVITY_REQUEST_CODE = 300;

    public static final int MEDIA_TYPE_AUDIO = 3;

    private String mUserToken;
    private Mission mission;
    private Task task;

    private Uri fileUri;

    private TextView questionView;
    private VideoView audioView;
    private Button takeAudioButton;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        Bundle extras = getIntent().getExtras();
        mUserToken = extras.getString("token");
        mission = extras.getParcelable("mission");
        task = extras.getParcelable("task");

        setTitle(mission.name);

        questionView = (TextView) findViewById(R.id.task_question);
        questionView.setText(task.question);

        audioView = (VideoView) findViewById(R.id.task_audio);

        takeAudioButton = (Button) findViewById(R.id.take_audio_button);
        takeAudioButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_AUDIO); // create a file to save the audio
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_AUDIO_ACTIVITY_REQUEST_CODE);
            }
        });

        doneButton = (Button) findViewById(R.id.done_button);
        doneButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }

        });
    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Meggnify");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("Meggnify", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "AUD_"+ timeStamp + ".mp3");

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_AUDIO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Audio captured and saved to fileUri specified in the Intent
                audioView.setVideoURI(fileUri);
                audioView.setMediaController(new MediaController(this));
                audioView.requestFocus();
                audioView.start();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.audio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
