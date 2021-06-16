package jonathan.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;



import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk membuat struktur request untuk jobseeker atau login
 * menggunakkan Volley Request
 * @author Jonathan
 * @version 1.0
 * @since 10 Juni
 *
 */
public class LoginRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8080/jobseeker/login";
    private Map<String, String> params;

    /**
     * Constructor untuk request login ke server
     * @param email
     * @param password
     * @param listener
     */
    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
