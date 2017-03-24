/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.lxs.peep.baseadapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A adapter using View Holder to display the item of ComingSoon list view;
 *
 * @param <ItemDataType>
 * @author http://www.liaohuqiu.net
 */
public class ListViewDataAdapter<ItemDataType> extends ListViewDataAdapterBase<ItemDataType> {

    protected List<ItemDataType> mItemDataList;

    public ListViewDataAdapter() {

    }

    /**
     * @param viewHolderCreator The view holder creator will create ComingSoon View Holder that extends {@link ViewHolderBase}
     */
    public ListViewDataAdapter(ViewHolderCreator<ItemDataType> viewHolderCreator, List<ItemDataType> mItemDataList) {
        super(viewHolderCreator);
        this.mItemDataList = mItemDataList;
    }

    public List<ItemDataType> getDataList() {
        return mItemDataList;
    }

    public void refreshList(List<ItemDataType> mItemDataList) {
        this.mItemDataList.clear();
        this.mItemDataList.addAll(mItemDataList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItemDataList.size();
    }

    @Override
    public ItemDataType getItem(int position) {
        if (mItemDataList.size() <= position || position < 0) {
            return null;
        }
        return mItemDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
