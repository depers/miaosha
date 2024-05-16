package cn.bravedawn.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/13 16:01
 */

@Data
public class OrderReqDTO {

    /**
     * 商品id
     */
    @NotBlank
    private String itemId;

    /**
     * 商品数量
     */
    @NotBlank
    private String amount;

    /**
     * 活动id
     */
    @NotBlank
    private String promoId;
}
