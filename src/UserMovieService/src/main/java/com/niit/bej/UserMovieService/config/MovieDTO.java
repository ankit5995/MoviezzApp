package com.niit.bej.UserMovieService.config;

import org.json.simple.JSONObject;

public class MovieDTO {

    private JSONObject jsonObject;

   public  MovieDTO(JSONObject jsonObject) {this.jsonObject = jsonObject;    }

    public MovieDTO(){}


    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
