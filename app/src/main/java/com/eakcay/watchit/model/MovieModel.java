package com.eakcay.watchit.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;
import java.util.List;

public class MovieModel implements Serializable, Parcelable {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("popularity")
    @Expose
    private String popularity;

    @SerializedName("vote_count")
    @Expose
    private String voteCount;

    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("vote_average")
    @Expose
    private String voteAverage;

    @SerializedName("runtime")
    @Expose
    private int runtime;

    @SerializedName("genres")
    @Expose
    private List<Genre> genres;


    protected MovieModel(Parcel in) {
        title = in.readString();
        id = in.readString();
        name = in.readString();
        popularity = in.readString();
        voteCount = in.readString();
        firstAirDate = in.readString();
        posterPath = in.readString();
        originalLanguage = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        runtime = in.readInt();

    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genreIds) {
        this.genres = genreIds;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return Integer.parseInt(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImageUrl() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(popularity);
        parcel.writeString(voteCount);
        parcel.writeString(firstAirDate);
        parcel.writeString(posterPath);
        parcel.writeString(originalLanguage);
        parcel.writeString(backdropPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(voteAverage);
        parcel.writeInt(runtime);
    }
}
