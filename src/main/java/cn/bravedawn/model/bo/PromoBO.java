package cn.bravedawn.model.bo;


import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/14 15:43
 */
public class PromoBO {

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
    private DateTime startDate;

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
    private DateTime endDate;

    /**
     * //秒杀活动状态 1表示还未开始，2表示进行中，3表示已结束
     */
    private Integer status;

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

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }


    public Integer getStatus() {
        DateTime now = DateTime.now();
        if (now.isBefore(startDate)) {
            return 1;
        }

        if (now.isAfter(startDate) && now.isBefore(endDate)) {
            return 2;
        }

        if (now.isAfter(endDate)) {
            return 3;
        }
        return 1;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
