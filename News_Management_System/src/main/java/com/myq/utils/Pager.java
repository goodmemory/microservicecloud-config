package com.myq.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author xiaoph
 * @date 2018-1-20 09:59:08
 */
public class Pager<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    // 总记录数
    private Long totalCount=0L;
    // 每页记录数
    private int pageSize;
    // 总页数
    private int totalPage = 10;// 默认10条
    // 当前页数
    private int pageIndex = 1;// 默认第一页
    // 列表数据
    private List<T> list;
    // 其他数据
    private Object otherData;

    /**
     * 分页
     *
     * @param list
     *            列表数据
     * @param totalCount
     *            总记录数
     * @param pageSize
     *            每页记录数
     * @param currPage
     *            当前页数
     */
    public Pager(List<T> list, Long totalCount, int pageSize, int pageIndex) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * 分页
     *
     * @param list
     *            列表数据
     * @param totalCount
     *            总记录数
     * @param pageSize
     *            每页记录数
     * @param currPage
     *            当前页数
     */
    public static <T> Pager<T> build(List<T> list, long totalCount, int pageSize, int pageIndex) {
        return new Pager<T>(list, totalCount, pageSize, pageIndex);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Pager<T> setOtherData(Object otherData) {
        this.otherData = otherData;
        return this;
    }

    public Object getOtherData() {
        return otherData;
    }

    public static int getTotalPages(int totalCount, int pageSize) {
        return (int) Math.ceil((double) totalCount / pageSize);
    }

}
