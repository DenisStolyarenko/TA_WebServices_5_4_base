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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApacheHttpClientTest {
    @Test
    public void checkHttpStatus() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://jsonplaceholder.typicode.com/users");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        int actualStatusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(actualStatusCode, 200,"Response returned NOT 200 status");
    }

    @Test
    public void checkHttpResponseHeader() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://jsonplaceholder.typicode.com/users");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        String valueOfContentTypeHeader = response.getFirstHeader("content-type").toString();
        System.out.println(valueOfContentTypeHeader);
        Assert.assertTrue(valueOfContentTypeHeader.contains("application/json; charset=utf-8"));
    }

    @Test
    public void checkResponseBody() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://jsonplaceholder.typicode.com/users");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        entity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
        Gson gson = new Gson();
        User[] users = gson.fromJson(br, User[].class);
        System.out.println(users.length);
        Assert.assertEquals(users.length, 10, "The quantity of user is NOT equal");
    }

}
