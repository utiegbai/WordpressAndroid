package com.entpress.entpress.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.entpress.entpress.BottomSheetFragment;
import com.entpress.entpress.R;
import com.entpress.entpress.interfaces.VolleyResponse;
import com.entpress.entpress.models.Posts;
import com.entpress.entpress.utility.api.ApiCall;
import com.entpress.entpress.utility.api.ApiUrl;
import com.entpress.entpress.utility.views.ShowAlert;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

/**
 * Created by utimac on 22/07/2018.
 */

public class SinglePostActivity extends AppCompatActivity implements View.OnClickListener, ViewPagerEx.OnPageChangeListener {
    ProgressBar progressBarInitial;
    public static String RECIEVER_DATA = "com.entpress.entpress";
    public RelativeLayout pDialog, call_tap, map_tap, map_container, mail_tap;
    ScrollView post_scrollview;
    TextView bar_post_title, no_posts, txt_title, txt_date, txt_description;
    private int tab_position = 0;
    private LinearLayout toolbar_two;
    ImageView icon_more, post_gal_img, image_icon_more;
    ProgressBar progress_bar;
    Toolbar toolbar;
    CharSequence categoryTitle;
    String intPostId, intPostTitle;
    WebView webview;
    int position;
    String post_Id;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_post_activity);

        Window window = SinglePostActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(SinglePostActivity.this.getResources().getColor(R.color.material_purple_800));
        }
        initDeclare();
        intentData();
        getPostDetails(Integer.valueOf(post_Id));

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

    public void showBottomSheetDialogFragment() {
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    private void intentData() {
        if (getIntent().hasExtra("post_id")) {
            intPostId = (String) getIntent().getExtras().getString("post_id");
            intPostTitle = (String) getIntent().getExtras().getString("post_title");
            position = getIntent().getIntExtra("position", 0);
            post_Id = intPostId;
            Log.e("PostId result",intPostTitle);
        }
    }

    private void initDeclare() {
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        no_posts = (TextView) findViewById(R.id.no_posts);
        txt_date = (TextView) findViewById(R.id.txt_date);
        webview = (WebView) findViewById(R.id.webview);
        toolbar_two = (LinearLayout) findViewById(R.id.toolbar_two);
        txt_title = (TextView) findViewById(R.id.txt_title);
        bar_post_title = (TextView) findViewById(R.id.bar_post_title);
        post_gal_img = (ImageView) findViewById(R.id.post_gal_img);
        image_icon_more = (ImageView) findViewById(R.id.image_icon_more);
        progressBarInitial = (ProgressBar) findViewById(R.id.progressBarInitial);
        post_scrollview = (ScrollView) findViewById(R.id.post_scrollview);
        icon_more = (ImageView) findViewById(R.id.icon_more);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        icon_more.setOnClickListener(this);
        image_icon_more.setOnClickListener(this);

        //toolbar_two.setBackgroundColor(Color.TRANSPARENT);
    }

    private void getPostDetails(int postId) {

        new ApiCall().get(new VolleyResponse() {
            @Override
            public void onSuccess(String result) {

                Log.e("getPostDetails result",result);
                try {
                    JSONObject response = new JSONObject(result);
                    JSONArray array = response.getJSONArray("posts");
                    int Json_msg = response.getInt("success");
                    if (Json_msg != 0) {
                        if (array != null) {
                            for (int i = 0; i < array.length(); i++) {
                               final Posts model = new Posts();

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

                                bar_post_title.setText(model.getPostTitle());
                                txt_date.setText(model.getPostDate());
                                webview.loadDataWithBaseURL(null, model.getPostContent(), "text/html", "UTF-8", null);
                                webview.animate();
                                txt_title.setText(model.getPostTitle());
                               Picasso.with(SinglePostActivity.this).load(model.getThumbnail()).fit().placeholder(R.drawable.entertainment).into(post_gal_img);


                            }

                        } else {
                            progress_bar.setVisibility(View.GONE);
                        }
                    } else {
                        progress_bar.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("GetPostDetails request",e.getMessage());
                }
            }

            @Override
            public void requestError(VolleyError error) {
                ShowAlert.Alert(SinglePostActivity.this, "Error", "Connection Error", null, "Ok");
            }
        }, ApiUrl.SINGLE_POST_URL+postId);
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
       // mSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public void onClick(View v) {
        if (v == icon_more) {
            showBottomSheetDialogFragment();
        } else if (v == image_icon_more) {
            showBottomSheetDialogFragment();
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
