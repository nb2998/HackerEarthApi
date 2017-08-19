package com.apps.nishtha.hackerrankapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hackerrank.api.client.ApiException;
import com.hackerrank.api.hackerrank.api.CheckerApi;
import com.hackerrank.api.hackerrank.model.Submission;

public class MainActivity extends AppCompatActivity {

    TextView textOutput,textSample;
    EditText editSourceCode;
    Button btnRun,btnTrySample;
    Submission response;
    StringBuilder output;

    public static final String TAG="TAG";
    StringBuilder baseUrl=new StringBuilder();
    String apiKey="73e9886c06c02caaaf3e61439fdb9d114874aae8";
    String sourceCode="import java.io.*;\n" +
            "import java.util.*;\n" +
            "import java.text.*;\n" +
            "import java.math.*;\n" +
            "import java.util.regex.*;\n" +
            "\n" +
            "public class Solution {\n" +
            "\n" +
            "    public static void main(String[] args) {\n" +
            "        Scanner in = new Scanner(System.in);\n" +
            "        int n = in.nextInt();\n" +
            "        \n" +
            "        System.out.println(n);\n" +
            "    }\n" +
            "}\n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textOutput= (TextView) findViewById(R.id.textOutput);
        editSourceCode= (EditText) findViewById(R.id.editSourceCode);
        btnRun= (Button) findViewById(R.id.btnRun);
        btnTrySample= (Button) findViewById(R.id.btnTrySample);

//        baseUrl.append("http://api.hackerrank.com/checker/submission.json?");
//        baseUrl.append("source=print_1&lang=5&testcases=");
//        baseUrl.append("[\"1\"]");
//        baseUrl.append("&api_key=hackerrank|1715963-1798|73e9886c06c02caaaf3e61439fdb9d114874aae8");
//        Log.d(TAG, "onCreate: "+baseUrl);

        final String apiKey = "hackerrank|1715963-1798|73e9886c06c02caaaf3e61439fdb9d114874aae8";
        final String source = "import java.io.*;\n" +
                "import java.util.*;\n" +
                "import java.text.*;\n" +
                "import java.math.*;\n" +
                "import java.util.regex.*;\n" +
                "\n" +
                "public class Solution {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner in = new Scanner(System.in);\n" +
                "        int n = in.nextInt();\n" +
                "        \n" +
                "        System.out.println(n);\n" +
                "    }\n" +
                "}\n";


        final Integer lang = new Integer(3);
        final String testcases = "[\"1\", \"2\"]";
        final String format = "JSON";
        final String callbackUrl = "https://testing.com/response/handler";
        final String wait = "true";

        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CheckerApi checkerApi = new CheckerApi();

                    response = checkerApi.submission(apiKey, editSourceCode.getText().toString(), lang, testcases, format, callbackUrl, wait);
                    Log.d(TAG, "run: inside run");
                    Log.d(TAG, "run: "+response.getResult());
                    Log.d(TAG, "run: "+response.toString());
                    output=new StringBuilder();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: "+response.getResult().getCompilemessage().length());
                            if((response.getResult().getCompilemessage().length()!=0)){
                                textOutput.setText("Error!  "+response.getResult().getCompilemessage());
                            }else{
                                for(int i=0; i<response.getResult().getStdout().size(); i++){
                                    output.append(response.getResult().getStdout().get(i)+" ");
                                }
                                textOutput.setText(output);
                            Log.d(TAG, "run: ****"+response.getResult().getCompilemessage()+"****");
                            }
                        }
                    });

                } catch (ApiException e) {
                    Log.d(TAG, "run: inside catch");
                    System.out.printf("ApiException caught: %s\n", e.getMessage());
                }
            }
        });

        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.start();
            }
        });

        btnTrySample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSourceCode.setText(source);
                thread.start();
            }
        });

    }
}
