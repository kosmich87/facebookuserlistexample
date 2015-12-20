package kz.kkurtukov.facebookuserlistexample.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import kz.kkurtukov.facebookuserlistexample.ApplicationLoader;
import kz.kkurtukov.facebookuserlistexample.R;
import kz.kkurtukov.facebookuserlistexample.model.FBUser;
import kz.kkurtukov.facebookuserlistexample.utilities.RequestQueueSingleton;

/**
 * Created by kkurtukov on 20.12.2015.
 */
public class FriendsListAdapter extends BaseAdapter{

    private List<FBUser> items;
    private LayoutInflater inflater;

    public FriendsListAdapter(Context context, List<FBUser> items) {
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class Holder {
        com.android.volley.toolbox.NetworkImageView friendPhoto;
        TextView friendName;
        TextView placeOfBirthOrLiving;
        TextView placeOfStudy;
        ImageView status;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder;

        view = inflater.inflate(R.layout.friend_cell, null);
        holder = new Holder();

        holder.friendPhoto = (com.android.volley.toolbox.NetworkImageView) view.findViewById(R.id.friend_photo);
        ImageLoader mImageLoader;
        mImageLoader = RequestQueueSingleton.getInstance(ApplicationLoader.applicationContext).getImageLoader();
        holder.friendPhoto.setImageUrl(items.get(position).photoUrl, mImageLoader);

        holder.friendName = (TextView) view.findViewById(R.id.friend_name);
        holder.friendName.setText(items.get(position).name);

        holder.placeOfBirthOrLiving = (TextView) view.findViewById(R.id.place_of_birth);
        holder.placeOfBirthOrLiving.setText(items.get(position).birthOrLivingPlace);

        holder.placeOfStudy = (TextView) view.findViewById(R.id.place_of_study);
        holder.placeOfStudy.setText(items.get(position).studyPlace);

        holder.status = (ImageView) view.findViewById(R.id.friend_status);
        if (items.get(position).state == 0){
            holder.status.setImageResource(android.R.drawable.presence_offline);
        }

        return view;
    }
}
