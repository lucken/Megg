package com.meggnify.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.meggnify.R;
import com.meggnify.helper.BaseFragment;
import com.meggnify.helper.so;

/**
 * Created by Luqman Hakim on 3/2/2015.
 */
public class MissionFragment extends BaseFragment {
    private TextView TvAssignmentTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mission, container, false);
        TvAssignmentTitle = (TextView) view.findViewById(R.id.TvAssignmentTitle);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        TvAssignmentTitle.setText("Start Mission - " + so.currentAssignment.getAssignment_type() );
        selectItem(0);
    }

    void selectItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new MissionChecklistFragment();
                TvAssignmentTitle.setText("Start Mission - " + so.currentAssignment.getAssignment_type() + " - Checklist");
                break;
            case 1:
                fragment = new AssignmentMapFragment();
                TvAssignmentTitle.setText("Map");
                break;
            default:
                fragment = new AssignmentMyFragment();
                TvAssignmentTitle.setText("My Jobs");
                break;
        }


        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.mission_container, fragment)
                .commit();
    }
}
