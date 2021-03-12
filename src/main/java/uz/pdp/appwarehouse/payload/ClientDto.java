package uz.pdp.appwarehouse.payload;

import lombok.Data;

@Data
public class ClientDto {
    private String name;
    private String phoneNumber;
    private Boolean active;
}
