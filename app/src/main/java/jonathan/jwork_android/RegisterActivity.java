package jonathan.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity untuk melakukan Register
 * @author Jonathan
 * @version 1.0
 * @since 10 Juni 2021
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * Method inisialisasi pembuatan layout activity_register.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Inisialisasi setiap component pada layout ke private variable instance yang ada
        EditText etName = findViewById(R.id.name_input);
        EditText etEmail = findViewById(R.id.email_input_register);
        EditText etPassword = findViewById(R.id.password_input_register);
        Button btnRegister = findViewById(R.id.register_button);
        //Method saat button Register di klik
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
//                          e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(name, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });
    }
}