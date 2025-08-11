package model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Item {

    private String code;
    private String description;
    private String packetSixe;
    private double unitPrice;
    private int qtyOnHand;

}
