package gototongcheng.zhyan.com.library.Bean;

/**
 * Created by admin on 2017/3/12.
 */

public class OrderDetailBean {
    /*用户id*/
    private String userUsid = "";
    /*用户id*/

    private float orderLong;
    private float orderLat;
    private float orderDlong;
    private float orderDlat;

    private String clientaddrThings1;

    private String clientaddr1Things1;

    /*寄件信息:姓名＋详细地址＋联系方式*/
    private String clientaddrAddr= "";
    /*寄件信息:姓名＋详细地址＋联系方式*/
    /*收件信息:姓名＋详细地址＋联系方式*/
    private String clientaddrAddr1= "";
    /*收件信息:姓名＋详细地址＋联系方式*/
    /*重量*/
    private String orderHeight= "";
    /*重量*/
    /*物品种类*/
    private String orderName= "";
    /*物品种类*/
    /*收件时间*/
    private String orderTimeliness= "";
    /*收件时间*/
    /*备注*/
    private String orderRemark= "";
    /*备注*/
    /*价格*/
    private Double orderOrderprice;
    /*价格*/
    /*里程*/


    private Double orderMileage;
    /*里程*/
    /*区域(镇名)*/
    private String clientaddrArea= "";
    /*区域(镇名)*/
    /*清单*/
    private String detailsGoodsname= "";
    /*清单*/

    public float getOrderLong() {
        return orderLong;
    }

    public void setOrderLong(float orderLong) {
        this.orderLong = orderLong;
    }

    public float getOrderLat() {
        return orderLat;
    }

    public void setOrderLat(float orderLat) {
        this.orderLat = orderLat;
    }

    public float getOrderDlong() {
        return orderDlong;
    }

    public void setOrderDlong(float orderDlong) {
        this.orderDlong = orderDlong;
    }

    public float getOrderDlat() {
        return orderDlat;
    }

    public void setOrderDlat(float orderDlat) {
        this.orderDlat = orderDlat;
    }

    public String getClientaddrThings1() {
        return clientaddrThings1;
    }

    public void setClientaddrThings1(String clientaddrThings1) {
        this.clientaddrThings1 = clientaddrThings1;
    }

    public String getClientaddr1Things1() {
        return clientaddr1Things1;
    }

    public void setClientaddr1Things1(String clientaddr1Things1) {
        this.clientaddr1Things1 = clientaddr1Things1;
    }

    public String getUserUsid() {
        return userUsid;
    }

    public void setUserUsid(String userUsid) {
        this.userUsid = userUsid;
    }

    public String getClientaddrAddr() {
        return clientaddrAddr;
    }

    public void setClientaddrAddr(String clientaddrAddr) {
        this.clientaddrAddr = clientaddrAddr;
    }

    public String getClientaddrAddr1() {
        return clientaddrAddr1;
    }

    public void setClientaddrAddr1(String clientaddrAddr1) {
        this.clientaddrAddr1 = clientaddrAddr1;
    }

    public String getOrderHeight() {
        return orderHeight;
    }

    public void setOrderHeight(String orderHeight) {
        this.orderHeight = orderHeight;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderTimeliness() {
        return orderTimeliness;
    }

    public void setOrderTimeliness(String orderTimeliness) {
        this.orderTimeliness = orderTimeliness;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public Double getOrderOrderprice() {
        return orderOrderprice;
    }

    public void setOrderOrderprice(Double orderOrderprice) {
        this.orderOrderprice = orderOrderprice;
    }

    public Double getOrderMileage() {
        return orderMileage;
    }

    public void setOrderMileage(Double orderMileage) {
        this.orderMileage = orderMileage;
    }

    public String getClientaddrArea() {
        return clientaddrArea;
    }

    public void setClientaddrArea(String clientaddrArea) {
        this.clientaddrArea = clientaddrArea;
    }

    public String getDetailsGoodsname() {
        return detailsGoodsname;
    }

    public void setDetailsGoodsname(String detailsGoodsname) {
        this.detailsGoodsname = detailsGoodsname;
    }
}
