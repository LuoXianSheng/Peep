package com.github.lxs.peep.ui.dy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.lxs.peep.R;
import com.github.lxs.peep.baseadapter.BaseAdapter;
import com.github.lxs.peep.baseadapter.BaseViewHolder;
import com.github.lxs.peep.image.GlideTransform;

import butterknife.BindView;

/**
 * Created by cl on 2017/3/27.
 */

public class MenuAdapter extends BaseAdapter<String> {

    private Context mContext;
    private MenuItemClick onClick;
    private String[] iconUrl = {"https://staticlive.douyucdn.cn//upload//game_cate//f719087663581b7a723c4d39f8721bc1.jpg",
            "https://staticlive.douyucdn.cn//upload//game_cate//67b6c31daee46b0e9d9ec9d420721149.jpg",
            "https://staticlive.douyucdn.cn//upload//game_cate//d3e0073bfb714186ab0c818744601963.jpg",
            "https://staticlive.douyucdn.cn//upload//game_cate//ac9e22d6b80cb57b878e6fa3cca15400.jpg",
            "https://staticlive.douyucdn.cn//upload//game_cate//ac9e22d6b80cb57b878e6fa3cca15400.jpg",
            "https://staticlive.douyucdn.cn//upload//game_cate//ac9e22d6b80cb57b878e6fa3cca15400.jpg",
            "https://staticlive.douyucdn.cn//upload//game_cate//ac9e22d6b80cb57b878e6fa3cca15400.jpg",
            "https://staticlive.douyucdn.cn//upload//game_cate//ac9e22d6b80cb57b878e6fa3cca15400.jpg"};

    public MenuAdapter(Context context, MenuItemClick onClick) {
        super(context);
        mContext = context;
        this.onClick = onClick;
    }

    public MenuAdapter(Context context, boolean useAnimation) {
        super(context, useAnimation);
        mContext = context;
    }

    @Override
    protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        return new CateListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.circle_text_item, parent, false));
    }

    class CateListViewHolder extends BaseViewHolder {
        @BindView(R.id.img)
        ImageView mImg;
        @BindView(R.id.text)
        TextView mText;

        public CateListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(int position) {
            mText.setText(mData.get(position));
            itemView.setOnClickListener(v -> onClick.onMenuItemClick(position));
            Glide.with(mContext)
                    .load(iconUrl[position])
                    .dontAnimate()
                    .transform(new GlideTransform(mContext, GlideTransform.CIRCLE))
                    .into(mImg);
        }
    }

    public interface MenuItemClick {
        void onMenuItemClick(int position);
    }
}
