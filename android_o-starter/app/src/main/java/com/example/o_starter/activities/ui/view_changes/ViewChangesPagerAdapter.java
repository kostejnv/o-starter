package com.example.o_starter.activities.ui.view_changes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.o_starter.R;
import com.example.o_starter.database.StartlistsDatabase;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ViewChangesPagerAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_changes, R.string.tab_unstarted};
    private final Context mContext;
    private final int competitionId;

    public ViewChangesPagerAdapter(Context context, FragmentManager fm, int competitionId) {
        super(fm);
        mContext = context;
        this.competitionId = competitionId;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return ChangesFragment.newInstance(competitionId);
            case 1:
                //Create view
                boolean wasCompetitionFinished = StartlistsDatabase.getInstance(mContext).competitionDao().GetCompetitionById(competitionId).isWasFinished();
                if(wasCompetitionFinished){
                    return UnstartedFragment.newInstance(competitionId);
                }
                else{
                    return UnstartedNotFinishedFragment.newInstance(competitionId);
                }
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    /**
     * override to update all tabs after NotifySetCHanged()
     */
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

}