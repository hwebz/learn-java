package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    public static String ADMIN = "ADMINISTRATOR";
    public static String USER = "USER";

    public boolean isRole(String role) {
        return this.getName().toUpperCase().equals(role);
    }
}
