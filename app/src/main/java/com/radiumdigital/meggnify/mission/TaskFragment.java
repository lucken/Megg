package com.radiumdigital.meggnify.mission;

import com.radiumdigital.meggnify.R;
import com.radiumdigital.meggnify.R.id;
import com.radiumdigital.meggnify.R.layout;
import com.radiumdigital.meggnify.model.Task;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TaskFragment extends Fragment {

	private Task task;
	
	private TextView questionView;
	private Button doneButton;
	
	public TaskFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_task, container, false);
		
		Bundle extras = getArguments();
		task = extras.getParcelable("task");
		
		questionView = (TextView) view.findViewById(R.id.task_question);
		questionView.setText(task.question);
		
		doneButton = (Button) view.findViewById(R.id.done_button);
		doneButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		return view;
	}

}
