package dominio.mi.restaurant.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dominio.mi.restaurant.R;
import dominio.mi.restaurant.models.RestaurantDetailsModel;
import dominio.mi.restaurant.models.RestaurantModel;
import dominio.mi.restaurant.network.managers.RestaurantManager;
import dominio.mi.restaurant.network.managers.RestaurantManagerCallbacks;
import dominio.mi.restaurant.ui.adapters.RestaurantDetailsAdapter;

/**
 * Created by Dumevi Cruces on 21/07/17.
 */

public class RestaurantDetailsFragment extends Fragment {
    private List<RestaurantDetailsModel> restaurantModels;
    private RestaurantDetailsAdapter restaurantDetailsAdapter;
    private OnLoadingActions onLoadingActions;
    private RestaurantManager restaurantManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.fragment_restaurant_details, container, false);

        restaurantManager = new RestaurantManager();

        setRecyclerViews(viewGroup);
        getRestaurantData();

        return viewGroup;
    }

    private void setRecyclerViews(ViewGroup viewGroup) {
        RecyclerView recyclerRestaurantDetails = viewGroup.findViewById(R.id.recycler_restaurant_details);
        restaurantModels = new ArrayList<>();
        restaurantDetailsAdapter = new RestaurantDetailsAdapter(restaurantModels, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerRestaurantDetails.setLayoutManager(layoutManager);
        recyclerRestaurantDetails.setAdapter(restaurantDetailsAdapter);
    }

    private void getRestaurantData() {
        restaurantManager.getRestaurant(getArguments().getInt("id"),
                new RestaurantManagerCallbacks.OnGetRestaurant() {
                    @Override
                    public void onGetRestaurantSuccess(RestaurantModel restaurantModel) {
                        int id = restaurantModel.getId();
                        restaurantModels.add(new RestaurantDetailsModel("Location",
                                restaurantModel.getLocation().getAddress(), 1, id));

                        String phone = restaurantModel.getPhoneNumber();
                        if (phone != null && !phone.isEmpty()) {
                            restaurantModels
                                    .add(new RestaurantDetailsModel("Phone number",
                                    phone, 2, id));
                        } else {
                            restaurantModels
                                    .add(new RestaurantDetailsModel("Phone number",
                                    "No phone number", 2, id));
                        }

                        restaurantModels.add(new RestaurantDetailsModel("Cuisine",
                                restaurantModel.getCuisines(), 3, id));

                        restaurantDetailsAdapter.notifyDataSetChanged();

                        if (onLoadingActions != null) {
                            onLoadingActions.onLoadingReady();
                        }
                    }

                    @Override
                    public void onGetRestaurantError(Exception e) {
                        Toast.makeText(getActivity(), "Sorry, an error occurred",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void setOnLoadingActions(OnLoadingActions onLoadingActions) {
        this.onLoadingActions = onLoadingActions;
    }

    public interface OnLoadingActions {
        void onLoadingReady();
    }

}
