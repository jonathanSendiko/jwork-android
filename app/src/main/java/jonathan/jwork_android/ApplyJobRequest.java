package jonathan.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk membuat struktur request untuk invoice yang akan dikirim ke server
 * menggunakkan Volley Request
 * @author Jonathan
 * @version 1.0
 * @since 10 Juni 2021
 */
public class ApplyJobRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice";
    private Map<String, String> params;

    /**
     * Constructor Request pembuatan Invoice dengan Bank Payment
     * @param job
     * @param id
     * @param url
     * @param listener
     */
    public ApplyJobRequest(String job, String id, String url, Response.Listener<String> listener){
        super(Method.POST, URL + url, listener, null);
        params = new HashMap<>();
        params.put("jobIdList", job);
        params.put("jobseekerId", id);
        params.put("adminFee", "0");
    }

    /**
     * Constructor Request pembuatan Invoice dengan E Wallet Payment
     * @param jobIdList
     * @param jobseekerId
     * @param referralCode
     * @param listener
     * @param url
     */
    public ApplyJobRequest(String jobIdList, String jobseekerId, String referralCode, Response.Listener<String> listener, String url){
        super(Method.POST, URL + url, listener, null);
        params = new HashMap<>();
        params.put("jobIdList", jobIdList);
        params.put("jobseekerId", jobseekerId);
        params.put("referralCode", referralCode);
    }

    /**
     * Mendaoatkan params yang telah diput di konstruktor
     * @return
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
