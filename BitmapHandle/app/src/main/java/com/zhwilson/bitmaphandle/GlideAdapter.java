package com.zhwilson.bitmaphandle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class GlideAdapter extends BaseAdapter {
    private List<String> imageUrls;
    private LayoutInflater inflater;
    public GlideAdapter(Context context, List<String> urls){
        if (imageUrls == null) imageUrls = new ArrayList<>();
        imageUrls.clear();
        imageUrls.addAll(urls);

        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int i) {
        return imageUrls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) view = inflater.inflate(R.layout.image_item, viewGroup, false);

        String imageUrl = (String) getItem(i);
        ImageView imageView = view.findViewById(R.id.image_view);
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
        return view;
    }
}
