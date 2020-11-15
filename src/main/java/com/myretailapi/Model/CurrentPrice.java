package com.myretailapi.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CurrentPrice {

    @JsonProperty("value")
    private String value = "";

    @JsonProperty("currency_code")
    private String currencyCode = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentPrice that = (CurrentPrice) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(currencyCode, that.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currencyCode);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }


}
