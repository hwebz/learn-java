package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
