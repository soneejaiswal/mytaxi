package com.mytaxi.criteria.driver;

import com.mytaxi.datatransferobject.FilterDTO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainvalue.AttributeType;
import com.mytaxi.domainvalue.CriteriaType;
import com.mytaxi.exception.InvalidCriteriaException;


public class DriverCriteriaBuilder {
    static DriverCriteria rootCriteria = null;

    public static DriverCriteria build(FilterDTO node) throws InvalidCriteriaException {


        if (node == null) {
            return null;
        }

        if (CriteriaType.AND == node.getCriteriaType()) {
            DriverCriteria criteria1 = buildCriteria(node.getAttribute(), node.getValue());
            if (node.getOtherCriteria() != null) {
                DriverCriteria criteria2 = build(node.getOtherCriteria());
                rootCriteria = new AndDriverCriteria(criteria1, criteria2);
            }

        } else if (CriteriaType.OR == node.getCriteriaType()) {
            DriverCriteria criteria1 = buildCriteria(node.getAttribute(), node.getValue());
            if (node.getOtherCriteria() != null) {
                DriverCriteria criteria2 = build(node.getOtherCriteria());
                rootCriteria = new OrDriverCriteria(criteria1, criteria2);
            }

        } else {
            AttributeType attribute = node.getAttribute();
            String value = node.getValue();
            rootCriteria = buildCriteria(attribute, value);
        }

        return rootCriteria;
    }

    private static DriverCriteria buildCriteria(AttributeType attribute, String value) throws InvalidCriteriaException {
        DriverCriteria criteria = null;
        switch (attribute){
            case RATING:
                criteria = new CarRatingDriverCriteria(getRating(value));
                break;
            case MANUFACTURER:
                criteria = new CarManufacturerDriverCriteria(getMaufacturer(value));
                break;
        }
        return null;
    }

    private static ManufacturerDO getMaufacturer(String value) {
        ManufacturerDO manufacturer = new ManufacturerDO(value);
        return manufacturer;
    }

    private static Float getRating(String value) throws InvalidCriteriaException {
        Float rating;
        try {
            rating = Float.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidCriteriaException("Invalid rating");
        }
        return rating;
    }

}
