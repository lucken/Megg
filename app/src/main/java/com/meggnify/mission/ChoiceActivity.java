package com.meggnify.mission;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.meggnify.R;
import com.meggnify.model.Mission;
import com.meggnify.model.Task;

public class ChoiceActivity extends ActionBarActivity {


    private String mUserToken;
    private Mission mission;
    private Task task;

    private TextView questionView;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        Bundle extras = getIntent().getExtras();
        mUserToken = extras.getString("token");
        mission = extras.getParcelable("mission");
        task = extras.getParcelable("task");

        setTitle(mission.name);

        questionView = (TextView) findViewById(R.id.task_question);
        questionView.setText(task.question);

        doneButton = (Button) findViewById(R.id.done_button);
        doneButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }

        });
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choice, menu);
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
