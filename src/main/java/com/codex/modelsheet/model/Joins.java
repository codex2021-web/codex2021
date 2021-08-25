package com.codex.modelsheet.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Joins {
    private Organizations Organizations;

    public com.codex.modelsheet.model.Organizations getOrganizations() {
        return Organizations;
    }

    @JsonProperty("Organizations")
    public void setOrganizations(com.codex.modelsheet.model.Organizations organizations) {
        Organizations = organizations;
    }

    @Override
    public String toString() {
        return "Joins{" +
                "organizations=" + Organizations.toString() +
                '}';
    }
}