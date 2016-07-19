package com.paulinadelgado.bakinghero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class DialogFragment extends android.app.DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info_dialog, container, true);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);




        return rootView;
    }


}
