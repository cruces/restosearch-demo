package dominio.mi.restaurant.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import dominio.mi.restaurant.R;
import dominio.mi.restaurant.Utils;
import dominio.mi.restaurant.ui.MyActivity;

public class RegisterActivity extends MyActivity implements DatePickerDialog.OnDateSetListener {

    private TextView dateTextView;
    private EditText userEmail;
    private EditText password;
    private EditText fullname;
    private EditText confirmPassword;
    private CheckBox terms;
    private static final int MIN_PASSWORD_LENGTH = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView textLogo = findViewById(R.id.logo_text_resto);
        textLogo.setTypeface(Utils.getFontsAcme(getAssets()));

        TextView textLogoSearch = findViewById(R.id.logo_text_search);
        textLogoSearch.setTypeface(Utils.getFontShadow(getAssets()));

        fullname = findViewById(R.id.fullname);
        userEmail = findViewById(R.id.email);

        password = findViewById(R.id.password);
        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());

        confirmPassword = findViewById(R.id.confirm_password);
        confirmPassword.setTypeface(Typeface.DEFAULT);
        confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
        terms = findViewById(R.id.terms);

        TextView registerText = findViewById(R.id.signin_button);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formRegister();
            }
        });

        TextView haveAccount = findViewById(R.id.have_account);
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        dateTextView = findViewById(R.id.bithdate);

        Calendar now = Calendar.getInstance();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                RegisterActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show(getFragmentManager(), "Datepickerdialog");
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            }
        });


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        dateTextView.setText(date);
    }

    private void formRegister() {
        String userFullNameForm = fullname.getText().toString();
        String userEmailForm = userEmail.getText().toString();
        String userPassword = password.getText().toString();
        String userPasswordConfirm = confirmPassword.getText().toString();

        if (TextUtils.isEmpty(userFullNameForm)) {
            toast(this.getString(R.string.empty_fullname));
        } else if (TextUtils.isEmpty(userEmailForm)) {
            toast(this.getString(R.string.empty_email));
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmailForm).matches()) {
            toast(this.getString(R.string.invalid_email));
        } else if (TextUtils.isEmpty(userPassword)) {
            toast(this.getString(R.string.empty_password));
        } else if (userPassword.length() < MIN_PASSWORD_LENGTH) {
            toast(this.getString(R.string.short_password));
        } else if (!userPasswordConfirm.equals(userPassword)) {
            toast(this.getString(R.string.same_password));
        } else if (!terms.isChecked()) {
            toast(this.getString(R.string.accept_terms_and_conditions));
        } else {
            startActivity(Utils.intentUserSharedPreferences(this,
                    CategoriesActivity.class, true));
        }
    }
}
