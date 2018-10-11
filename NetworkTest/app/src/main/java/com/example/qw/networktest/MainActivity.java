package com.example.qw.networktest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView responseTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button pullsendRequest=(Button)findViewById(R.id.pull_send_request);
        Button saxsendRequest=(Button)findViewById(R.id.sax_send_request);
        Button jsonsendRequest=(Button)findViewById(R.id.json_send_request);
        Button gsonsendRequest=(Button)findViewById(R.id.gson_send_request);
        responseTest=(TextView)findViewById(R.id.response_text);
        pullsendRequest.setOnClickListener(this);
        saxsendRequest.setOnClickListener(this);
        jsonsendRequest.setOnClickListener(this);
        gsonsendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pull_send_request:
                pullSendRequestWithOkHttp();
                break;
            case R.id.sax_send_request:
                saxSendRequestWithOkHttp();
                break;
            case R.id.json_send_request:
                jsonSendRequestWithOkHttp();
                break;
            case R.id.gson_send_request:
                gsonSendRequestWithOkHttp();
                break;
        }
    }
    private void pullSendRequestWithOkHttp(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client=new OkHttpClient();
//                    Request request=new Request.Builder().url("http://10.0.2.2:8080/get_data.xml").build();
//                    Response response=client.newCall(request).execute();
//                    String responseData=response.body().string();
//                    parseXMLWithPull(responseData);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        HttpUtil.sendOkHttpRequest("http://10.0.2.2:8080/get_data.xml",new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                parseXMLWithPull(responseData);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void saxSendRequestWithOkHttp(){
                    HttpUtil.sendOkHttpRequest("http://10.0.2.2:8080/get_data.xml",new okhttp3.Callback(){
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseData=response.body().string();
                            parseXMLWithSAX(responseData);
                        }

                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }
                    });
    }

    private void jsonSendRequestWithOkHttp(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client=new OkHttpClient();
//                    Request request=new Request.Builder().url("http://10.0.2.2:8080/get_data.json").build();
//                    Response response=client.newCall(request).execute();
//                    String responseData=response.body().string();
//                    parseJSONWithJSONObject(responseData);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        HttpUtil.sendOkHttpRequest("http://10.0.2.2:8080/get_data.json", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                parseJSONWithJSONObject(responseData);
            }
        });
    }

    private void gsonSendRequestWithOkHttp(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client=new OkHttpClient();
//                    Request request=new Request.Builder().url("http://10.0.2.2:8080/get_data.json").build();
//                    Response response=client.newCall(request).execute();
//                    String responseData=response.body().string();
//                    parseJSONWithGSON(responseData);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        HttpUtil.sendOkHttpRequest("http://10.0.2.2:8080/get_data.json",new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                parseJSONWithGSON(responseData);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    //PULL方法解析xml数据
    private  void parseXMLWithPull(String xmlData){
        try{
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType=xmlPullParser.getEventType();
            String id="";
            String name="";
            String version="";
            while(eventType!= XmlPullParser.END_DOCUMENT){
                String nodeName=xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)){
                            id=xmlPullParser.nextText();
                        }else if("name".equals(nodeName)){
                            name=xmlPullParser.nextText();
                        }else if("version".equals(nodeName)){
                            version=xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        if("app".equals(nodeName)){
                            Log.d("MainActivity","id is"+id);
                            Log.d("MainActivity","name is"+name);
                            Log.d("MainActivity","version is"+version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType=xmlPullParser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //SAN方法解析xml数据
    private void parseXMLWithSAX(String xmlData){
        try{
            SAXParserFactory factory=SAXParserFactory.newInstance();
            XMLReader xmlReader=factory.newSAXParser().getXMLReader();
            ContentHandler handler=new ContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        }catch (Exception p){
            p.printStackTrace();
        }
    }
    //JSONObject方法解析JSON数据
    private void parseJSONWithJSONObject(String jsondata){
        try{
            JSONArray jsonArray=new JSONArray(jsondata);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("id");
                String name=jsonObject.getString("name");
                String version=jsonObject.getString("version");
                Log.d("MainActivity","id is "+id);
                Log.d("MainActivity","name is "+name);
                Log.d("MainActivity","version is "+version);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void parseJSONWithGSON(String jsonData){
        Gson gson=new Gson();
        List<App> appList=gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());
        for(App app:appList){
            Log.d("MainActivity","id is "+app.getId());
            Log.d("MainActivity","name is "+app.getName());
            Log.d("MainActivity","version is "+app.getVersion());
        }
    }
}
