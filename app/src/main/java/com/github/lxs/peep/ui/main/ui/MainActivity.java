package com.github.lxs.peep.ui.main.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.github.lxs.peep.R;
import com.github.lxs.peep.base.BaseActivity;
import com.github.lxs.peep.ui.dy.ui.DYFragment;
import com.github.lxs.peep.ui.wb.ui.TwoFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.layFrame)
    FrameLayout mLayFrame;
    @BindView(R.id.navigation_bar)
    BottomNavigationBar mNavigationBar;

    private SparseArray<Fragment> mSparseArray;
    private FragmentManager mFragmentManager;

//    public static final String APIKEY = "0b2bdeda43b5688921839c8ecb20399b";


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mSparseArray = new SparseArray<>();
        super.init();
    }

    @Override
    protected void initViews() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.layFrame, buildFragment(0)).commitAllowingStateLoss();
        initNavigationBar();
    }

    private void initNavigationBar() {
        mNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Home"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Books"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Music"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "TV"))
                .initialise();
        mNavigationBar.setTabSelectedListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment fragment = mSparseArray.get(position, null);
        if (fragment == null) {
            transaction.add(R.id.layFrame, buildFragment(position));
        } else transaction.show(mSparseArray.get(position));
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onTabUnselected(int position) {
        Fragment fragment = mSparseArray.get(position, null);
        if (fragment != null)
            mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

    @Override
    public void onTabReselected(int position) {

    }

    private Fragment buildFragment(int position) {
        if (position == 0)
            mSparseArray.append(position, new DYFragment());
        else if (position == 1)
            mSparseArray.append(position, new TwoFragment());
        else if (position == 2)
            mSparseArray.append(position, new TwoFragment());
        else if (position == 3)
            mSparseArray.append(position, new TwoFragment());
        else return null;
        return mSparseArray.get(position);
    }

}
