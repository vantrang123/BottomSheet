package com.trangdv.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class FullSheetDialogFragment extends BottomSheetDialogFragment {
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog dialog;
    private RecyclerView recyclerView;
    private List<String> mList = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private TextView tvTitle;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.bottom_sheet_dialog_fragment, null);
        dialog.setContentView(view);

        initRecv();
        initView();
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;

    }

    private void initView() {
        tvTitle = dialog.findViewById(R.id.tv_title);
    }

    private void initRecv() {
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(mList,getContext());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View item, int position) {
                Toast.makeText(getContext(), "Clicked Item!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for(int i=0; i<20; i++){
            mList.add("item posittion "+i);
        }
        recyclerAdapter.notifyDataSetChanged();

        tvTitle.setText("Danh sách:");
    }

    public void doclick(View v) {
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
}
