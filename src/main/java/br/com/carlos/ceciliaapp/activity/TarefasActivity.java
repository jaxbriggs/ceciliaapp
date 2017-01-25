package br.com.carlos.ceciliaapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.json.JSONObject;

import java.util.List;

import br.com.carlos.ceciliaapp.Application;
import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.adapter.TarefasViewPagerAdapter;
import br.com.carlos.ceciliaapp.dao.ORMLiteHelper;
import br.com.carlos.ceciliaapp.enumeration.HttpMethod;
import br.com.carlos.ceciliaapp.fragment.GerenciarTarefasFragment;
import br.com.carlos.ceciliaapp.http.DownloadCallback;
import br.com.carlos.ceciliaapp.http.NetworkFragment;
import br.com.carlos.ceciliaapp.model.Tarefa;
import br.com.carlos.ceciliaapp.slidingtabs.SlidingTabLayout;

public class TarefasActivity extends AppCompatActivity implements DownloadCallback {

    //Vars
    private NetworkFragment mNetworkFragment;
    private boolean mDownloading = false;

    ViewPager pager;
    TarefasViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"DISPONÍVEIS","MINHAS","GERENCIAR"};

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    private FragmentRefreshListener fragmentRefreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);

        //Atualiza os dados do banco local
        Application.updateAllData(TarefasActivity.this);

        //Pega tarefas gerenciaveis
        RuntimeExceptionDao<Tarefa, Integer> tarefaDao =
                ORMLiteHelper.getInstance(TarefasActivity.this).getTarefaRuntimeDao();
        List<Tarefa> tarefas = tarefaDao.queryForAll();

        ((Application)getApplicationContext()).tarefasGerenciaveis = tarefas;

        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), "user/tarefas?XDEBUG_SESSION_START=CARLOS-HENRIQUE$");

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
    protected void onResume() {
        super.onResume();
        if(getFragmentRefreshListener() != null && ((Application)getApplicationContext()).isTarefasGerenciaveisChanged) {
            getFragmentRefreshListener().onRefresh();
        }
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

    private void startDownload(HttpMethod method, JSONObject output) {
        if (!mDownloading && mNetworkFragment != null) {
            // Execute the async download.
            mNetworkFragment.startDownload(method, output);
            mDownloading = true;
        }
    }

    @Override
    public void updateFromDownload(Object result) {

    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        //NOT USED
    }

    @Override
    public void finishDownloading() {
        mDownloading = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }

    public interface FragmentRefreshListener {
        void onRefresh();
    }
}
