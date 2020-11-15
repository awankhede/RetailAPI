package com.myretailapi.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class RetailVO {

    @JsonProperty("id")
    private String id = "";

    @JsonProperty("name")
    private String name = "";

    @JsonProperty("current_price")
    private CurrentPrice currentPrice = null;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetailVO retailVO = (RetailVO) o;
        return Objects.equals(id, retailVO.id) &&
                Objects.equals(name, retailVO.name) &&
                Objects.equals(currentPrice, retailVO.currentPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, currentPrice);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrentPrice getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(CurrentPrice currentPrice) {
        this.currentPrice = currentPrice;
    }
}
