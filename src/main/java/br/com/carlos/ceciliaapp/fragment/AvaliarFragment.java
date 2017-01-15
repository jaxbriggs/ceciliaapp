package br.com.carlos.ceciliaapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.carlos.ceciliaapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AvaliarFragment extends Fragment {


    public AvaliarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avaliar, container, false);
    }

}
