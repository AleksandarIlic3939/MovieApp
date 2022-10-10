package com.example.boopromovieapp.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private String name;
    private String imageSrc;
    private Integer categoryId;

}