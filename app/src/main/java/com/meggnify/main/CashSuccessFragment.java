package com.meggnify.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meggnify.R;

/**
 * Created by Luqman Hakim on 2/28/2015.
 */
public class CashSuccessFragment extends Fragment {

    TextView TvYay;
    Button BtYay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash_out, container, false);


        TvYay = (TextView) view.findViewById(R.id.TvYay);

        BtYay = (Button) view.findViewById(R.id.BtCashOut);
        BtYay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MeggnifyActivity)getActivity()).ChangeModule(2);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Typeface typeface = Typeface.createFromAsset(getActivity().getResources().getAssets(), "fonts/sketch_block.ttf");
        TvYay.setTypeface(typeface);
    }
}
