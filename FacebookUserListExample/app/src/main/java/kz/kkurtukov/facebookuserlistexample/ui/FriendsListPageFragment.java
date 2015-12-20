package kz.kkurtukov.facebookuserlistexample.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kz.kkurtukov.facebookuserlistexample.R;
import kz.kkurtukov.facebookuserlistexample.model.FBUser;
import kz.kkurtukov.facebookuserlistexample.model.FBUserManager;


public class FriendsListPageFragment extends Fragment {

    private static final String ARG_PAGE = "page";

    private int mPage;
    private ListView friendsListView;
    private ArrayList<FBUser> friendsArrayList;
    private FriendsListAdapter mAdapter;


    public FriendsListPageFragment() {
        // Required empty public constructor
    }

    public static FriendsListPageFragment newInstance(int page) {
        FriendsListPageFragment fragment = new FriendsListPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends_list_page, container, false);

        friendsListView = (ListView) view.findViewById(R.id.friends_listView);

        TextView emptyTextView = (TextView) view.findViewById(R.id.friends_listView_empty);
        friendsListView.setEmptyView(emptyTextView);

        friendsArrayList = new ArrayList<FBUser>();
        switch (mPage){
            case 0:
                friendsArrayList.addAll(FBUserManager.getFriendsByGender("male"));
                break;
            case 1:
                friendsArrayList.addAll(FBUserManager.getFriendsByGender("female"));
                break;
            case 2:
                friendsArrayList.addAll(FBUserManager.getChilds());
                break;
        }
        mAdapter = new FriendsListAdapter(getActivity(), friendsArrayList);
        friendsListView.setAdapter(mAdapter);

        return view;
    }

}
