package cn.bravedawn.entity;

import java.io.Serializable;

public class StockLog implements Serializable {
    private String stockLogId;

    private Integer itemId;

    private Integer amount;

    /**
     * //1表示初始状态，2表示下单扣减库存成功，3表示下单回滚
     */
    private Integer status;

    private static final long serialVersionUID = 1L;

    public String getStockLogId() {
        return stockLogId;
    }

    public void setStockLogId(String stockLogId) {
        this.stockLogId = stockLogId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", stockLogId=").append(stockLogId);
        sb.append(", itemId=").append(itemId);
        sb.append(", amount=").append(amount);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}