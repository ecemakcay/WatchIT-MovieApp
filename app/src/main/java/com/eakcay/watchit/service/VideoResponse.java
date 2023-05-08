package com.eakcay.watchit.service;

import com.eakcay.watchit.model.VideoModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {
    @SerializedName("results")
    private List<VideoModel> videoList;

    public List<VideoModel> getVideoList() {
        return videoList;
    }
}
