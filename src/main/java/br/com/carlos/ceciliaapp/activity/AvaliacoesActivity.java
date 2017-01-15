package br.com.carlos.ceciliaapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.carlos.ceciliaapp.Application;
import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.adapter.AvaliacoesAdapter;
import br.com.carlos.ceciliaapp.model.Avaliacao;

public class AvaliacoesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ArrayList<Avaliacao> avaliacoes;
    ListView listView;
    private AvaliacoesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Avaliações");

        listView=(ListView)findViewById(R.id.listViewAvaliacoes);

        avaliacoes= new ArrayList<>();

        avaliacoes.add(new Avaliacao());
        avaliacoes.add(new Avaliacao());
        avaliacoes.add(new Avaliacao());
        avaliacoes.add(new Avaliacao());
        avaliacoes.add(new Avaliacao());
        avaliacoes.add(new Avaliacao());
        avaliacoes.add(new Avaliacao());
        avaliacoes.add(new Avaliacao());
        avaliacoes.add(new Avaliacao());


        /*
        dataModels.add(new DataModel("Apple Pie", "Android 1.0", "1","September 23, 2008"));
        dataModels.add(new DataModel("Banana Bread", "Android 1.1", "2","February 9, 2009"));
        dataModels.add(new DataModel("Cupcake", "Android 1.5", "3","April 27, 2009"));
        dataModels.add(new DataModel("Donut","Android 1.6","4","September 15, 2009"));
        dataModels.add(new DataModel("Eclair", "Android 2.0", "5","October 26, 2009"));
        dataModels.add(new DataModel("Froyo", "Android 2.2", "8","May 20, 2010"));
        dataModels.add(new DataModel("Gingerbread", "Android 2.3", "9","December 6, 2010"));
        dataModels.add(new DataModel("Honeycomb","Android 3.0","11","February 22, 2011"));
        dataModels.add(new DataModel("Ice Cream Sandwich", "Android 4.0", "14","October 18, 2011"));
        dataModels.add(new DataModel("Jelly Bean", "Android 4.2", "16","July 9, 2012"));
        dataModels.add(new DataModel("Kitkat", "Android 4.4", "19","October 31, 2013"));
        dataModels.add(new DataModel("Lollipop","Android 5.0","21","November 12, 2014"));
        dataModels.add(new DataModel("Marshmallow", "Android 6.0", "23","October 5, 2015"));
        */

        adapter= new AvaliacoesAdapter(avaliacoes,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Avaliacao dataModel= avaliacoes.get(position);

                startActivity(new Intent(AvaliacoesActivity.this, AvaliacoesDetalhesActivity.class));

                /*
                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                */
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tarefas, menu);

        invalidateOptionsMenu();
        MenuItem item = menu.findItem(R.id.action_avaliacoes);
        item.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_tarefas) {
            startActivity(new Intent(AvaliacoesActivity.this, TarefasActivity.class));
            return true;
        } else if(id == R.id.action_grupos) {
            return true;
        } else if(id == R.id.action_sair){
            showSairDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSairDialog(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Application.logoff();
                        Intent intent  = new Intent(AvaliacoesActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(AvaliacoesActivity.this);
        builder.setMessage("Voce deseja realmente sair?").setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener).show();
    }

}
