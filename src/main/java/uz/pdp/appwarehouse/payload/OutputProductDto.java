package uz.pdp.appwarehouse.payload;

import lombok.Data;

@Data
public class OutputProductDto {
    private Double amount;
    private Double price;
    private Integer productId;
    private Integer outputId;
}
