package com.dbmanagement.GymLife.webObject;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WebMembership {

    @NotNull(message = "Name is required")
    @Size(min = 1, message = "Name is required")
    private String typeName;

    @NotNull(message = "Price is required")
    // @Min(0)
    // @Max(1000)
    private String price;

    public WebMembership() {
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
