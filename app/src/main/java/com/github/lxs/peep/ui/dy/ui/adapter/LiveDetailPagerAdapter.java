package com.github.lxs.peep.ui.dy.ui.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class LiveDetailPagerAdapter extends FragmentPagerAdapter {
	
	private List<Fragment> mList;

	public LiveDetailPagerAdapter(FragmentManager fm, List<Fragment> mList) {
		super(fm);
		this.mList = mList;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}
}
