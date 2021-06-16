package jonathan.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Activity untuk pendaftaran untuk sebuah Job
 * @author Jonathan
 * @version 1.0
 * @since 10 Juni 2021
 *
 *
 */
public class ApplyJobActivity extends AppCompatActivity {
    //Private Variable untuk ApplyJobActivity
    private int jobseekerID;
    private int jobID ;
    private String jobName;
    private  String jobCategory;
    private double jobFee;
    private int bonus;
    private String selectedPayment;
    //Request Class untuk melakukan request ke server jwork
    ApplyJobRequest applyJobRequest;

    /**
     * Method inisialisasi pembuatan layout activity_apply_job.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Inisialisasi view layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        //Inisialisasi setiap component pada layout ke private variable instance yang ada
        TextView job_name =  findViewById(R.id.job_name);
        TextView job_category  = findViewById(R.id.job_category);
        TextView job_fee = findViewById(R.id.job_fee);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton ewallet = findViewById(R.id.ewallet);
        RadioButton bank = findViewById(R.id.bank);
        EditText referral_code = findViewById(R.id.referral_code);
        TextView total_fee = findViewById(R.id.total_fee);
        Button btnApply = findViewById(R.id.btnApply);
        Button hitung = findViewById(R.id.hitung);
        TextView textCode = findViewById(R.id.textCode);

        //Proses pengambilan intent dari MainActivity
        Intent intent = getIntent();
        //Pengambilan extras dari mainActivity
        jobseekerID = intent.getIntExtra("jobseekerID", 0);
        jobID = intent.getIntExtra("jobID", 0);
        jobName = intent.getStringExtra("jobName");
        jobCategory = intent.getStringExtra("jobCategory");
        jobFee = intent.getIntExtra("jobFee", 0);

        //Menghilangkan apply button dan referral code pada layout
        btnApply.setVisibility(View.GONE);
        textCode.setVisibility(View.GONE);
        referral_code.setText("");
        referral_code.setVisibility(View.GONE);

        /**
         * Set component job name, category, fee dan total fee sesuai dengan data yang telah
         * diambil dari Main Activity
         */
        job_name.setText(jobName);
        job_category.setText(jobCategory);
        DecimalFormat decimalFormat = new DecimalFormat("0.#");
        job_fee.setText(decimalFormat.format(jobFee));
        total_fee.setText(decimalFormat.format(jobFee));

        /**
         * Method saat radio button dipilih
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.ewallet:
                       textCode.setVisibility(View.VISIBLE);
                       referral_code.setVisibility(View.VISIBLE);
                       break;
                   case R.id.bank:
                       textCode.setVisibility(View.GONE);
                       referral_code.setVisibility(View.GONE);
                       break;
               }

            }
        });

        //Method saat button Count diklik
        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.bank:

                        hitung.setVisibility(View.GONE);
                        btnApply.setVisibility(View.VISIBLE);
                        break;
                    case R.id.ewallet:
                        if(referral_code.getText().toString().isEmpty()){

                            hitung.setVisibility(View.GONE);
                            btnApply.setVisibility(View.VISIBLE);
                        } else {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject != null) {
                                            if(jsonObject.getDouble("minTotalFee") <= Double.parseDouble(total_fee.getText().toString())){
                                                System.out.println(jsonObject.getDouble("minTotalFee") >= Double.parseDouble(total_fee.getText().toString()));
                                                total_fee.setText((decimalFormat.format(jobFee + jsonObject.getInt("extraFee"))));
                                                Toast.makeText(ApplyJobActivity.this, "Code Applied", Toast.LENGTH_SHORT).show();
                                                hitung.setVisibility(View.GONE);
                                                btnApply.setVisibility(View.VISIBLE);
                                            } else {
                                                total_fee.setText(decimalFormat.format(jobFee));
                                                Toast.makeText(ApplyJobActivity.this, "Min Total Fee is not fulfilled", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(ApplyJobActivity.this, "Code is Invalid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            BonusRequest bonusRequest = new BonusRequest(referral_code.getText().toString(), responseListener);
                            RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                            queue.add(bonusRequest);
                        }
                        break;
                }
            }
        });

        //Method saat button Apply diklik
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioButtonId);
                final String method = radioButton.getText().toString().trim();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null){
                                Toast.makeText(ApplyJobActivity.this, "Apply Job Successful", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            Toast.makeText(ApplyJobActivity.this, "Apply Job Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                switch (method) {
                    case "Bank":
                        applyJobRequest = new ApplyJobRequest(jobID + "" , jobseekerID + "", "/createBankPayment", responseListener);
                        break;
                    case "E-Wallet":
                        applyJobRequest = new ApplyJobRequest(jobID + "" , jobseekerID + "", referral_code.getText().toString(), responseListener, "/createEWalletPayment");
                        break;
                }
                RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                queue.add(applyJobRequest);

            }
        });
    }
}