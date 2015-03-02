package com.meggnify.helper;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.raaf.rDialog;

/**
 * Created by Luqman Hakim on 3/1/2015.
 */
public class BaseFragment extends Fragment {

    protected final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            onHandlerResponse(msg);
        }
    };

    protected void onHandlerResponse(Message msg) {
        API.ApiParser(msg);
        if (so.result.getStatus() == 401)
            ((BaseActivity) getActivity()).doLogout();
        if (so.result.getStatus() == 200)
            return;
        else
            rDialog.SetToast(getActivity(), so.result.getStatus() + " : " + so.result.getInfo());

    }
}