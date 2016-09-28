package com.wishcan.www.vocabulazy.mainmenu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishcan.www.vocabulazy.R;
import com.wishcan.www.vocabulazy.mainmenu.activity.MainMenuActivity;
import com.wishcan.www.vocabulazy.mainmenu.adapter.MainMenuFragmentPagerAdapter;
import com.wishcan.www.vocabulazy.mainmenu.exam.fragment.ExamIndexFragment;
import com.wishcan.www.vocabulazy.mainmenu.info.InfoFragment;
import com.wishcan.www.vocabulazy.mainmenu.model.MainMenuModel;
import com.wishcan.www.vocabulazy.mainmenu.note.fragment.NoteFragment;
import com.wishcan.www.vocabulazy.mainmenu.textbook.fragment.TextbookFragment;
import com.wishcan.www.vocabulazy.mainmenu.view.MainMenuViewPager;

public class MainMenuFragment extends Fragment implements TextbookFragment.OnTextbookClickListener, NoteFragment.OnNoteClickListener, ExamIndexFragment.OnExamIndexClickListener {

    public interface OnMainMenuEventListener {
        void onTextbookSelected(int bookIndex, int lessonIndex);
        void onNoteSelected(int noteIndex);
        void onNoteRename(int noteIndex, String originalName);
        void onNoteDelete(int noteIndex, String noteTitle);
        void onNoteCreate();
        void onExamTextbookSelected(int examBookIndex, int examLessonIndex);
        void onExamNoteSelected(int examNoteIndex);
    }

    public static final String TAG = "MainMenuFragment";

    private MainMenuModel mMainMenuModel;
    private OnMainMenuEventListener mOnMainMenuEventListener;

    /**
     * Fragments for ViewPager
     * */
    private TextbookFragment mTextbookFragment;
    private NoteFragment mNoteFragment;
    private ExamIndexFragment mExamIndexFragment;
    private InfoFragment mInfoFragment;

    /**
     * TabItems for each fragments
     * */
    private TabItem mTextbookTabItem;
    private TabItem mNoteTabItem;
    private TabItem mExamIndexTabItem;
    private TabItem mInfoTabItem;

    public MainMenuFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Init tab items corresponding to fragments */
        initTabItems();
        /** Init fragments for view pager */
        initFragments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);

        Fragment[] fragments = new Fragment[]{mTextbookFragment, mNoteFragment, mExamIndexFragment, mInfoFragment};
        TabItem[] tabItems = new TabItem[] {mTextbookTabItem, mNoteTabItem, mExamIndexTabItem, mInfoTabItem};
        int[] tabItemResIds = {R.drawable.main_menu_tab_item_text_book, R.drawable.main_menu_tab_item_note, R.drawable.main_menu_tab_item_exam_index, R.drawable.main_menu_tab_item_info};
        String[] titles = getResources().getStringArray(R.array.main_menu_tab_title);
        MainMenuFragmentPagerAdapter pagerAdapter = new MainMenuFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments);

        MainMenuViewPager viewPager = (MainMenuViewPager) rootView.findViewById(R.id.main_menu_viewpager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragments.length);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.main_menu_tab_container);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabItems.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabItemResIds[i]);
            tabLayout.getTabAt(i).setText(titles[i]);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMainMenuModel = ((MainMenuActivity) getActivity()).getModel();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFragmentsContent();
    }

    @Override
    public void onTextbookClicked(int bookIndex, int lessonIndex) {
        Log.d(TAG, "Textbook [" + bookIndex + ", " + lessonIndex + "] clicked");
        if (mOnMainMenuEventListener != null) {
            mOnMainMenuEventListener.onTextbookSelected(bookIndex, lessonIndex);
        }
    }

    @Override
    public void onNotePlay(int noteIndex) {
        Log.d(TAG, "Note [PLAY " + noteIndex + "]");
        if (mOnMainMenuEventListener != null) {
            mOnMainMenuEventListener.onNoteSelected(noteIndex);
        }
    }

    @Override
    public void onNoteRename(int noteIndex, String name) {
        Log.d(TAG, "Note [RENAME " + name + " at " + noteIndex+"]");
        if (mOnMainMenuEventListener != null) {
            mOnMainMenuEventListener.onNoteRename(noteIndex, name);
        }
    }

    @Override
    public void onNoteCopy() {
        Log.d(TAG, "Note [COPY]");
    }

    @Override
    public void onNoteDelete(int noteIndex, String name) {
        Log.d(TAG, "Note [DELETE " + noteIndex + "]");
        if (mOnMainMenuEventListener != null) {
            mOnMainMenuEventListener.onNoteDelete(noteIndex, name);
        }
    }

    @Override
    public void onNoteCreate() {
        if (mOnMainMenuEventListener != null) {
            mOnMainMenuEventListener.onNoteCreate();
        }
    }

    @Override
    public void onExamTextbookClicked(int bookIndex, int lessonIndex) {
        Log.d(TAG, "Exam textbook [" + bookIndex + ", " + lessonIndex + "] clicked");
        if (mOnMainMenuEventListener != null) {
            mOnMainMenuEventListener.onExamTextbookSelected(bookIndex, lessonIndex);
        }
    }

    @Override
    public void onExamNoteClicked(int noteIndex) {
        Log.d(TAG, "Exam note [" + noteIndex + "]");
        if (mOnMainMenuEventListener != null) {
            mOnMainMenuEventListener.onExamNoteSelected(noteIndex);
        }
    }

    public void addOnMainMenuEventListener(OnMainMenuEventListener listener) {
        mOnMainMenuEventListener = listener;
    }

    private void initTabItems() {
        if (mTextbookTabItem == null) {
            mTextbookTabItem = (TabItem) ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_main_menu_tab_item_textbook, null);
        }

        if (mNoteTabItem == null) {
            mNoteTabItem = new TabItem(getContext());
        }

        if (mExamIndexTabItem == null) {
            mExamIndexTabItem = new TabItem(getContext());
        }

        if (mInfoTabItem == null) {
            mInfoTabItem = new TabItem(getContext());
        }
    }

    private void initFragments() {
        if (mTextbookFragment == null) {
            mTextbookFragment = TextbookFragment.newInstance();
            mTextbookFragment.addOnTextbookClickListener(this);
        }

        if (mNoteFragment == null) {
            mNoteFragment = NoteFragment.newInstance();
            mNoteFragment.addOnNoteClickListener(this);
        }

        if (mExamIndexFragment == null) {
            mExamIndexFragment = ExamIndexFragment.newInstance();
            mExamIndexFragment.addOnExamIndexClickListener(this);
        }

        if (mInfoFragment == null) {
            mInfoFragment = InfoFragment.getInstance();
        }
    }

    public void updateFragmentsContent() {
        mMainMenuModel.generateBookItems();
        mMainMenuModel.generateNoteItems();
        mMainMenuModel.generateExamIndexItems();
        mTextbookFragment.updateBookContent(mMainMenuModel.getTextbookGroupItems(), mMainMenuModel.getTextbookChildItemsMap());
        mNoteFragment.updateNoteContent(mMainMenuModel.getNoteGroupItems(), mMainMenuModel.getNoteChildItemsMap());
        mExamIndexFragment.updateExamIndexContent(mMainMenuModel.getExamIndexTextbookGroupItems(), mMainMenuModel.getExamIndexTextbookChildItemsMap(), mMainMenuModel.getExamIndexNoteGroupItems(), mMainMenuModel.getExamIndexNoteChildItemsMap());
    }

    public void refreshFragments() {
        mTextbookFragment.refresh();
        mNoteFragment.refresh();
        mExamIndexFragment.refresh();
    }
}
