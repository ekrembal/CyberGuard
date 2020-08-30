package com.example.safesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MyReceiver extends BroadcastReceiver {
    private static final String SMS_RECIEVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    String msg, phoneNo = "", sonuc = "";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i(TAG, "Intent Received:" + intent.getAction());
        if(intent.getAction()==SMS_RECIEVED)
        {
            Bundle dataBundle = intent.getExtras();
            if(dataBundle != null){
                Object[] mypdu = (Object[])dataBundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[mypdu.length];
                for (int i = 0; i < mypdu.length; i++){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        String format = dataBundle.getString("format");
                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i], format);
                    } else{
                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i]);
                    }
                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getOriginatingAddress();
                }


                RequestQueue queue = Volley.newRequestQueue(context);

                final String url ="http://10.0.2.2:5000/nlp/" + msg;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                if(response.equals("1")){
                                    if (MainActivity.getInst() != null)
                                        MainActivity.getInst().updateUI("Merhaba, size gönderilen son mesaj:\n" + msg + "\nyapay zeka algoritmamız tarafından taciz içerikli olabileceği tespit edilmiştir. Eğer bu taciz sebebiyle kendinizi kötü hissediyorsanız, gönüllü psikologumuz Özge Hurma ile iletişime geçebilirsiniz. Ayrıca suç duyurusunda bulunmak isterseniz https://www.istanbulbarosu.org.tr/ adresinden davayı başlatabilirsiniz.");
//                                    Toast.makeText(context, "Merhaba, şuan saldırı altındasınız", Toast.LENGTH_LONG).show();
                                }
//                                Toast.makeText(context, "url:" + url +  "Message:" + response + "#", Toast.LENGTH_LONG).show();
//                                Toast.makeText(context, , Toast.LENGTH_LONG).show();
//                                textView.setText("Response is: "+ response.substring(0,500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "url:" + url +  " ERROR " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest);

//                Toast.makeText(context, "sikinti var" + sonuc, Toast.LENGTH_LONG).show();


            }
        }
    }
}
