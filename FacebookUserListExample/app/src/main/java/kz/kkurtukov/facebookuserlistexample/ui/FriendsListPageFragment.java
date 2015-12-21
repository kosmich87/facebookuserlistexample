package kz.kkurtukov.facebookuserlistexample.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
    private ArrayList<FBUser> friendsArrayListForSearch;
    private FriendsListAdapter mAdapter;

    private EditText searchEditText;
    private TextChangeListener textChangeListener;

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
        setHasOptionsMenu(true);
        textChangeListener = new TextChangeListener();
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);

        View actionView = MenuItemCompat.getActionView(item);
        searchEditText = (EditText) actionView.findViewById(R.id.search_view);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                hideKeyboard();
                searchEditText.setText("");
                return true;
            }
        });

        searchEditText.addTextChangedListener(textChangeListener);

        super.onCreateOptionsMenu(menu, inflater);
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
        friendsArrayListForSearch = new ArrayList<FBUser>();
        contentArrayList();
        mAdapter = new FriendsListAdapter(getActivity(), friendsArrayList);
        friendsListView.setAdapter(mAdapter);

        return view;
    }

    private void contentArrayList() {
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
        friendsArrayListForSearch.addAll(friendsArrayList);
    }

    private class TextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            friendsArrayList.clear();
            if (s.length() == 0){
                friendsArrayList.addAll(friendsArrayListForSearch);
            }else {
                for (FBUser fbUser : friendsArrayListForSearch) {
                    if (fbUser.lastName.toLowerCase().contains(s)) {
                        friendsArrayList.add(fbUser);
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void hideKeyboard(){
        InputMethodManager input = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }
}
