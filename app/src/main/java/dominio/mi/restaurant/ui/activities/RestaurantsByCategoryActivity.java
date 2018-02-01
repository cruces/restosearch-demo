package dominio.mi.restaurant.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dominio.mi.restaurant.R;
import dominio.mi.restaurant.models.RestaurantIntermedyModel;
import dominio.mi.restaurant.network.managers.RestaurantManager;
import dominio.mi.restaurant.network.managers.RestaurantManagerCallbacks;
import dominio.mi.restaurant.ui.MyActivity;
import dominio.mi.restaurant.ui.adapters.RestaurantCategoryAdapter;

public class RestaurantsByCategoryActivity extends MyActivity {
    private RestaurantCategoryAdapter restaurantsAdapter;
    private int idCategory;
    private int numStart = 0;
    private int numGot = 0;
    public static final String CATEGORY_NAME = "categoryName";
    public static final String CATEGORY = "category";
    private boolean searchActive = false;
    private RestaurantsScrollListener restaurantsScrollListener;
    private String myQuerySearch;
    private RestaurantManager restaurantManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_by_category);

        getToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String categoryName = getIntent().getStringExtra(CATEGORY_NAME);

        if (categoryName != null && !categoryName.isEmpty())
            getSupportActionBar().setTitle(categoryName);

        restaurantManager = new RestaurantManager();

        idCategory = getIntent().getIntExtra(CATEGORY, 0);

        setRecyclerViews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryText),
                        PorterDuff.Mode.SRC_ATOP);
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            Handler handlerSearch = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    clearRecyclerView();
                    getRestaurantSearch(myQuerySearch);
                }
            };

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchActive = true;
                clearRecyclerView();
                getRestaurantSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchActive = true;
                myQuerySearch = newText;
                initDelay();

                return false;
            }

            private void initDelay() {
                handlerSearch.removeCallbacks(runnable);
                handlerSearch.postDelayed(runnable, 700);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchActive = false;
                clearRecyclerView();
                restaurantsAdapter.notifyDataSetChanged();
                getRestaurantsData();
                return false;
            }
        });

        return true;
    }

    private void clearRecyclerView() {
        numStart = 0;
        restaurantsScrollListener.setLastItems(0);
        restaurantsAdapter.clearRestaurantList();
    }

    private void getRestaurantSearch(String query) {
        numGot = 0;
        restaurantManager.getRestaurantsSearch(10, numStart, 1, query, new RestaurantManagerCallbacks.OnGetRestaurantsSearch() {
            @Override
            public void onGetRestaurantsSearchSuccess(List<RestaurantIntermedyModel> restaurantModelList) {
                restaurantsAdapter.loadListSearch(restaurantModelList, numStart);
                numGot = restaurantModelList.size();
            }

            @Override
            public void onGetRestaurantsSearchError(Exception e) {
                Toast.makeText(RestaurantsByCategoryActivity.this, ERROR_MESSAGE,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRestaurantsData() {
        numGot = 0;
        restaurantManager.getRestaurants(10, numStart, idCategory, new RestaurantManagerCallbacks.OnGetRestaurants() {
            @Override
            public void onGetRestaurantsSuccess(List<RestaurantIntermedyModel> restaurantModelList) {
                restaurantsAdapter.loadList(restaurantModelList);
                numGot = restaurantModelList.size();
            }

            @Override
            public void onGetRestaurantsError(Exception e) {
                Toast.makeText(RestaurantsByCategoryActivity.this, ERROR_MESSAGE,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerViews() {
        RecyclerView recyclerRestaurants = findViewById(R.id.recycler_restaurants);
        List<RestaurantIntermedyModel> restaurantList = new ArrayList<>();

        restaurantsAdapter = new RestaurantCategoryAdapter(restaurantList, this);
        GridLayoutManager gridLayoutManagerRestaurants = new GridLayoutManager(this, 2);
        gridLayoutManagerRestaurants.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (restaurantsAdapter != null) {
                    switch (restaurantsAdapter.getItemViewType(position)) {
                        case 1:
                            return 1;
                        case 2:
                            return 2;
                        default:
                            return -1;
                    }
                } else {
                    return -1;
                }
            }
        });
        recyclerRestaurants.setLayoutManager(gridLayoutManagerRestaurants);
        recyclerRestaurants.setAdapter(restaurantsAdapter);

        restaurantsScrollListener = new RestaurantsScrollListener();
        restaurantsScrollListener.setOnRestaurantScrollListenerActions(new RestaurantsScrollListener.OnRestaurantScrollListenerActions() {
            @Override
            public void loadMore() {
                numStart = numStart + numGot;
                if (searchActive) {
                    getRestaurantSearch(myQuerySearch);
                } else {
                    getRestaurantsData();
                }
            }
        });

        recyclerRestaurants.addOnScrollListener(restaurantsScrollListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_search:
                onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
