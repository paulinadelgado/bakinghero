package com.paulinadelgado.bakinghero;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TipsFragment extends Fragment {
    public TipsFragment() {
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_tips, container, false);
		
		return rootView;
	}

}
