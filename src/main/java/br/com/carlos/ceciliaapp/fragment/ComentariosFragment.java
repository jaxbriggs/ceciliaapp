package br.com.carlos.ceciliaapp.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.adapter.ComentariosAdapter;
import br.com.carlos.ceciliaapp.model.Comentario;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComentariosFragment extends Fragment {

    Comentario[] comentarios;
    ListView listView;
    private ComentariosAdapter adapter;
    private FloatingActionButton fabAddComentario;

    public ComentariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comentarios, container, false);

        getViews(v);

        listView=(ListView)v.findViewById(R.id.listViewComentarios);

        comentarios= new Comentario[5];

        comentarios[0] = new Comentario();
        comentarios[1] = new Comentario();
        comentarios[2] = new Comentario();
        comentarios[3] = new Comentario();
        comentarios[4] = new Comentario();

        adapter= new ComentariosAdapter(comentarios,getActivity().getApplicationContext());

        listView.setAdapter(adapter);

        //Configura os clicks

        return v;
    }

    private void getViews(View v){
        this.fabAddComentario = (FloatingActionButton) v.findViewById(R.id.fabAddComentario);
    }

}
