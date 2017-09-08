package com.wangzai.lovesy.ui.refresh;

/**
 * Created by wangzai on 2017/9/4
 */

public class PagingBean {

    private int mPageIndex = 0;
    private int mPageSize = 0;
    private int mTotal = 0;
    private int mCurrentCount = 0;

    public int getmPageIndex() {
        return mPageIndex;
    }

    public PagingBean setmPageIndex(int mPageIndex) {
        this.mPageIndex = mPageIndex;
        return this;
    }

    public int getmPageSize() {
        return mPageSize;
    }

    public PagingBean setmPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
        return this;
    }

    public int getmTotal() {
        return mTotal;
    }

    public PagingBean setmTotal(int mTotal) {
        this.mTotal = mTotal;
        return this;
    }

    public int getmCurrentCount() {
        return mCurrentCount;
    }

    public PagingBean setmCurrentCount(int mCurrentCount) {
        this.mCurrentCount = mCurrentCount;
        return this;
    }

    public PagingBean addIndex() {
        mPageIndex++;
        return this;
    }
}
