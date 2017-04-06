package com.github.lxs.peep.http.response;

/**
 * 作者：gaoyin
 * 电话：18810474975
 * 邮箱：18810474975@163.com
 * 版本号：1.0
 * 类描述： 约定服务器公共的json数据
 * 备注消息：
 * 修改时间：2016/11/23 下午5:52
 **/
public class GirlHttpResponse<T> extends BaseHttpResponse<T> {

    /**
     * tag1 : 美女
     * tag2 : 全部
     * totalNum : 16870
     * start_index : 2
     * return_number : 5
     */

    private String tag1;
    private String tag2;
    private int totalNum;
    private int start_index;
    private int return_number;

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getStart_index() {
        return start_index;
    }

    public void setStart_index(int start_index) {
        this.start_index = start_index;
    }

    public int getReturn_number() {
        return return_number;
    }

    public void setReturn_number(int return_number) {
        this.return_number = return_number;
    }

    @Override
    public String toString() {
        return "GirlHttpResponse{" +
                "tag1='" + tag1 + '\'' +
                ", tag2='" + tag2 + '\'' +
                ", totalNum=" + totalNum +
                ", start_index=" + start_index +
                ", return_number=" + return_number +
                '}';
    }
}
