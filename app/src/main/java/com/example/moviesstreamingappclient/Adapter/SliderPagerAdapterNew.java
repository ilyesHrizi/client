package com.example.moviesstreamingappclient.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.moviesstreamingappclient.Model.SliderSide;
import com.example.moviesstreamingappclient.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SliderPagerAdapterNew  extends PagerAdapter {
private Context mContext ;
List<SliderSide> mlist ;


    public SliderPagerAdapterNew(Context mContext, List<SliderSide> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    public SliderPagerAdapterNew() {
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<SliderSide> getMlist() {
        return mlist;
    }

    public void setMlist(List<SliderSide> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View slideLayout = inflater.inflate(R.layout.slide_item,null);
        ImageView slideimage = slideLayout.findViewById(R.id.slide_img);
        TextView slidetitle = slideLayout.findViewById(R.id.slide_title);
        FloatingActionButton floatingActionButton = slideLayout.findViewById(R.id.floatimgActionBotton);
        Glide.with(mContext).load(mlist.get(position).getVideo_thumb()).into(slideimage);
        slidetitle.setText(mlist.get(position).getVideo_name()+"/n"+mlist.get(position).getVideo_description());
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        container.addView(slideLayout);
        return slideLayout;
    }


    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
