package dominio.mi.restaurant.network.managers;

import java.util.List;

import dominio.mi.restaurant.models.CategoryIntermedyModel;
import dominio.mi.restaurant.models.RestaurantIntermedyModel;
import dominio.mi.restaurant.models.RestaurantModel;

/**
 * Created by Dumevi Cruces on 04/10/17.
 */

public class RestaurantManagerCallbacks {
    public interface OnGetCategories {
        void onGetCategoriesSuccess(List<CategoryIntermedyModel> categoryModelList);

        void onGetCategoriesError(Exception e);
    }

    public interface OnGetRestaurants {
        void onGetRestaurantsSuccess(List<RestaurantIntermedyModel> restaurantModelList);

        void onGetRestaurantsError(Exception e);
    }

    public interface OnGetRestaurantsSearch {
        void onGetRestaurantsSearchSuccess(List<RestaurantIntermedyModel> restaurantModelList);

        void onGetRestaurantsSearchError(Exception e);
    }

    public interface OnGetRestaurant {
        void onGetRestaurantSuccess(RestaurantModel restaurantModel);

        void onGetRestaurantError(Exception e);
    }
}
