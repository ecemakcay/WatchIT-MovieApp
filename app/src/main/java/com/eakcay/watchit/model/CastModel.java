package com.eakcay.watchit.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CastModel implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("character")
    private String character;
    @SerializedName("profile_path")
    private String profilePath;

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getProfilePath() {
        return profilePath;
    }

}