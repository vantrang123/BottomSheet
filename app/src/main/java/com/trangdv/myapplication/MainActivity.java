package com.trangdv.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    BottomSheetBehavior mBottomSheetBehavior;
    GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showBottomSheetBtn = findViewById(R.id.btn_show_BottomSheetBehavior);
        Button showBottomSheetDialogBtn1 = findViewById(R.id.btn_show_BottomSheetDialog1);
        Button showBottomSheetDialogBtn2 = findViewById(R.id.btn_show_BottomSheetDialog2);
        Button showBottomSheetDialogBtn3 = findViewById(R.id.btn_show_BottomSheetDialog3);
        Button showBottomSheetDialogFragmentBtn = findViewById(R.id.btn_show_BottomSheetDialogFragment);

        View bottomSheet = findViewById(R.id.layout_bottomSheetBehavior);
        TextView tv1 = bottomSheet.findViewById(R.id.tv);
        TextView tv2 = bottomSheet.findViewById(R.id.tv2);
        TextView tv3 = bottomSheet.findViewById(R.id.tv3);

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(0);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        showBottomSheetBtn.setOnClickListener(this);
        showBottomSheetDialogBtn1.setOnClickListener(this);
        showBottomSheetDialogBtn2.setOnClickListener(this);
        showBottomSheetDialogBtn3.setOnClickListener(this);
        showBottomSheetDialogFragmentBtn.setOnClickListener(this);

        initGestureDetector();
    }

    private void initGestureDetector() {
        mGestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            return true;
                        }
                        return super.onSingleTapConfirmed(e);
                    }
                });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv3:
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            case R.id.btn_show_BottomSheetBehavior:
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.btn_show_BottomSheetDialog1:
                initBottomSheetDialog1();
                break;
            case R.id.btn_show_BottomSheetDialog2:
                initBottomSheetDialog2();
                break;

            case R.id.btn_show_BottomSheetDialog3:
                initBottomSheetDialog3();
                break;

            case R.id.btn_show_BottomSheetDialogFragment:
                new FullSheetDialogFragment().show(getSupportFragmentManager(), "dialog");

                break;
            default:
                break;

        }

    }

    private void initBottomSheetDialog3() {
        RecyclerView recyclerView;
        //startActivity(new Intent(MainActivity.this,RecyclerVireWithBottomSheetDialogActivity.class));
        BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
        View view = View.inflate(MainActivity.this, R.layout.bottom_sheet_dialog_share, null);
        dialog.setContentView(view);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ShareRecyclerViewAdapter(MainActivity.this,dialog));
        dialog.show();
    }

    private void initBottomSheetDialog2() {
        List<String> mList;
        mList = new ArrayList<>();
        for(int i=0; i<20; i++){
            mList.add("item "+i);
        }

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        RecyclerView recyclerView = new RecyclerView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(mList,this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View item, int position) {
                Toast.makeText(MainActivity.this, "item "+position, Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(recyclerView);
        bottomSheetDialog.show();
    }

    private void initBottomSheetDialog1() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View inflate = View.inflate(this, R.layout.bottom_sheet_dialog_fragment, null);
        View qq = inflate.findViewById(R.id.share_qq);
        View wx = inflate.findViewById(R.id.share_wx);
        View sina = inflate.findViewById(R.id.share_sina);
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(inflate);
        bottomSheetDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        super.onBackPressed();
    }
}
