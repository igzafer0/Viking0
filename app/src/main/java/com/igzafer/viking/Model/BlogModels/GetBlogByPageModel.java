package com.igzafer.viking.Model.BlogModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBlogByPageModel {
    @SerializedName("pageNumber")
    @Expose
    int pageNumber;
    @SerializedName("pageSize")
    @Expose
    int pageSize;

    public GetBlogByPageModel(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
