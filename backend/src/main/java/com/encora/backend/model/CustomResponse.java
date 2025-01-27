package com.encora.backend.model;

import java.util.List;

public class CustomResponse<T> {
    private int offset;
    private int pages;
    private List<T> data;

    public CustomResponse(List<T> data, int pages, int offset) {
        this.data = data;
        this.pages = pages;
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public int getPages() {
        return pages;
    }

    public List<T> getData() {
        return data;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
