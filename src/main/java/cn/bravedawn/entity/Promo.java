package cn.bravedawn.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Promo implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 活动名称
     */
    private String promoName;

    /**
     * 活动开始时间
     */
    private Date startDate;

    /**
     * 商品id
     */
    private Integer itemId;

    /**
     * 活动商品金
     */
    private BigDecimal promoItemPrice;

    /**
     * 活动结束时间
     */
    private Date endDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", promoName=").append(promoName);
        sb.append(", startDate=").append(startDate);
        sb.append(", itemId=").append(itemId);
        sb.append(", promoItemPrice=").append(promoItemPrice);
        sb.append(", endDate=").append(endDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}