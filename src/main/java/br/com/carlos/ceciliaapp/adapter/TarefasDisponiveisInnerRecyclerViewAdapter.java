package br.com.carlos.ceciliaapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.model.Tarefa;

/**
 * Created by Carlos Henrique on 1/11/2017.
 */

public class TarefasDisponiveisInnerRecyclerViewAdapter
        extends RecyclerView.Adapter<TarefasDisponiveisInnerRecyclerViewAdapter.TarefasDisponiveisDetalheViewHolder> {
    private Tarefa[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class TarefasDisponiveisDetalheViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //[TODO:Itens da tela]
        public TarefasDisponiveisDetalheViewHolder(View v) {
            super(v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TarefasDisponiveisInnerRecyclerViewAdapter(Tarefa[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TarefasDisponiveisDetalheViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_tarefa_disponivel_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        TarefasDisponiveisDetalheViewHolder vh = new TarefasDisponiveisDetalheViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TarefasDisponiveisDetalheViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
