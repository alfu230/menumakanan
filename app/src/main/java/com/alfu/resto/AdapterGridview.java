package com.alfu.resto;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

public class AdapterGridview extends ArrayAdapter<Item> {

    private List<Item> items;
    private Item objBean;
    private Activity activity;
    private int row;

    public AdapterGridview(Activity ctx, int resource, List<Item> itm) {
        super(ctx, resource, itm);
        this.row = resource;
        this.activity = ctx;
        this.items = itm;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        View view = paramView;
        ViewHolder item;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);
            item = new ViewHolder();
            view.setTag(item);
        } else {
            item = (ViewHolder) view.getTag();
        }

        if ((items == null) || ((paramInt + 1) > items.size()))
            return view;

        objBean = items.get(paramInt);

        item.name = view.findViewById(R.id.txtGrid);
        item.imgName = view.findViewById(R.id.imgView);
        item.pbar = view.findViewById(R.id.pBarGrid);

        if (item.name != null && null != objBean.getNama()
                && objBean.getNama().trim().length() > 0) {
            item.name.setText(Html.fromHtml(objBean.getNama() + " | Rp. " + objBean.getHarga()));
        }
        if (item.imgName != null) {
            String url = objBean.getLink();
            final ProgressBar pbar = item.pbar;
            if (null != url && url.trim().length() > 0) {
                pbar.setVisibility(View.VISIBLE);
                Glide.with(activity).load(url).asBitmap().placeholder(R.mipmap.ic_launcher)
                        .into(new BitmapImageViewTarget(item.imgName) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                super.onResourceReady(resource, glideAnimation);
                                pbar.setVisibility(View.GONE);
                            }
                        });
            } else {
                item.imgName.setImageResource(R.mipmap.ic_launcher);
            }
        }
        return view;
    }

    private class ViewHolder {
        TextView name;
        ImageView imgName;
        ProgressBar pbar;
    }
}
