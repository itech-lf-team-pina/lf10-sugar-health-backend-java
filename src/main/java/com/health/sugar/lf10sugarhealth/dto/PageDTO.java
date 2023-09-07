package com.health.sugar.lf10sugarhealth.dto;

import java.util.List;

public class PageDTO {

    private List<?> content;

    private long totalElements;

    private int totalPages;

    private boolean firstPage;

    private boolean lastPage;

    private int pageSize;

    private int pageNumber;

    private int numberOfElements;

    public PageDTO() {}

    public PageDTO(List<?> contentList, boolean isFirstPage, boolean isLastPage, int totalPages, int pageSize, int pageNumber, long totalElements, int numberOfElements) {
        this.content = contentList;
        this.firstPage = isFirstPage;
        this.lastPage = isLastPage;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.numberOfElements = numberOfElements;
    }


    public List<?> getContent() {
        return content;
    }

    public void setContent(List<?> content) {
        this.content = this.content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
}
