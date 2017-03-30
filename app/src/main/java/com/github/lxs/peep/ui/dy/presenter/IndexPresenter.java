package com.github.lxs.peep.ui.dy.presenter;

import com.github.lxs.peep.base.BasePresenter;
import com.github.lxs.peep.bean.dy.IndexCateList;
import com.github.lxs.peep.listener.OnLoadCompleteListener;
import com.github.lxs.peep.ui.dy.model.IndexModel;
import com.github.lxs.peep.ui.dy.view.IIndexView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cl on 2017/3/30.
 */

public class IndexPresenter extends BasePresenter<IIndexView> {

    private IndexModel mIndexModel;
    private IIndexView mIIndexView;

    @Inject
    public IndexPresenter(IIndexView mIIndexView) {
        super(mIIndexView);
        this.mIIndexView = mIIndexView;
        mIndexModel = new IndexModel();
    }

    public void loadMenuBaseData() {
        mIndexModel.loadMenuBaseData(new OnLoadCompleteListener<List<IndexCateList>>() {
            @Override
            public void onLoadSuccess(List<IndexCateList> indexCateLists) {
                mIIndexView.setMenuBaseData(indexCateLists);
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });
    }
}
