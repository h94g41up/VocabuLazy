package wishcantw.vocabulazy.exam.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import wishcantw.vocabulazy.R;
import wishcantw.vocabulazy.exam.fragment.ExamFragment;
import wishcantw.vocabulazy.exam.fragment.ExamResultFragment;
import wishcantw.vocabulazy.exam.fragment.ExamVocTooLessDialogFragment;
import wishcantw.vocabulazy.storage.Database;
import wishcantw.vocabulazy.utility.Logger;

/**
 * Created by SwallowChen on 8/25/16.
 */
public class ExamActivity extends AppCompatActivity implements ExamFragment.OnExamDoneListener,
                                                               ExamResultFragment.OnExamResultDoneListener,
                                                               ExamVocTooLessDialogFragment.OnExamAlertDoneListener {
    public static final String TAG = "ExamActivity";
    public static final String ARG_BOOK_INDEX = "arg-book-index";
    public static final String ARG_LESSON_INDEX = "arg-lesson-index";

    private static final int VIEW_RES_ID = R.layout.activity_exam;
    private static final int VIEW_MAIN_RES_ID = R.id.exam_fragment_container;
    private static final int TOOLBAR_RES_ID = R.id.toolbar;

    private ExamFragment mExamFragment;
    private ExamResultFragment mExamResultFragment;
    private ExamVocTooLessDialogFragment mExamVocTooLessDialogFragment;

    private int mBookIndex;
    private int mLessonIndex;

    private Database mDatabase;

    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBookIndex = getIntent().getIntExtra(ARG_BOOK_INDEX, 1359);
        mLessonIndex = getIntent().getIntExtra(ARG_LESSON_INDEX, 1359);

        setContentView(VIEW_RES_ID);
        mToolbar = (Toolbar) findViewById(TOOLBAR_RES_ID);

        // get Database instance
        mDatabase = Database.getInstance();

        setSupportActionBar(mToolbar);
        setActionBarTitle(Database.getInstance().getLessonTitle(mBookIndex, mLessonIndex));

        ExamFragment fragment = ExamFragment.newInstance(mBookIndex, mLessonIndex);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(ExamActivity.VIEW_MAIN_RES_ID, fragment, "ExamFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof ExamFragment) {
            mExamFragment = (ExamFragment) fragment;
            mExamFragment.setOnExamDoneListener(this);
        } else if (fragment instanceof ExamResultFragment) {
            mExamResultFragment = (ExamResultFragment) fragment;
            mExamResultFragment.setOnExamResultDoneListener(this);
        } else if (fragment instanceof ExamVocTooLessDialogFragment) {
            // Handle listener if necessary
            mExamVocTooLessDialogFragment = (ExamVocTooLessDialogFragment) fragment;
            mExamVocTooLessDialogFragment.setOnExamAlertDoneListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDatabase.getContentIds(mBookIndex, mLessonIndex).size() <= 3) {
            onVocToLessAlert();
            Log.d("ExamActivity", "getContentIds + " + mDatabase.getContentIds(mBookIndex, mLessonIndex).size());
        }
    }

    @Override
    public void onExamDone(int totalNumber, int correctNumber) {
        Logger.d(TAG, "onExamDone" + correctNumber);
        int correctCount, correctRatio;
        correctCount = correctNumber;
        correctRatio = (int) ((correctNumber / (float)totalNumber) * 100);
        ExamResultFragment fragment = ExamResultFragment.newInstance(correctCount, correctRatio);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(ExamActivity.VIEW_MAIN_RES_ID, fragment, "ExamResultFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onExamTryAgain() {
        Logger.d(TAG, "onExamTryAgain");
        ExamFragment fragment = ExamFragment.newInstance(mBookIndex, mLessonIndex);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(ExamActivity.VIEW_MAIN_RES_ID, fragment, "ExamFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onExamTryOther() {
        Logger.d(TAG, "onExamTryOther");
        finish();
    }

    @Override
    public void onExamAlertDone() {
        Logger.d(TAG, "onExamAlertDone");
        finish();
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }

    private void onVocToLessAlert() {
        Logger.d(TAG, "onVocToLessAlert");
        ExamVocTooLessDialogFragment dialogFragment = new ExamVocTooLessDialogFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(ExamActivity.VIEW_MAIN_RES_ID, dialogFragment, "ExamVocTooLessDialogFragment");
        fragmentTransaction.addToBackStack("ExamVocTooLessDialogFragment");
        fragmentTransaction.commit();
    }
}
