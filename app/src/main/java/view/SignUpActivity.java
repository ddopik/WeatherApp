package view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddopikmain.seedapplication.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import presenter.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.UserName)
    EditText UserName;
    @BindView(R.id.input_name)
    EditText input_name;
    @BindView(R.id.input_email)
    EditText input_email;
    @BindView(R.id.input_mobile)
    EditText input_mobile;
    @BindView(R.id.input_password)
    EditText input_password;
    @BindView(R.id.radioM)
    RadioButton radioM;
    @BindView(R.id.radioF)
    RadioButton radioF;
    @BindView(R.id.btn_signup)
    AppCompatButton btn_signup;
    @BindView(R.id.link_login)
    TextView link_login;

    private Map<String, String> signUpHashMap;
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);



    }

    @OnClick(R.id.btn_signup)
    public void setBtn_signup() {

        if (validateFields()) {
            signUpHashMap = new HashMap<String, String>();
            signUpHashMap.put("name", input_name.getText().toString());
            signUpHashMap.put("username", UserName.getText().toString());
            signUpHashMap.put("input_password", input_password.getText().toString());
            signUpHashMap.put("input_email", input_email.getText().toString());
            signUpHashMap.put("mobile ", input_mobile.getText().toString());
            if (radioM.equals(true))
                signUpHashMap.put("gender", radioM.getText().toString());
            else {
                signUpHashMap.put("gender", "false");
            }

            signUpPresenter=new SignUpPresenter(this,signUpHashMap);
            signUpPresenter.sentRequest(false);
        } else {
            Toast.makeText(this, "Errror---", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean validateFields() {

        if (UserName.getText().toString().equals("")) {
            UserName.setError(getString(R.string.error_field_required));
            return false;
        } else if (input_name.getText().toString().equals("")) {
            input_name.setError(getString(R.string.error_field_required));
            return false;
        }  else if (input_email.getText().toString().equals("")) {
            input_email.setError(getString(R.string.error_field_required));
            return false;
        } else if (input_password.getText().toString().equals("")) {
            input_password.setError(getString(R.string.error_field_required));
            return false;
        } else if (input_mobile.getText().toString().equals("")) {
            input_mobile.setError(getString(R.string.error_field_required));
            return false;
        }

        return true;


    }
}