package jonathan.jwork_android;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk membuat struktur request untuk Permintaan Job yang akan dikirim ke server
 * menggunakkan Volley Request
 * @author Jonathan
 * @version 1.0
 * @since 10 Juni 2021
 */
public class JobFetchRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/jobseeker/";
    private Map<String, String> params;

    /**
     * Constructor yang akan dilemparkan ke server untuk mengambil Job/Invoice
     * berdasarkan jobseekerId yang diberikan
     * @param jobseekerId
     * @param listener
     */
    public JobFetchRequest(String jobseekerId, Response.Listener<String> listener){
        super(Method.GET, URL+jobseekerId, listener, null);
        params = new HashMap<>();
    }

    /**
     * Mendapatkan params yang telah diput di konstruktor
     * @return
     * @throws AuthFailureError
     */
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
