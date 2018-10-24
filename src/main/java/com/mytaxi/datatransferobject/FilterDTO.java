package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mytaxi.domainvalue.AttributeType;
import com.mytaxi.domainvalue.CriteriaType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterDTO {
    private AttributeType attribute;
    private String value;
    private CriteriaType criteriaType;
    private FilterDTO criteria;
    private FilterDTO otherCriteria;

    public FilterDTO() {}

    public FilterDTO(AttributeType attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    public FilterDTO(AttributeType attribute, String value,
                     CriteriaType criteriaType,
                     FilterDTO criteria, FilterDTO otherCriteria) {
        this.attribute = attribute;
        this.value = value;
        this.criteriaType = criteriaType;
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    public AttributeType getAttribute() {
        return attribute;
    }

    public String getValue() {
        return value;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    public FilterDTO getCriteria() {
        return criteria;
    }

    public FilterDTO getOtherCriteria() {
        return otherCriteria;
    }
}
