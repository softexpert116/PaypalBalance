package com.clevery.android.paypalbalance.httpModule;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class RequestBuilder {
    HashMap<String, String> params;
    MultipartEntity entity;
    String serverUrl;
    Context context;

    public RequestBuilder(String url)
    {
        params = new HashMap<>();
        entity = new MultipartEntity();
        serverUrl = url;
    }


    public RequestBuilder clear() {
        params.clear();
        entity = null;
        return this;
    }

    public RequestBuilder addParam(String name, String value) {
        params.put(name, value);
        return this;
    }

    public ResponseElement sendRequest() {
        HttpCommunicator communicator = new HttpCommunicator(serverUrl);
        return new ResponseElement(communicator.post(params));
    }

    public HashMap<String, String> getParams() {
        return params;
    }




    /******************************************************************************************************************************
     *                                               AsynTask send request(params)                                                  *
     ******************************************************************************************************************************/

    public void sendRequest(final RunanbleCallback callback) {
        SendRequest request = new SendRequest();
        request.setRunanbleCallback(callback);
        request.execute();
    }
    private class SendRequest extends AsyncTask<String, Void, String> {
        private RunanbleCallback callback;
        ResponseElement element;

        public void setRunanbleCallback(RunanbleCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... params1) {
            HttpCommunicator communicator = new HttpCommunicator(serverUrl);
            element = new ResponseElement(communicator.post(params));
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            callback.finish(element);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    /******************************************************************************************************************************
     *                                               File Upload Part                                                              *
     ******************************************************************************************************************************/
    public RequestBuilder addEntityParam(String name, String value){
        try {
            entity.addPart(name, new StringBody(value));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }
    public RequestBuilder addMultiEntityParam(String key, String fileName){
        entity.addPart(key, new FileBody(new File(fileName), "image/jpeg"));
        return this;
    }
    public ResponseElement sendMultiRequest(){
        HttpCommunicator communicator = new HttpCommunicator(serverUrl);
        return new ResponseElement(communicator.postMulti(entity));
    }
    public MultipartEntity getEntity(){
        return entity;
    }

    /******************************************************************************************************************************
     *                                               AsynTask send request(params)                                                 *
     ******************************************************************************************************************************/

    public void sendMultiRequest(final RunanbleCallback callback) {
        SendRequest request = new SendRequest();
        request.setRunanbleCallback(callback);
        request.execute();
    }
    private class SendMultiRequest extends AsyncTask<String, Void, String> {
        private RunanbleCallback callback;
        ResponseElement element;

        public void setRunanbleCallback(RunanbleCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... params1) {
            HttpCommunicator communicator = new HttpCommunicator(serverUrl);
            element = new ResponseElement(communicator.postMulti(entity));
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            callback.finish(element);
        }
        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
