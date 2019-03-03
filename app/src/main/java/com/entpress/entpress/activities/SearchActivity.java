package com.entpress.entpress.activities;

        import android.animation.Animator;
        import android.animation.AnimatorListenerAdapter;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.annotation.RequiresApi;
        import android.support.v4.view.MenuItemCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.SearchView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewAnimationUtils;
        import android.widget.AdapterView;
        import android.widget.AutoCompleteTextView;
        import android.widget.EditText;
        import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.VolleyError;
        import com.entpress.entpress.Adapters.Category_Posts_Adapter;
        import com.entpress.entpress.R;
        import com.entpress.entpress.interfaces.VolleyResponse;
        import com.entpress.entpress.models.Posts;
        import com.entpress.entpress.utility.api.ApiCall;
        import com.entpress.entpress.utility.api.ApiUrl;
        import com.entpress.entpress.utility.views.ShowAlert;
        import com.google.android.gms.ads.AdListener;
        import com.google.android.gms.ads.AdRequest;
        import com.google.android.gms.ads.AdView;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.lang.reflect.Field;
        import java.util.ArrayList;

/**
 * Created by utimac on 22/07/2018.
 */

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{
    GridView listview_tracks;
    ProgressBar progressBarInitial;
    public static String RECIEVER_DATA = "com.entpress.entpress";
    public RelativeLayout pDialog;
    TextView no_posts;
    private Category_Posts_Adapter list_Adapter;
    private int tab_position = 0;
    private LinearLayout bottom_holder;
    ProgressBar progress_bar;
    int categoryId;
    public static ArrayList<Posts> adapter_data = new ArrayList<>();
    private AdView mAdView;

    Toolbar toolbar, searchtollbar;
    Menu search_menu;
    MenuItem item_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        initDeclare();

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
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_status:
                Toast.makeText(this, "Home Status Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_search:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.searchtoolbar,1,true,true);
                else
                    searchtollbar.setVisibility(View.VISIBLE);

                item_search.expandActionView();
                return true;
            case R.id.action_logout:
                Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void setSearchtollbar()
    {
        searchtollbar = (Toolbar) findViewById(R.id.searchtoolbar);
        if (searchtollbar != null) {
            searchtollbar.inflateMenu(R.menu.menu_search);
            search_menu=searchtollbar.getMenu();

            searchtollbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        circleReveal(R.id.searchtoolbar,1,true,false);
                    else
                        searchtollbar.setVisibility(View.GONE);
                }
            });

            item_search = search_menu.findItem(R.id.action_filter_search);

            MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(R.id.searchtoolbar,1,true,false);
                    }
                    else
                        searchtollbar.setVisibility(View.GONE);
                    return true;
                }

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // Do something when expanded
                    return true;
                }
            });

            initSearchView();


        } else
            Log.d("toolbar", "setSearchtollbar: NULL");
    }

    public void initSearchView()
    {
        final SearchView searchView =
                (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();

        // Enable/Disable Submit button in the keyboard

        searchView.setSubmitButtonEnabled(false);

        // Change search close button image

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.ic_cancel_black_24dp);
        closeButton.setColorFilter(getResources().getColor(R.color.colorPrimary));


        // set hint and the text colors

        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint("Search..");
        txtSearch.setHintTextColor(Color.DKGRAY);
        txtSearch.setTextColor(getResources().getColor(R.color.colorPrimary));


        // set the cursor

        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearch(newText);
                return true;
            }

            public void callSearch(String query) {
                //Do searching
                Log.i("query", "" + query);

            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow)
    {
        final View myView = findViewById(viewID);

        int width=myView.getWidth();

        if(posFromRight>0)
            width-=(posFromRight*getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material))-(getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)/ 2);
        if(containsOverflow)
            width-=getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx=width;
        int cy=myView.getHeight()/2;

        Animator anim;
        if(isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0,(float)width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float)width, 0);

        anim.setDuration((long)220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isShow)
                {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if(isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();


    }

    private void initDeclare() {
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        no_posts = (TextView) findViewById(R.id.no_posts);
        progressBarInitial = (ProgressBar) findViewById(R.id.progressBarInitial);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listview_tracks = (GridView) findViewById(R.id.list_view_tracks);
        listview_tracks.setOnItemClickListener(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Search Ads");
        setSearchtollbar();
        initSearchView();
    }

    private void getSearch(String q) {

        new ApiCall().get(new VolleyResponse() {
            @Override
            public void onSuccess(String result) {

                Log.e("searchpost result",result);
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
                                list_Adapter = new Category_Posts_Adapter(SearchActivity.this, CategoryPostsActivity.adapter_data, 0, false);

                                listview_tracks.setVisibility(View.VISIBLE);
                                progress_bar.setVisibility(View.GONE);
                                list_Adapter.setData(CategoryPostsActivity.adapter_data);
                                no_posts.setVisibility(View.GONE);
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
                    Log.e("searchpost request",e.getMessage());
                }
            }

            @Override
            public void requestError(VolleyError error) {
                // progressBarInitial.setVisibility(View.INVISIBLE);
                ShowAlert.Alert(SearchActivity.this, "Error", "Connection Error", null, "Ok");
            }
        }, ApiUrl.SEARCH_URL+q);
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

            Intent i = new Intent(SearchActivity.this, SinglePostActivity.class);
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
