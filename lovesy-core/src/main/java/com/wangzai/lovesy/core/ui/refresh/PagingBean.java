package com.wangzai.lovesy.core.ui.refresh;

/**
 * Created by wangzai on 2017/9/4
 */

public class PagingBean {

    private int mPageIndex = 1;
    private int mPageSize = 0;
    private int mTotal = 0;
    private int mCurrentCount = 0;

    private String mFirstDataId;

    public int getPageIndex() {
        return mPageIndex;
    }

    public PagingBean setPageIndex(int mPageIndex) {
        this.mPageIndex = mPageIndex;
        return this;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public PagingBean setPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
        return this;
    }

    public int getTotal() {
        return mTotal;
    }

    public PagingBean setTotal(int mTotal) {
        this.mTotal = mTotal;
        return this;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    PagingBean setCurrentCount(int mCurrentCount) {
        this.mCurrentCount = mCurrentCount;
        return this;
    }

    public String getFirstDataId() {
        return mFirstDataId;
    }

    public PagingBean setFirstDataId(String id) {
        this.mFirstDataId = id;
        return this;
    }

    public PagingBean addIndex() {
        mPageIndex++;
        return this;
    }

    void reset() {
        mPageIndex = 1;
        mPageSize = 0;
        mTotal = 0;
        mCurrentCount = 0;
    }
}
