package br.com.carlos.ceciliaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.model.Tarefa;

/**
 * Created by Carlos Henrique on 1/8/2017.
 */
public class GerenciarTarefasAdapter extends ArrayAdapter<Tarefa> implements View.OnClickListener {

    private Tarefa[] dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        /*
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
        */
    }

    public GerenciarTarefasAdapter(Tarefa[] data, Context context) {
        super(context, R.layout.row_gerenciar_tarefas_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Tarefa dataModel=(Tarefa) object;

        switch (v.getId())
        {
            /*
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;
            */
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Tarefa dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_gerenciar_tarefas_item, parent, false);
            /*
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);
            */

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        /*
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        */

        /*
        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtType.setText(dataModel.getType());
        viewHolder.txtVersion.setText(dataModel.getVersion_number());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        */
        // Return the completed view to render on screen
        return convertView;
    }
}
