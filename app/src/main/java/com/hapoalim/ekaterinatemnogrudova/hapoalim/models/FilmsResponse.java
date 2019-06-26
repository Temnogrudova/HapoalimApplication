package com.hapoalim.ekaterinatemnogrudova.hapoalim.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FilmsResponse {
    @SerializedName("page")
    private String page;
    @SerializedName("total_results")
    private int total_results;
    @SerializedName("results")
    private List<Film> results;

    public String getPage() {
        return page;
    }

    public void setPage(String status) {
        this.page = page;
    }

    public int getTotalResults() {
        return total_results;
    }

    public void setTotalResults(int total_results) {
        this.total_results = total_results;
    }

    public List<Film> getResults() {
        return results;
    }

    public void setResults(List<Film> results) {
        this.results = results;
    }

}
