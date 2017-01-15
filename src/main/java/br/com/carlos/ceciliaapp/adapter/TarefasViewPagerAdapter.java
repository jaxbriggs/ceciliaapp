package br.com.carlos.ceciliaapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.carlos.ceciliaapp.fragment.GerenciarTarefasFragment;
import br.com.carlos.ceciliaapp.fragment.MinhasTarefasFragment;
import br.com.carlos.ceciliaapp.fragment.TarefasFragment;

/**
 * Created by Carlos Henrique on 1/7/2017.
 */
public class TarefasViewPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public TarefasViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0)
        {
            TarefasFragment tab1 = new TarefasFragment();
            return tab1;
        }
        else if(position == 1)
        {
            MinhasTarefasFragment tab2 = new MinhasTarefasFragment();
            return tab2;
        } else {
            GerenciarTarefasFragment tab3 = new GerenciarTarefasFragment();
            return tab3;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
