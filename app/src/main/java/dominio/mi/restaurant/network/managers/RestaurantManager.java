package dominio.mi.restaurant.network.managers;

import dominio.mi.restaurant.Utils;
import dominio.mi.restaurant.models.CategoryModel;
import dominio.mi.restaurant.models.RestaurantListModel;
import dominio.mi.restaurant.models.RestaurantModel;
import dominio.mi.restaurant.network.RestaurantRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Dumevi Cruces on 04/10/17.
 */

public class RestaurantManager {
    private RestaurantRequest restaurantRequest;

    public RestaurantManager() {
        Retrofit retrofitRestaurant = Utils.getRetrofit();
        restaurantRequest = retrofitRestaurant.create(RestaurantRequest.class);
    }

    public void getCategories(final RestaurantManagerCallbacks.OnGetCategories callback) {
        restaurantRequest.getCategories().enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                if (response.body().getCategory() != null) {
                    callback.onGetCategoriesSuccess(response.body().getCategory());
                } else {
                    callback.onGetCategoriesError(new Exception("Sorry, an error occurred"));
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                callback.onGetCategoriesError(new Exception(t));
            }
        });
    }

    public void getRestaurants(int page, int start, int category, final RestaurantManagerCallbacks.OnGetRestaurants callback) {
        restaurantRequest.getRestaurants(page, start, category).enqueue(new Callback<RestaurantListModel>() {
            @Override
            public void onResponse(Call<RestaurantListModel> call, Response<RestaurantListModel> response) {
                if (response.body().getRestaurantsModel() != null) {
                    callback.onGetRestaurantsSuccess(response.body().getRestaurantsModel());
                } else {
                    callback.onGetRestaurantsError(new Exception("Sorry, an error occurred"));
                }
            }

            @Override
            public void onFailure(Call<RestaurantListModel> call, Throwable t) {
                callback.onGetRestaurantsError(new Exception(t));
            }
        });
    }

    public void getRestaurantsSearch(int page, int start, int category, String query,
                                     final RestaurantManagerCallbacks.OnGetRestaurantsSearch callback) {
        restaurantRequest.getRestaurantSearch(page, start, category, query).enqueue(new Callback<RestaurantListModel>() {
            @Override
            public void onResponse(Call<RestaurantListModel> call, Response<RestaurantListModel> response) {
                if (response.body().getRestaurantsModel() != null) {
                    callback.onGetRestaurantsSearchSuccess(response.body().getRestaurantsModel());
                } else {
                    callback.onGetRestaurantsSearchError(new Exception("Sorry, an error occurred"));
                }
            }

            @Override
            public void onFailure(Call<RestaurantListModel> call, Throwable t) {
                callback.onGetRestaurantsSearchError(new Exception(t));
            }
        });
    }

    public void getRestaurant(int id, final RestaurantManagerCallbacks.OnGetRestaurant callback) {
        restaurantRequest.getRestaurant(id).enqueue(new Callback<RestaurantModel>() {
            @Override
            public void onResponse(Call<RestaurantModel> call, Response<RestaurantModel> response) {
                if (response.body() != null) {
                    callback.onGetRestaurantSuccess(response.body());
                } else {
                    callback.onGetRestaurantError(new Exception("Sorry, an error occurred"));
                }
            }

            @Override
            public void onFailure(Call<RestaurantModel> call, Throwable t) {
                callback.onGetRestaurantError(new Exception(t));
            }
        });
    }
}
