package dominio.mi.restaurant.ui.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import dominio.mi.restaurant.R;
import dominio.mi.restaurant.models.RestaurantDetailsModel;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dumevi Cruces on 24/01/18.
 */

public class RestaurantDetailsViewHolderTest {
    private TextView restaurantLabel = Mockito.mock(TextView.class);
    private TextView restaurantText = Mockito.mock(TextView.class);
    private ImageView icon = Mockito.mock(ImageView.class);
    private ImageView iconDetails = Mockito.mock(ImageView.class);
    private RestaurantDetailsViewHolder restaurantDetailsViewHolder;
    private RestaurantDetailsModel model;

    @Before
    public void init() {
        model = Mockito.mock(RestaurantDetailsModel.class);
        View v = Mockito.mock(View.class);

        Mockito.when(v.findViewById(R.id.restaurant_label))
                .thenReturn(restaurantLabel);

        Mockito.when(v.findViewById(R.id.restaurant_text))
                .thenReturn(restaurantText);

        Mockito.when(v.findViewById(R.id.ic_action_view_location))
                .thenReturn(icon);

        Mockito.when(v.findViewById(R.id.icon_details))
                .thenReturn(iconDetails);

        restaurantDetailsViewHolder = new RestaurantDetailsViewHolder(v);
    }

    @Test
    public void restaurantDetailsViewHolder_setData() {
        ArgumentCaptor<CharSequence> argumentCaptor =
                ArgumentCaptor.forClass(CharSequence.class);
        ArgumentCaptor<CharSequence> argumentCaptor2 =
                ArgumentCaptor.forClass(CharSequence.class);

        Mockito.when(model.getRestaurantTitle()).thenReturn("title");
        Mockito.when(model.getGetRestaurantDescription()).thenReturn("description");
        Mockito.when(model.getType()).thenReturn(0);

        restaurantDetailsViewHolder.setData(model);

        Mockito.verify(restaurantLabel).setText(argumentCaptor.capture());
        Mockito.verify(restaurantText).setText(argumentCaptor2.capture());
        Mockito.verify(icon).setVisibility(View.GONE);
        Mockito.verify(iconDetails, Mockito.never())
                .setImageResource(Mockito.anyInt());


        assertEquals("title", argumentCaptor.getValue());
        assertEquals("description", argumentCaptor2.getValue());
    }

    @Test
    public void restaurantDetailsViewHolder_setData_CaseEqualToOne() {
        Mockito.when(model.getType()).thenReturn(1);

        restaurantDetailsViewHolder.setData(model);

        Mockito.verify(icon, Mockito.never()).setImageResource(Mockito.anyInt());
        Mockito.verify(iconDetails).setImageResource(R.drawable.ic_action_location);
        Mockito.verify(iconDetails, Mockito.never())
                .setImageResource(R.drawable.ic_action_phone);
        Mockito.verify(iconDetails, Mockito.never())
                .setImageResource(R.drawable.ic_action_cuisine);

    }

    @Test
    public void restaurantDetailsViewHolder_setData_CaseEqualToTwo(){
        Mockito.when(model.getType()).thenReturn(2);

        restaurantDetailsViewHolder.setData(model);

        Mockito.verify(icon, Mockito.never()).setImageResource(Mockito.anyInt());
        Mockito.verify(iconDetails, Mockito.never())
                .setImageResource(R.drawable.ic_action_location);
        Mockito.verify(iconDetails).setImageResource(R.drawable.ic_action_phone);
        Mockito.verify(iconDetails, Mockito.never())
                .setImageResource(R.drawable.ic_action_cuisine);
    }

    @Test
    public void restaurantDetailsViewHolder_setData_CaseEqualToThree(){
        Mockito.when(model.getType()).thenReturn(3);

        restaurantDetailsViewHolder.setData(model);

        Mockito.verify(icon, Mockito.never()).setImageResource(Mockito.anyInt());
        Mockito.verify(iconDetails, Mockito.never())
                .setImageResource(R.drawable.ic_action_location);
        Mockito.verify(iconDetails, Mockito.never())
                .setImageResource(R.drawable.ic_action_phone);
        Mockito.verify(iconDetails).setImageResource(R.drawable.ic_action_cuisine);
    }
}
