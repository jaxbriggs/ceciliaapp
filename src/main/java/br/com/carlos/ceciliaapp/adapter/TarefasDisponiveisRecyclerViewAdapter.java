package br.com.carlos.ceciliaapp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.model.Tarefa;

/**
 * Created by Carlos Henrique on 1/11/2017.
 */

public class TarefasDisponiveisRecyclerViewAdapter
        extends RecyclerView.Adapter<TarefasDisponiveisRecyclerViewAdapter.TarefasDisponiveisViewHolder> {

    private Context context;
    private Tarefa[] mDataset;
    private TarefasDisponiveisInnerRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static class TarefasDisponiveisViewHolder extends RecyclerView.ViewHolder {



        public TextView txtGrupo;
        public RecyclerView mRecyclerView;
        public TextView txtAcaoVerTodas;

        public TarefasDisponiveisViewHolder(View v) {
            super(v);
            txtGrupo = (TextView) v.findViewById(R.id.txtGrupo);
            mRecyclerView = (RecyclerView) v.findViewById(R.id.grupos_tarefas_recyclerView);
            txtAcaoVerTodas = (TextView) v.findViewById(R.id.txtVerTodas);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TarefasDisponiveisRecyclerViewAdapter(Context c, Tarefa[] myDataset) {
        mDataset = myDataset;
        this.context = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TarefasDisponiveisViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_tarefa_disponivel, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        TarefasDisponiveisViewHolder vh = new TarefasDisponiveisViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TarefasDisponiveisViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtGrupo.setText("Limpeza");

        holder.mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        holder.mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        adapter = new TarefasDisponiveisInnerRecyclerViewAdapter(mDataset);
        holder.mRecyclerView.setAdapter(adapter);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
