package cn.bravedawn.model.bo;

import cn.bravedawn.entity.Promo;
import lombok.Data;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/14 15:32
 */

@Data
public class ItemBO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 商品金额
     */
    private Double price;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品销量
     */
    private Integer sale;

    /**
     * 商品主图
     */
    private String imgUrl;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 活动信息
     */
    private PromoBO promo;
}
