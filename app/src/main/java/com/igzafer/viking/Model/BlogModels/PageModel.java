package com.igzafer.viking.Model.BlogModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageModel {
    @SerializedName("TotalCount")
    @Expose
    int TotalCount;
    @SerializedName("PageSize")
    @Expose
    int PageSize;
    @SerializedName("CurrentPage")
    @Expose
    int CurrentPage;
    @SerializedName("TotalPages")
    @Expose
    int TotalPages;
    @SerializedName("HasNext")
    @Expose
    Boolean HasNext;
    @SerializedName("HasPrevious")
    @Expose
    String HasPrevious;

    public PageModel(int totalCount, int pageSize, int currentPage, int totalPages, Boolean hasNext, String hasPrevious) {
        TotalCount = totalCount;
        PageSize = pageSize;
        CurrentPage = currentPage;
        TotalPages = totalPages;
        HasNext = hasNext;
        HasPrevious = hasPrevious;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        CurrentPage = currentPage;
    }

    public int getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(int totalPages) {
        TotalPages = totalPages;
    }

    public Boolean getHasNext() {
        return HasNext;
    }

    public void setHasNext(Boolean hasNext) {
        HasNext = hasNext;
    }

    public String getHasPrevious() {
        return HasPrevious;
    }

    public void setHasPrevious(String hasPrevious) {
        HasPrevious = hasPrevious;
    }
}
