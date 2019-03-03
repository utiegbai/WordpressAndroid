package com.entpress.entpress.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.entpress.entpress.Adapters.Category_Posts_Adapter;
import com.entpress.entpress.Adapters.Explore_Posts_Adapter;
import com.entpress.entpress.R;
import com.entpress.entpress.interfaces.VolleyResponse;
import com.entpress.entpress.models.Posts;
import com.entpress.entpress.utility.Util;
import com.entpress.entpress.utility.api.ApiCall;
import com.entpress.entpress.utility.api.ApiUrl;
import com.entpress.entpress.utility.views.ShowAlert;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

/**
 * Created by utimac on 22/07/2018.
 */

public class CategoryPostsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{
    GridView listview_tracks;
    ProgressBar progressBarInitial;
    public static String RECIEVER_DATA = "com.entpress.entpress";
    public RelativeLayout pDialog;
    TextView no_posts;
    private Category_Posts_Adapter list_Adapter;
    private int tab_position = 0;
    private LinearLayout bottom_holder;
    ProgressBar progress_bar;
    Menu cat_list_item;
    Toolbar toolbar;
    int cat_icon;
    CharSequence categoryTitle;
    int categoryId;
    public static ArrayList<Posts> adapter_data = new ArrayList<>();
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_post);

        if (getIntent().hasExtra("cat_id")) {
          categoryId = getIntent().getExtras().getInt("cat_id");
          categoryTitle = getIntent().getExtras().getCharSequence("cat_title");
        } else {
            finish();
        }

        initDeclare();
        getCatPosts(categoryId);

        mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("A306631BFD9E34B6E0535BC948742BF8")
                .build();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest);
    }

    private void initDeclare() {
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        no_posts = (TextView) findViewById(R.id.no_posts);
        progressBarInitial = (ProgressBar) findViewById(R.id.progressBarInitial);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listview_tracks = (GridView) findViewById(R.id.list_view_tracks);
        listview_tracks.setOnItemClickListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(categoryTitle);
        toolbar.setTitleTextColor(getResources().getColor(R.color.material_deep_purple_800));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
    }

    private void getCatPosts(int catId) {

        new ApiCall().get(new VolleyResponse() {
            @Override
            public void onSuccess(String result) {

                Log.e("getCatPosts result",result);
                try {
                    CategoryPostsActivity.adapter_data.clear();
                    JSONObject response = new JSONObject(result);
                    JSONArray array = response.getJSONArray("posts");
                    int Json_msg = response.getInt("success");
                    if (Json_msg != 0) {
                        if (array != null) {
                            for (int i = 0; i < array.length(); i++) {
                                Posts model = new Posts();

                                JSONObject obj = array.getJSONObject(i);
                                model.setId(obj.getString("ID"));
                                model.setPostAuthor(obj.getString("post_author"));
                                model.setPostTitle(obj.getString("post_title"));
                                model.setPostContent(obj.getString("post_content"));
                                model.setThumbnail(ApiUrl.FILE_PATH_URL+obj.getString("thumbnail"));
                                model.setPostType(obj.getString("post_type"));
                                model.setPostType(obj.getString("post_type"));
                                model.setCategoryName(obj.getString("category_name"));
                                model.setCategoryId(obj.getString("category_id"));
                                model.setCategorySlug(obj.getString("category_slug"));
                                model.setPostContentFiltered(obj.getString("post_content_filtered"));
                                model.setPostDate(obj.getString("post_date"));
                                model.setCommentCount(obj.getString("comment_count"));
                                model.setGuid(obj.getString("guid"));

                                Log.e("IMG URL", model.getThumbnail());

                                if (!model.getPostTitle().equalsIgnoreCase("")) {
                                    CategoryPostsActivity.adapter_data.add(model);
                                }
                                list_Adapter = new Category_Posts_Adapter(CategoryPostsActivity.this, CategoryPostsActivity.adapter_data, 0, false);

                                listview_tracks.setVisibility(View.VISIBLE);
                                progress_bar.setVisibility(View.GONE);
                                list_Adapter.setData(CategoryPostsActivity.adapter_data);

                                listview_tracks.setAdapter(list_Adapter);
                            }

                        } else {
                            listview_tracks.setVisibility(View.GONE);
                            progress_bar.setVisibility(View.GONE);
                            no_posts.setVisibility(View.VISIBLE);
                        }
                    } else {
                        listview_tracks.setVisibility(View.GONE);
                        progress_bar.setVisibility(View.GONE);
                        no_posts.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("getRecordings request",e.getMessage());
                }
            }

            @Override
            public void requestError(VolleyError error) {
                // progressBarInitial.setVisibility(View.INVISIBLE);
                ShowAlert.Alert(CategoryPostsActivity.this, "Error", "Connection Error", null, "Ok");
            }
        }, ApiUrl.CAT_POSTS_URL+catId);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object o = listview_tracks.getItemAtPosition(position);
        try {
            Posts post_detail = (Posts) o;
            String pid = post_detail.getId();

            Intent i = new Intent(CategoryPostsActivity.this, SinglePostActivity.class);
            i.putExtra("position", position);
            i.putExtra("post_id", pid);
            i.putExtra("post_title", post_detail.getPostTitle());
            startActivity(i);
            Log.e("PostDetails", "" + pid);
        } catch (NullPointerException | ClassCastException e) {
            Log.e("Null_noti", "" + e);
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
