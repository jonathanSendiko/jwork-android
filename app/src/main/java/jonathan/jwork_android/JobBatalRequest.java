package jonathan.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class yang dipanggil untuk membatalkan status invoice
 * menggunakkan Volley Request
 * @author Jonathan
 * @version 1.0
 * @since 10 Juni 2021
 */
public class JobBatalRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/invoiceStatus/";
    private Map<String, String> params;

    /**
     * Constructor untuk melakukan pengiriman request edit status ke server
     * @param id id Job
     * @param status status yang ingin dikirim yaitu status batal
     * @param listener bentuk Volley request yang aan dikirim ke URL
     */
    public JobBatalRequest(String id, String status, Response.Listener<String> listener) {
        super(Method.PUT, URL + id, listener, null);
        params = new HashMap<>();
        params.put("id",id);
        params.put("status", status);
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