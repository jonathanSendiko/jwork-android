package jonathan.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity untuk melakukan login
 * @author Jonathan
 * @version 1.0
 * @since 10 Juni 2021
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Method inisialisasi pembuatan layout activity_login.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Inisialisasi view layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inisialisasi setiap component pada layout ke private variable instance yang ada
        EditText etEmail = findViewById(R.id.email_input);
        EditText etPassword = findViewById(R.id.password_input);
        Button btnLogin = findViewById(R.id.login_button);
        TextView tvRegister = findViewById(R.id.register_action);

        //Method saat button Login diklik
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                                loginIntent.putExtra("jobseekerID", jsonObject.getInt("id"));
                                loginIntent.putExtra("jobseekerName", jsonObject.getString("name"));

                                startActivity(loginIntent);
                                finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
                        }
                    }
                };


                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                System.out.println(loginRequest);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}