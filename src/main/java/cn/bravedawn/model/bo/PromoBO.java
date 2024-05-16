package cn.bravedawn.model.bo;


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
    private Date startDate;

    /**
     * 商品id
     */
    private Integer itemId;

    /**
     * 活动商品金
     */
    private Double promoItemPrice;

    /**
     * 活动结束时间
     */
    private Date endDate;

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

    public Double getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(Double promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        Date now = new Date();
        if (now.before(startDate)) {
            return 1;
        }

        if (now.after(startDate) && now.before(endDate)) {
            return 2;
        }

        if (now.after(endDate)) {
            return 3;
        }
        return 1;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
