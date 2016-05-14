package br.com.blackseed.blackimob;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    InquilinosFragment mInquilinosFragment = new InquilinosFragment();
    ImoveisFragment mImoveisFragment = new ImoveisFragment();
    LocacaoFragment mLocacaoFragment = new LocacaoFragment();
    private Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context c) {
        super(fm);
        context = c;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mInquilinosFragment;
            case 1:
                return mImoveisFragment;
            case 2:
                return mLocacaoFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.inquilinos);
            case 1:
                return context.getString(R.string.imoveis);
            case 2:
                return context.getString(R.string.locacoes);
        }
        return null;
    }

    public void updatePages() {
        mInquilinosFragment.updateList();
    }
}