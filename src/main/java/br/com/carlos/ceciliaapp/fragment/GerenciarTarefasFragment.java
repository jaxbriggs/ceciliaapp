package br.com.carlos.ceciliaapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.carlos.ceciliaapp.Application;
import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.activity.TarefasActivity;
import br.com.carlos.ceciliaapp.adapter.GerenciarTarefasAdapter;
import br.com.carlos.ceciliaapp.model.Tarefa;


/**
 * A simple {@link Fragment} subclass.
 */
public class GerenciarTarefasFragment extends Fragment {

    ListView listView;
    private GerenciarTarefasAdapter adapter;

    public GerenciarTarefasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((TarefasActivity)getActivity()).setFragmentRefreshListener(new TarefasActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                updateGerenciarTarefasFragmentListView();
            }
        });

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_gerenciar_tarefas, container, false);

        listView=(ListView)v.findViewById(R.id.listViewGerenciarTarefas);

        List<Tarefa> tars = new ArrayList<>();
        tars.addAll(((Application)getActivity().getApplicationContext()).tarefasGerenciaveis);

        adapter =
                new GerenciarTarefasAdapter(tars, getActivity().getApplicationContext());

        listView.setAdapter(adapter);

        //Configura os clicks

        return v;
    }

    public void updateGerenciarTarefasFragmentListView(){
        if(adapter != null){
            adapter.updateDataset(
                    ((Application)getActivity().getApplicationContext()).tarefasGerenciaveis
            );
        }
    }

}
