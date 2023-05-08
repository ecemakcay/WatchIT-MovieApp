package com.eakcay.watchit.service;

import com.eakcay.watchit.model.CastModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditsResponse {
    @SerializedName("cast")
    private List<CastModel> castList;

    public List<CastModel> getCastList() {
        return castList;
    }
}