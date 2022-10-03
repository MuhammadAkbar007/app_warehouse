package uz.pdp.appwarehouse.payload;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private Long chatId;
    private Boolean active;
    private Set<Integer> warehouseIds;
}
