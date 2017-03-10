package cz.muni.fi.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TimetablingClientImpl implements TimetablingClient {
    private String apiKey;
    private static String url = "http://129.34.40.6";
    private static int port = 8000;

    public TimetablingClientImpl(String apiKey) {
        this.apiKey = apiKey;
    }


    @Override
    public InputStream getUpdate() throws IOException {
        HttpResponse response = executePostRequest("/getUpdate");

        // TO DELETE - debug information
        printDebugInfo(response);

        return response.getEntity().getContent();
    }

    @Override
    public void submitSplit(String solution) throws IOException {
        HttpResponse response = executePostRequest("/submitSplit", solution);

        // TO DELETE - debug information
        printDebugInfo(response);
    }

    @Override
    public InputStream getEvaluation() throws IOException {
        HttpResponse response = executePostRequest("/getEvaluation");

        // TO DELETE - debug information
        printDebugInfo(response);

        return response.getEntity().getContent();
    }

    private HttpResponse executePostRequest(String urlSuffix) throws IOException {
        String fullUrl = getFullUrl(urlSuffix);
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(fullUrl);
        HttpEntity entity = createHttpEntityWithApiKey();
        request.setEntity(entity);

        // TO DELETE - debug information
        printDebugInfo(request);

        return client.execute(request);
    }

    private HttpResponse executePostRequest(String urlSuffix, String content) throws IOException {
        return null;
    }

    private HttpEntity createHttpEntityWithApiKey() throws UnsupportedEncodingException {
        List<NameValuePair> content = new ArrayList<NameValuePair>();
        content.add(new BasicNameValuePair("apiKey", this.apiKey));
        return new UrlEncodedFormEntity(content);
    }

    private String getFullUrl(String urlSuffix) {
        return url + ':' + port + urlSuffix;
    }

    private void printDebugInfo(HttpPost request) throws IOException {
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + request.getEntity());

        StringBuilder result = new StringBuilder();
        String line = "";
        BufferedReader rdRequest = new BufferedReader(
                new InputStreamReader(request.getEntity().getContent()));
        while ((line = rdRequest.readLine()) != null) {
            result.append(line);
        }
        result.append(System.lineSeparator());
        System.out.println("Post content : " + result.toString());
    }

    private void printDebugInfo(HttpResponse response) throws IOException {
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        result.append(System.lineSeparator());
        System.out.println("Response content : " + result.toString());
    }
}
