package br.com.carlos.ceciliaapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.adapter.MinhasTarefasAdapter;
import br.com.carlos.ceciliaapp.model.Tarefa;


/**
 * A simple {@link Fragment} subclass.
 */
public class MinhasTarefasFragment extends Fragment {

    Tarefa[] tarefas;
    ListView listView;
    private MinhasTarefasAdapter adapter;


    public MinhasTarefasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_minhas_tarefas, container, false);

        listView=(ListView)v.findViewById(R.id.listViewMinhasTarefas);

        tarefas= new Tarefa[5];

        tarefas[0] = new Tarefa();
        tarefas[1] = new Tarefa();
        tarefas[2] = new Tarefa();
        tarefas[3] = new Tarefa();
        tarefas[4] = new Tarefa();

        adapter= new MinhasTarefasAdapter(tarefas,getActivity().getApplicationContext());

        listView.setAdapter(adapter);

        //Configura os clicks

        // Inflate the layout for this fragment
        return v;
    }

}
