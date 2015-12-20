package kz.kkurtukov.facebookuserlistexample.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.kkurtukov.facebookuserlistexample.R;

public class FriendsListFragment extends Fragment {

    static final int PAGE_COUNT = 3;

    private static ViewPager pager;
    private static PagerAdapter pagerAdapter;

    public FriendsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pager = (ViewPager) getView().findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FriendsListPageFragment.newInstance(position);
        }

        @Override
        public int getItemPosition(Object object) {
            FriendsListPageFragment fragment = (FriendsListPageFragment)object;
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = getString(R.string.male_label);
                    break;
                case 1:
                    title = getString(R.string.female_label);
                    break;
                case 2:
                    title = getString(R.string.child_label);
                    break;
            }
            return title;
        }

    }
}
