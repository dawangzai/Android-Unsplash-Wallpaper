package com.cleverzheng.wallpaper.ui.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseFragment;
import com.cleverzheng.wallpaper.ui.adapter.MeGvAdapter;
import com.cleverzheng.wallpaper.view.widget.MyDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author：cleverzheng
 * @date：2017/4/24:20:34
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class MeFragment extends BaseFragment implements MeContract.View, AdapterView.OnItemClickListener {

    private static final String ITEM_ICON = "ITEM_ICON";
    private static final String ITEM_DESCRIPTION = "ITEM_DESCRIPTION";

    @BindView(R.id.dvUserHead)
    MyDraweeView dvUserHead;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.gvMenu)
    GridView gvMenu;

    private MeContract.Presenter present;
    private MeGvAdapter mAdapter;

    public static MeFragment getInstances() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    public void setPresent(MeContract.Presenter present) {
        this.present = present;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_me);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void initData() {
        super.initData();
        setMenu();
    }

    @Override
    public void setUserHead(String headUrl) {

    }

    @Override
    public void setMenu() {
        String[] arrDescription = getResources().getStringArray(R.array.me_menu);
        int[] arrIcon = new int[]{
                R.mipmap.ic_me_download_100,
                R.mipmap.ic_me_like_100
        };
        List<HashMap<String, Object>> mAdapterData = new ArrayList<>();
        for (int i = 0; i < arrDescription.length; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put(ITEM_ICON, arrIcon[i]);
            data.put(ITEM_DESCRIPTION, arrDescription[i]);
            mAdapterData.add((HashMap<String, Object>) data);
        }
        mAdapter = new MeGvAdapter(getActivity(), mAdapterData);
        gvMenu.setAdapter(mAdapter);
        gvMenu.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, Object> itemAtPosition = (HashMap<String, Object>) parent.getItemAtPosition(position);
        Toast.makeText(getActivity(), itemAtPosition.get(ITEM_DESCRIPTION).toString(), Toast.LENGTH_SHORT).show();
    }
}
