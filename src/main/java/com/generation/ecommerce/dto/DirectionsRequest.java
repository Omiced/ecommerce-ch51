package com.generation.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectionsRequest {
    private String street;
    private String neighborhood;
    private String zipCode;
    private String country;

}
