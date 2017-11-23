package com.cicada.startup.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p/>
 * 创建时间: 16/3/25 上午12:04 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class UrlParams implements Parcelable {

    /**
     * packageType : 1001
     * childId : 12333
     * childName : 小明
     * productType : 1或2
     */

    /**
     * 特权类型
     */
    private String packageType;
    /**
     * 孩子ID
     */
    private long childId;
    /**
     * 商品类型
     */
    private int productType;

    /**
     * 开通状态
     */
    private int vipStatus;
    /**
     * 实物商品ID
     */
    private int itemId;
    /**
     * 实物商品数量
     */
    private int itemNum = 1;

    /**
     * 订单方式 线下订单需要带参数2
     */
    private int orderType = 1;

    /**
     * 商品类型 1：实物交易；0：虚拟交易
     */
    private int itemType;
    /**
     * 商品总价格
     */
    private String itemTotal;
    /**
     * 商品价格
     */
    private String itemPrice;

    /**
     * 商品名称
     */
    private String itemName;

    private long schoolId;

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(String itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public long getChildId() {
        return childId;
    }

    public void setChildId(long childId) {
        this.childId = childId;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(int vipStatus) {
        this.vipStatus = vipStatus;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public UrlParams() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageType);
        dest.writeLong(this.childId);
        dest.writeInt(this.productType);
        dest.writeInt(this.vipStatus);
        dest.writeInt(this.itemId);
        dest.writeInt(this.itemNum);
        dest.writeInt(this.orderType);
        dest.writeInt(this.itemType);
        dest.writeString(this.itemTotal);
        dest.writeString(this.itemPrice);
        dest.writeString(this.itemName);
        dest.writeLong(this.schoolId);
    }

    protected UrlParams(Parcel in) {
        this.packageType = in.readString();
        this.childId = in.readLong();
        this.productType = in.readInt();
        this.vipStatus = in.readInt();
        this.itemId = in.readInt();
        this.itemNum = in.readInt();
        this.orderType = in.readInt();
        this.itemType = in.readInt();
        this.itemTotal = in.readString();
        this.itemPrice = in.readString();
        this.itemName = in.readString();
        this.schoolId = in.readLong();
    }

    public static final Creator<UrlParams> CREATOR = new Creator<UrlParams>() {
        @Override
        public UrlParams createFromParcel(Parcel source) {
            return new UrlParams(source);
        }

        @Override
        public UrlParams[] newArray(int size) {
            return new UrlParams[size];
        }
    };
}
