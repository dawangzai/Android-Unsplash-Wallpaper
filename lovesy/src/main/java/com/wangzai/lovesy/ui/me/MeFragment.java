package com.wangzai.lovesy.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.base.ViewPagerFragment;
import com.wangzai.lovesy.operator.ImageLoaderOp;
import com.wangzai.lovesy.ui.adapter.MeGvAdapter;
import com.wangzai.lovesy.ui.sign.SignInActivity;
import com.wangzai.lovesy.view.widget.DraweeImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzai on 2017/4/24
 */
public class MeFragment extends ViewPagerFragment implements MeContract.View, AdapterView.OnItemClickListener {

    private static final String ITEM_ICON = "ITEM_ICON";
    private static final String ITEM_DESCRIPTION = "ITEM_DESCRIPTION";

    @BindView(R.id.dvUserHead)
    DraweeImageView dvUserHead;
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
    public void loadDataSuccess() {

    }

    @Override
    public void loadDataFailed(String message) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        setMenu();
        setUserHead("res://" + getActivity().getPackageName() + "/" + R.mipmap.ic_user);
    }

    @Override
    public void setUserHead(String headUrl) {
        ImageLoaderOp.setRoundImage(dvUserHead, headUrl);
    }

    @Override
    public void setMenu() {
        String[] arrDescription = getResources().getStringArray(R.array.me_menu);
        int[] arrIcon = new int[]{
                R.mipmap.ic_me_download_64,
                R.mipmap.ic_me_like_64
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

//        Intent intent = new Intent(this.getActivity(), SignInActivity.class);
        Intent intent = new Intent(this.getActivity(), SignInActivity.class);
        startActivity(intent);
    }
}
