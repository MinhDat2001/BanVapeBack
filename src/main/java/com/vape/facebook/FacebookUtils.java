package com.vape.facebook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.vape.entity.User;
import com.vape.google.GooglePojo;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FacebookUtils {


    @Autowired
    private Environment env;

    public String getToken(final String code) throws ClientProtocolException, IOException {
        String link = String.format(env.getProperty("facebook.link.get.token"), env.getProperty("facebook.app.id"),
                env.getProperty("facebook.app.secret"), env.getProperty("facebook.redirect.uri"), code);
        System.out.println("----link-------:"+link);
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }

    public com.vape.entity.User getUserInfo(final String accessToken)  throws ClientProtocolException, IOException, JSONException {

        String link = env.getProperty("facebook.link.get.user_info") + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        JSONObject jsonObject = new JSONObject(response);
        com.vape.entity.User facebookUser = new User();
        facebookUser.setStatus(1);
        facebookUser.setEmail(jsonObject.getString("email"));
        facebookUser.setName(jsonObject.getString("name"));
        JSONObject picData = jsonObject.getJSONObject("picture");
        JSONObject picture= picData.getJSONObject("data");
        facebookUser.setAvatar(picture.getString("url"));
        return facebookUser;
    }


}
