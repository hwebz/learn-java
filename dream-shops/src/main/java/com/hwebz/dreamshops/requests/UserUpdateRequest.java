package com.hwebz.dreamshops.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
}
