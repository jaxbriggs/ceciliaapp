package br.com.carlos.ceciliaapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.adapter.TarefasDisponiveisRecyclerViewAdapter;
import br.com.carlos.ceciliaapp.model.Tarefa;


/**
 * A simple {@link Fragment} subclass.
 */
public class TarefasFragment extends Fragment {

    private Context mContext;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public TarefasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tarefas, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.grupos_tarefas_recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Tarefa[] myDataset = new Tarefa[2];
        myDataset[0] = new Tarefa();
        myDataset[1] = new Tarefa();


        // specify an adapter (see also next example)
        mAdapter = new TarefasDisponiveisRecyclerViewAdapter(getActivity().getApplicationContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void getViews(){

    }

}
