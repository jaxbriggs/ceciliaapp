package br.com.carlos.ceciliaapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.adapter.GerenciarTarefasAdapter;
import br.com.carlos.ceciliaapp.model.Tarefa;


/**
 * A simple {@link Fragment} subclass.
 */
public class GerenciarTarefasFragment extends Fragment {

    Tarefa[] tarefas;
    ListView listView;
    private GerenciarTarefasAdapter adapter;

    public GerenciarTarefasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_gerenciar_tarefas, container, false);

        listView=(ListView)v.findViewById(R.id.listViewGerenciarTarefas);

        tarefas= new Tarefa[2];

        tarefas[0] = new Tarefa();
        tarefas[1] = new Tarefa();

        adapter= new GerenciarTarefasAdapter(tarefas,getActivity().getApplicationContext());

        listView.setAdapter(adapter);

        //Configura os clicks

        return v;
    }

}
