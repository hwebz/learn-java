package com.hwebz.dreamshops.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private CartDTO cart;
    private List<OrderDTO> orders;
}
