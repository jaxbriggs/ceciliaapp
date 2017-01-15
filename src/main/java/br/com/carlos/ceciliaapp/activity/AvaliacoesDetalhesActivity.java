package br.com.carlos.ceciliaapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.adapter.AvaiacoesDetalhesViewPagerAdapter;
import br.com.carlos.ceciliaapp.slidingtabs.SlidingTabLayout;

public class AvaliacoesDetalhesActivity extends AppCompatActivity {

    ViewPager pager;
    AvaiacoesDetalhesViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"AVALIAR","FOTO","COMENTÁRIOS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacoes_detalhes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Avalições");

        adapter =  new AvaiacoesDetalhesViewPagerAdapter(getSupportFragmentManager(),Titles,3);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.avaliacoes_detalhes_view_pager_adapter);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.avaliacoes_detalhes_sliding_tabs);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhe_avaliacoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_voltar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
