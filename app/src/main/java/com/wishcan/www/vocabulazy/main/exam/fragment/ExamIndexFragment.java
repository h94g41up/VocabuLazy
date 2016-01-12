package com.wishcan.www.vocabulazy.main.exam.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wishcan.www.vocabulazy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExamIndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamIndexFragment extends Fragment {

    public static ExamIndexFragment newInstance(String param1, String param2) {
        ExamIndexFragment fragment = new ExamIndexFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ExamIndexFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_exam_index, container, false);
    }


}
