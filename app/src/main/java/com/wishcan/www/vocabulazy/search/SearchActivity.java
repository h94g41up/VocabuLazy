package com.wishcan.www.vocabulazy.search;

import android.app.ActionBar;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;

import com.wishcan.www.vocabulazy.R;
import com.wishcan.www.vocabulazy.search.fragment.SearchFragment;
import com.wishcan.www.vocabulazy.storage.Database;
import com.wishcan.www.vocabulazy.storage.Vocabulary;


public class SearchActivity extends FragmentActivity {

    private static final int VIEW_ACTIVITY_RES_ID = R.layout.view_search_activity;
    private static final int VIEW_CONTAINER_RES_ID = R.id.activity_search_container;
    private static final int DEFAULT_MENU_RES_ID = R.menu.menu_search;
    private static final int DEFAULT_SEARCH_ITEM_RES_ID = R.id.action_search;

    private SearchFragment mSearchFragment;
    private SearchView mSearchView;
    private FragmentManager mFragmentManager;
    private MenuItem mSearchItem;
    private Menu mMenu;
    private ActionBar mActionBar;
    private Database mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(VIEW_ACTIVITY_RES_ID);
        if (savedInstanceState == null) {
            mSearchFragment = new SearchFragment();
            mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.add(VIEW_CONTAINER_RES_ID, mSearchFragment, "SearchFragment");
            fragmentTransaction.commit();
        }
        mDatabase = new Database(this);
        mActionBar = getActionBar();
        if(mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDatabase = new Database(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDatabase.writeToFile(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(DEFAULT_MENU_RES_ID, menu);
        mMenu = menu;

        mSearchItem = menu.findItem(DEFAULT_SEARCH_ITEM_RES_ID);
        mSearchView = (SearchView) mSearchItem.getActionView();

        mSearchView.onActionViewExpanded();          // Important, make ActionView expand initially
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Vocabulary> searchResultsList;
                if (newText.equals("")) {
                    searchResultsList = new ArrayList<>();
                } else {
                    searchResultsList = mDatabase.readSuggestVocabularyBySpell(newText);
                }
                mSearchFragment.refreshSearchResult(searchResultsList);
//                refreshSearchResult(mSearchResultsList);
                return true;
            }
        });

        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (mNewNoteDialogView != null)
//                    closeDialog(mNewNoteDialogView);
//                if (mDialogView != null)
//                    closeDialog(mDialogView);
//                if (mSearchDetailParentView.getVisibility() == View.VISIBLE)
//                    closeSearchDetail();
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (mNewNoteDialogView != null)
//            closeDialog(mNewNoteDialogView);
//        else if (mDialogView != null)
//            closeDialog(mDialogView);
//        else {
//            setResult(RESULT_OK, new Intent());
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
}
