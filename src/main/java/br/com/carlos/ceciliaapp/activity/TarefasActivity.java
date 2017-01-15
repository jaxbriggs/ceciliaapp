package br.com.carlos.ceciliaapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.carlos.ceciliaapp.Application;
import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.adapter.TarefasViewPagerAdapter;
import br.com.carlos.ceciliaapp.slidingtabs.SlidingTabLayout;

public class TarefasActivity extends AppCompatActivity {

    ViewPager pager;
    TarefasViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"DISPONÍVEIS","MINHAS","GERENCIAR"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Tarefas");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddTarefa);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TarefasActivity.this, NovaTarefaActivity.class));
            }
        });

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new TarefasViewPagerAdapter(getSupportFragmentManager(),Titles,3);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.tarefas_view_pager_adapter);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tarefas_sliding_tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showSairDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tarefas, menu);

        invalidateOptionsMenu();
        MenuItem item = menu.findItem(R.id.action_tarefas);
        item.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_avaliacoes) {
            startActivity(new Intent(TarefasActivity.this, AvaliacoesActivity.class));
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
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(TarefasActivity.this);
        builder.setMessage("Voce deseja realmente sair?").setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener).show();
    }

}
