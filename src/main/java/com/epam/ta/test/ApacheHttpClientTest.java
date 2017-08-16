package com.epam.ta.test;

import com.epam.ta.application.business_object.User;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStreamReader;

public class ApacheHttpClientTest {
    private String uri = "http://jsonplaceholder.typicode.com/users";

    @Test
    public void checkHttpStatus() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        int actualStatusCode = response.getStatusLine().getStatusCode();
        response.close();
        Assert.assertEquals(actualStatusCode, 200,"Response returned NOT 200 status");
    }

    @Test
    public void checkHttpResponseHeader() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        String valueOfContentTypeHeader = response.getFirstHeader("content-type").toString();
        response.close();
        Assert.assertTrue(valueOfContentTypeHeader.contains("application/json; charset=utf-8"),"The value of content type header is NOT equal to the required");
    }

    @Test
    public void checkResponseBody() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        Gson gson = new Gson();
        User[] users = gson.fromJson(new InputStreamReader(entity.getContent()), User[].class);
        response.close();
        Assert.assertEquals(users.length, 10, "The quantity of user is NOT equal");
    }

}
