package com.codex.modelsheet.model;

public class Dimensions {
    private Country createdAt;
    private Country country;

    public Country getCreatedAt() { return createdAt; }
    public void setCreatedAt(Country value) { this.createdAt = value; }

    public Country getCountry() { return country; }
    public void setCountry(Country value) { this.country = value; }

    @Override
    public String toString() {
        return "Dimensions{" +
                "createdAt=" + createdAt +
                ", country=" + country +
                '}';
    }
}