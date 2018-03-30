package dominio.mi.restaurant.ui.viewholders;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import dominio.mi.restaurant.R;

/**
 * Created by Dumevi Cruces on 26/01/18.
 */

public class FooterProgressBarViewHolderTest {
    private ProgressBar progressBar = Mockito.mock(ProgressBar.class);
    private TextView textView = Mockito.mock(TextView.class);
    private FooterProgressBarViewHolder viewholder;

    @Before
    public void init(){
        View v = Mockito.mock(View.class);
        Mockito.when(v.findViewById(R.id.progress_bar_footer))
                .thenReturn(progressBar);
        Mockito.when(v.findViewById(R.id.footer_no_matches))
                .thenReturn(textView);
        viewholder = new FooterProgressBarViewHolder(v);
    }

    @Test
    public void footerProgressBarViewHolder_CaseEqualZero(){
        viewholder.setFooter(0);
        Mockito.verify(progressBar).setVisibility(View.GONE);
        Mockito.verify(textView).setVisibility(View.VISIBLE);
    }

    @Test
    public void footerProgressBarViewHolder_CaseDifferentToZero(){
        viewholder.setFooter(1);
        Mockito.verify(progressBar).setVisibility(View.VISIBLE);
        Mockito.verify(textView).setVisibility(View.GONE);
    }
}
