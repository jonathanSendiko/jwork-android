package jonathan.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Class untuk membuat struktur request untuk bonus yang akan dikirim ke server
 * menggunakkan Volley Request
 * @author Jonathan
 * @version 1.0
 * @since 10 Juni
 *
 */
public class BonusRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/bonus/";
    private Map<String, String> params;

    /**
     * Constructor untuk Request Bonus yang akan dikirimkan ke server
     * @param referralCode
     * @param listener
     */
    public BonusRequest(String referralCode, Response.Listener<String> listener) {
        super(Method.GET, URL+referralCode, listener, null);
    }
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
