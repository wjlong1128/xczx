package com.wjl.xczx.common.result;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/*
 * @author Wang Jianlong
 * @version 1.0.0
 * @description
 * @date 2023/4/14
 */

public class PageResult<T> implements Serializable {

    private long page;
    private long pageSize;
    private long counts;
    private List<T> items;

    public PageResult() {
    }

    public PageResult(long page, long pageSize, long counts, List<T> items) {
        this.page = page;
        this.pageSize = pageSize;
        this.counts = counts;
        this.items = items;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getCounts() {
        return counts;
    }

    public void setCounts(long counts) {
        this.counts = counts;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageResult<?> that = (PageResult<?>) o;
        return page == that.page && pageSize == that.pageSize && counts == that.counts && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, pageSize, counts, items);
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", counts=" + counts +
                ", items=" + items +
                '}';
    }
}
