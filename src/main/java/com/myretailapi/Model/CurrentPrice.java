package com.myretailapi.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "currentprice")
public class CurrentPrice implements Serializable {

    @Id
    @JsonIgnore
    private String id;

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
    public String toString() {
        return "CurrentPrice{" +
                "value='" + value + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
