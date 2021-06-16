package jonathan.jwork_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Activity untuk pendaftaran untuk sebuah Job
 * @author Jonathan
 * @version 1.0
 * @since 10 Juni 2021
 *
 *
 */
public class SelesaiJobActivity extends AppCompatActivity {
    //Adapter dan variable view untuk inisiasi pada layout
    TextView invoice_Id, staticJobseekerName, staticInvoiceDate, staticPaymentType, staticInvoiceStatus,  staticJobName, staticTotalFee, staticReferralCode, job_name;
    TextView jobseeker_name, invoice_date, payment_type, invoice_status, referral_code, job_fee, total_fee;
    Button cancel_button, finish_button;

    //Instance Variable
    String jobName, jobseekerName, invoiceDate, referralCode, paymentType;
    int jobseekerID, jobFee, totalFee, currentInvoiceId, adminFee, discount;

    /**
     * Method inisialisasi pembuatan layout activity_selesai_job.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_job);

        //Inisialisasi setiap component pada layout ke private variable instance yang ada
        invoice_Id = findViewById(R.id.invoice_Id);
        staticJobseekerName = findViewById(R.id.staticJobseekerName);
        staticJobName = findViewById(R.id.staticJobName);
        staticInvoiceDate = findViewById(R.id.staticInvoiceDate);
        staticInvoiceStatus = findViewById(R.id.staticInvoiceStatus);
        staticPaymentType = findViewById(R.id.staticPaymentType);
        staticReferralCode = findViewById(R.id.staticReferralCode);
        staticTotalFee = findViewById(R.id.staticTotalFee);
        jobseeker_name = findViewById(R.id.jobseeker_name);
        invoice_date = findViewById(R.id.invoice_date);
        invoice_status = findViewById(R.id.invoice_status);
        payment_type = findViewById(R.id.payment_type);
        referral_code = findViewById(R.id.referral_code);
        job_fee = findViewById(R.id.job_fee);
        total_fee = findViewById(R.id.total_fee);
        cancel_button = findViewById(R.id.cancel_button);
        finish_button = findViewById(R.id.finish_button);
        job_name = findViewById(R.id.job_name);

        //Menghilangkan semua komponen pada layout secara default
        invoice_Id.setText("No Invoice");
        staticJobseekerName.setVisibility(View.GONE);
        staticJobName.setVisibility(View.GONE);
        staticInvoiceDate.setVisibility(View.GONE);
        staticInvoiceStatus.setVisibility(View.GONE);
        staticPaymentType.setVisibility(View.GONE);
        staticReferralCode.setVisibility(View.GONE);
        staticTotalFee.setVisibility(View.GONE);
        jobseeker_name.setVisibility(View.GONE);
        invoice_date.setVisibility(View.GONE);
        invoice_status.setVisibility(View.GONE);
        payment_type.setVisibility(View.GONE);
        referral_code.setVisibility(View.GONE);
        job_fee.setVisibility(View.GONE);
        job_name.setVisibility(View.GONE);
        total_fee.setVisibility(View.GONE);
        cancel_button.setVisibility(View.GONE);
        finish_button.setVisibility(View.GONE);

        //Proses pengambilan intent dari MainActivity
        Intent intent = getIntent();
        //Pengambilan extras dari mainActivity
        jobseekerID = intent.getIntExtra("jobseekerID", 0);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fetchJob();
            }
        });

        //Method saat button Cancel diklik
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(SelesaiJobActivity.this, "This invoice is cancelled", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SelesaiJobActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("jobseekerID", jobseekerID);
                                intent.putExtra("jobseekerName", jobseekerName);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiJobActivity.this);
                            builder.setMessage("Please try again").create().show();
                        }
                    }
                };

                JobBatalRequest request = new JobBatalRequest(String.valueOf(currentInvoiceId), "Cancelled", responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
                queue.add(request);
            }
        });

        //Method saat button Finish diklik
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(SelesaiJobActivity.this, "This invoice is finished", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SelesaiJobActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("jobseekerID", jobseekerID);
                                intent.putExtra("jobseekerName", jobseekerName);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiJobActivity.this);
                            builder.setMessage("Operation Failed! Please try again").create().show();
                        }
                    }
                };
                JobSelesaiRequest request = new JobSelesaiRequest(String.valueOf(currentInvoiceId), "Finished", responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
                queue.add(request);
            }
        });


    }

    public void fetchJob() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse != null) {
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject invoice = jsonResponse.getJSONObject(i);
                            JSONArray jobs = invoice.getJSONArray("jobs");
                            String invoiceStatus = invoice.getString("invoiceStatus");
                            currentInvoiceId = invoice.getInt("id");

                            if (invoiceStatus.equals("Ongoing")) {
                                for (int j = 0; j < jobs.length(); j++) {
                                    JSONObject job = jobs.getJSONObject(j);
                                    jobName = job.getString("name");
                                    jobFee = job.getInt("fee");
                                    job_name.setText(jobName);
                                    job_fee.setText("" + jobFee);
                                }

                                staticJobseekerName.setVisibility(View.VISIBLE);
                                staticInvoiceDate.setVisibility(View.VISIBLE);
                                staticJobName.setVisibility(View.VISIBLE);
                                staticInvoiceStatus.setVisibility(View.VISIBLE);
                                staticPaymentType.setVisibility(View.VISIBLE);
                                staticTotalFee.setVisibility(View.VISIBLE);
                                jobseeker_name.setVisibility(View.VISIBLE);
                                invoice_date.setVisibility(View.VISIBLE);
                                payment_type.setVisibility(View.VISIBLE);
                                invoice_status.setVisibility(View.VISIBLE);
                                job_name.setVisibility(View.VISIBLE);
                                job_fee.setVisibility(View.VISIBLE);
                                total_fee.setVisibility(View.VISIBLE);
                                cancel_button.setVisibility(View.VISIBLE);
                                finish_button.setVisibility(View.VISIBLE);

                                invoice_Id.setText("Invoice ID: " + currentInvoiceId);
                                jobseeker_name.setText(jobseekerName);
                                invoice_status.setText(invoiceStatus);

                                invoiceDate = invoice.getString("date");
                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);
                                Date date = inputFormat.parse(invoiceDate);
                                Locale indonesia = new Locale("in", "ID");
                                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", indonesia);
                                invoiceDate = outputFormat.format(date);

                                invoice_date.setText(invoiceDate);
                                paymentType = invoice.getString("paymentType");
                                payment_type.setText(paymentType);
                                totalFee = invoice.getInt("totalFee");
                                total_fee.setText("" + totalFee);

                                switch (paymentType) {
                                    case "BankPayment":
                                        adminFee = invoice.getInt("adminFee");
                                        referral_code.setVisibility(View.GONE);
                                        staticReferralCode.setVisibility(View.GONE);
                                        break;
                                    case "EwalletPayment":
                                        JSONObject bonus= invoice.getJSONObject("bonus");
                                        referralCode = bonus.getString("referralCode");
                                        if(bonus.isNull("referralCode")) {
                                            referral_code.setVisibility(View.GONE);
                                            staticReferralCode.setVisibility(View.GONE);
                                        } else{
                                            discount = bonus.getInt("extraFee");
                                            referral_code.setVisibility(View.VISIBLE);
                                            staticReferralCode.setVisibility(View.VISIBLE);
                                            referral_code.setText(referralCode);
                                        }
                                        break;
                                }
                            } else {
                                invoice_Id.setText("No Invoice");
                                staticJobseekerName.setVisibility(View.GONE);
                                staticJobName.setVisibility(View.GONE);
                                staticInvoiceDate.setVisibility(View.GONE);
                                staticInvoiceStatus.setVisibility(View.GONE);
                                staticPaymentType.setVisibility(View.GONE);
                                staticReferralCode.setVisibility(View.GONE);
                                staticTotalFee.setVisibility(View.GONE);
                                jobseeker_name.setVisibility(View.GONE);
                                invoice_date.setVisibility(View.GONE);
                                invoice_status.setVisibility(View.GONE);
                                payment_type.setVisibility(View.GONE);
                                referral_code.setVisibility(View.GONE);
                                job_fee.setVisibility(View.GONE);
                                job_name.setVisibility(View.GONE);
                                total_fee.setVisibility(View.GONE);
                                cancel_button.setVisibility(View.GONE);
                                finish_button.setVisibility(View.GONE);

                            }
                        }

                    }
                } catch (JSONException | ParseException e) {
                    Toast.makeText(SelesaiJobActivity.this, ""+currentInvoiceId, Toast.LENGTH_LONG).show();
                }
            }
        };

        JobFetchRequest request = new JobFetchRequest(Integer.toString(jobseekerID), responseListener);
        RequestQueue queue = new Volley().newRequestQueue(SelesaiJobActivity.this);
        queue.add(request);
    }
}