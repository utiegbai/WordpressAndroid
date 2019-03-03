package com.entpress.entpress.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.entpress.entpress.Adapters.Explore_Posts_Adapter;
import com.entpress.entpress.App;
import com.entpress.entpress.R;
import com.entpress.entpress.interfaces.VolleyResponse;
import com.entpress.entpress.models.Posts;
import com.entpress.entpress.utility.api.ApiCall;
import com.entpress.entpress.utility.api.ApiUrl;
import com.entpress.entpress.utility.views.ShowAlert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener, View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    GridView listview_tracks;
    ProgressBar progressBarInitial;
    public static String RECIEVER_DATA = "com.entpress.entpress";
    public RelativeLayout pDialog;
    private Explore_Posts_Adapter list_Adapter;
    private int tab_position = 0;
    private LinearLayout bottom_holder;
    ProgressBar progress_bar;
    Menu cat_list_item;
    int cat_icon;
    TextView price_txt;
    public static ArrayList<Posts> adapter_data = new ArrayList<>();
    private SliderLayout mSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.username);

        Log.e("TEXTVIEW", String.valueOf(username));

        initDeclare();
        getPosts();

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("one", "https://www.nigpro.com/wp-content/uploads/2018/08/banner1.jpg");
        url_maps.put("three", "https://www.nigpro.com/wp-content/uploads/2018/08/naijamade-2.jpg");
        url_maps.put("four", "https://www.nigpro.com/wp-content/uploads/2018/08/banner14.png");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(MainActivity.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mSlider.addSlider(textSliderView);
        }

        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(2000);
        mSlider.addOnPageChangeListener(this);

    }


    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    private void initDeclare() {

        //pDialog = (RelativeLayout) findViewById(R.id.progressBarHolder);
        bottom_holder = findViewById(R.id.bottom_holder);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBarInitial = (ProgressBar) findViewById(R.id.progressBarInitial);
        listview_tracks = (GridView) findViewById(R.id.list_view_tracks);
        mSlider = (SliderLayout)findViewById(R.id.slider);
        enableTabItem(R.id.tab_home);
        initAndEnableBottomBar();
        listview_tracks.setOnItemClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        cat_list_item = (Menu)findViewById(R.id.cat_list_item);
        navigationView.setNavigationItemSelectedListener(this);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Menu menu = navigationView.getMenu();

        getCategories(menu);

        NavigationView.OnNavigationItemSelectedListener item_click_listener = new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
               int item_id = item.getItemId();
               CharSequence item_title = item.getTitle();

                Intent intent = new Intent(getApplicationContext(), CategoryPostsActivity.class);
                intent.putExtra("cat_id", item_id);
                intent.putExtra("cat_title", item_title);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                drawerLayout.closeDrawers();
                return true;
            }
        };

        navigationView.setNavigationItemSelectedListener(item_click_listener);

        /**
         * Setting up Drawer Toggle for Toolbar.
         */

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        drawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }

    private void initAndEnableBottomBar(){
        final int childCount = bottom_holder.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View element = bottom_holder.getChildAt(i);

            // Relativelayout
            if (element instanceof RelativeLayout) {
                RelativeLayout el = (RelativeLayout) element;
                el.setOnClickListener(this);
            }

        }
    }

    private void enableTabItem(int id){
        final int childCount = bottom_holder.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View element = bottom_holder.getChildAt(i);

            // Relativelayout
            if (element instanceof RelativeLayout) {
                RelativeLayout el = (RelativeLayout) element;
                if(el.getId()!= R.id.tab_recorder) {
                    ImageView icon = (ImageView) el.getChildAt(0);
                    View line = (View) el.getChildAt(1);
                    if (el.getId() == id) {
                        el.setBackgroundColor(getResources().getColor(R.color.material_purple_800));
                        line.setVisibility(View.VISIBLE);
                        icon.setColorFilter(getResources().getColor(R.color.white));
                    } else {
                        el.setBackgroundColor(getResources().getColor(R.color.white));
                        line.setVisibility(View.GONE);
                        icon.setColorFilter(getResources().getColor(R.color.material_purple_800));
                    }
                }
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_home:
                if(tab_position!=0) {
                    enableTabItem(R.id.tab_home);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    tab_position=0;
                }
                break;
            case R.id.tab_search:
                if(tab_position!=1) {
                    enableTabItem(R.id.tab_search);
                    Intent inte = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(inte);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    tab_position=1;
                }
                break;
            case R.id.tab_recorder:
                enableTabItem(R.id.tab_recorder);
                //ShowAlert.Notify(MainActivity.this, "Error", "Hello "+loginUser.getName()+"! We'd recommend you to use our website for posting of Ad. Thank you!", "No", "Ok");
                tab_position=2;

                break;
            case R.id.tab_notification:
                if(tab_position!=3) {
                    enableTabItem(R.id.tab_notification);
                    Toast.makeText(getBaseContext(), "You don't have notification", Toast.LENGTH_LONG).show();
                    tab_position=3;
                }
                break;
            case R.id.tab_profile:
                if(tab_position!=4) {
                    enableTabItem(R.id.tab_profile);
                    tab_position=4;
                }
                break;
        }
    }

    private void getCategories(final Menu menu) {

        new ApiCall().get(new VolleyResponse() {
            @Override
            public void onSuccess(String result) {

                Log.e("categories result",result);
                try {
                    JSONObject response = new JSONObject(result);
                    JSONArray array = response.getJSONArray("categories");
                    if (array != null) {
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject obj = array.getJSONObject(i);
                            String cat_id = obj.getString("c_id");
                            String cat_name = obj.getString("c_title").toUpperCase();
                            //String cat_url = obj.getString("c_url");

                                cat_icon = R.drawable.ic_arrow_forward_black;

                            menu.add(0, Integer.parseInt(cat_id), Menu.FIRST, cat_name).setIcon(cat_icon);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Categories err",e.getMessage());
                }
            }

            @Override
            public void requestError(VolleyError error) {
                // progressBarInitial.setVisibility(View.INVISIBLE);
                ShowAlert.Alert(MainActivity.this, "Error", "Connection Error", null, "Ok");
            }
        }, ApiUrl.CAT_URL);
    }

    private void getPosts() {

        new ApiCall().get(new VolleyResponse() {
            @Override
            public void onSuccess(String result) {
                Posts model;

                Log.e("getAds result",result);
                try {
                    MainActivity.adapter_data.clear();
                    JSONObject response = new JSONObject(result);
                    JSONArray array = response.getJSONArray("posts");
                    if (array != null) {
                        for (int i = 0; i < array.length(); i++) {
                            model = new Posts();

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

                            //Log.e("IMG URL",model.getImages());

                            if (!model.getPostTitle().equalsIgnoreCase("")) {
                                MainActivity.adapter_data.add(model);
                            }
                            list_Adapter = new Explore_Posts_Adapter(getApplicationContext(), MainActivity.adapter_data, 0, false);

                            listview_tracks.setVisibility(View.VISIBLE);
                            progress_bar.setVisibility(View.GONE);
                            list_Adapter.setData(MainActivity.adapter_data);

                            listview_tracks.setAdapter(list_Adapter);
                        }

                    } else {
                        listview_tracks.setVisibility(View.GONE);
                        progress_bar.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("getAds request",e.getMessage());
                }
            }

            @Override
            public void requestError(VolleyError error) {
               // progressBarInitial.setVisibility(View.INVISIBLE);
                ShowAlert.Alert(MainActivity.this, "Error", "Connection Error", null, "Ok");
            }
        }, ApiUrl.ALLPOST_URL);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object o = listview_tracks.getItemAtPosition(position);
        try {
            Posts post_detail = (Posts) o;
            String pid = post_detail.getId();

            Intent i = new Intent(MainActivity.this, SinglePostActivity.class);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            //go to login activity
            //Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            //startActivity(intent);
            //finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


