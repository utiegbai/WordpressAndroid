package com.entpress.entpress.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.entpress.entpress.R;

import com.entpress.entpress.models.Posts;
import com.entpress.entpress.utility.api.ApiUrl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ST_004 on 19-08-2016.
 */
public class Profile_Posts_Adapter extends ArrayAdapter<Posts> {
    Context mcontext;
    private List<Posts> tracks = new ArrayList<>();
    private LayoutInflater inflater;
    ViewHolder viewHolder;
    private int count;
    private String uid;
    private List<Posts> temp = new ArrayList<>();
    public static ArrayList<Posts> single_track_list = new ArrayList<>();
    private static String KEY_SUCCESS = "success";
    private boolean FROM_DOWNLOAD = false;
    private Posts user1;

    private static class ViewHolder {
        TextView txt_track_name, text_name;
        ImageView iv_track_image;
        LinearLayout linearlayout_artist;
    }

    public Profile_Posts_Adapter(Context context, ArrayList<Posts> track123, int count, boolean FROM_DOWNLOAD) {
        super(context, R.layout.list_profile_posts, track123);
        this.mcontext = context;
        this.count = count;
        this.tracks = track123;
        temp.addAll(tracks);
        this.FROM_DOWNLOAD = FROM_DOWNLOAD;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public Posts getItem(int position) {
        return tracks.get(position);
    }

    public void setData(ArrayList<Posts> temp_list) {
        this.tracks = temp_list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        user1 = tracks.get(position);
        try {
            String TAG_TOP = "TOP";
            if (count == 0) {
                if (convertView == null || convertView.getTag().equals(TAG_TOP)) {
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.list_profile_posts, parent, false);
                    viewHolder.txt_track_name = (TextView) convertView.findViewById(R.id.track_title);
                    viewHolder.iv_track_image = (ImageView) convertView.findViewById(R.id.track_icon);
                    viewHolder.text_name = (TextView) convertView.findViewById(R.id.track_info);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                String title = user1.getPostTitle();
                String img_url = user1.getThumbnail();
                String name = user1.getPostContent();
                //String image = user1.getImageART();

                if (title.equalsIgnoreCase("")) {
                    viewHolder.linearlayout_artist.setVisibility(View.GONE);
                } else {
                    viewHolder.txt_track_name.setText(name);
                    viewHolder.text_name.setText(title);
                    Picasso.with(getContext()).load(img_url).fit().placeholder(R.drawable.entertainment).into(viewHolder.iv_track_image);
                }
            }
        } catch (NullPointerException e) {
            Log.e("Null_CHeck", "" + e);
        }


        return convertView;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}

/*    public boolean checkFavItem(Explore_model_item checkfav) {
        boolean check = false;
        //   List<Inicio_station> recents = RadioUtil.arr_recentstations;

        List<Explore_model_item> favs = Player.fav_adapter_data;
        if (favs != null) {
            for (Explore_model_item station_data : favs) {
                if (station_data.equals(checkfav)) {
                    check = true;
                    System.out.println("-----fav check true---" + check);
                    break;
                }
            }
        }
        System.out.println("-----fav check false---" + check);
        return check;
    }*/

