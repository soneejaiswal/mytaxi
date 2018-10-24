package com.mytaxi.criteria.car;

import com.mytaxi.datatransferobject.FilterDTO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainvalue.AttributeType;
import com.mytaxi.domainvalue.CriteriaType;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.exception.InvalidCriteriaException;

public class CriteriaBuilder {
    static CarCriteria rootCriteria = null;

    public static CarCriteria build(FilterDTO node) throws InvalidCriteriaException {


        if (node == null) {
            return null;
        }

        if (CriteriaType.AND == node.getCriteriaType()) {
            CarCriteria criteria1 = buildCriteria(node.getAttribute(), node.getValue());
            if (node.getOtherCriteria() != null) {
                CarCriteria criteria2 = build(node.getOtherCriteria());
                rootCriteria = new AndCarCriteria(criteria1, criteria2);
            }

        } else if (CriteriaType.OR == node.getCriteriaType()) {
            CarCriteria criteria1 = buildCriteria(node.getAttribute(), node.getValue());
            if (node.getOtherCriteria() != null) {
                CarCriteria criteria2 = build(node.getOtherCriteria());
                rootCriteria = new OrCarCriteria(criteria1, criteria2);
            }

        } else {
            AttributeType attribute = node.getAttribute();
            String value = node.getValue();
            rootCriteria = buildCriteria(attribute, value);
        }

        return rootCriteria;
    }

    private static CarCriteria buildCriteria(AttributeType attribute, String value) throws InvalidCriteriaException {
        CarCriteria criteria = null;
        switch (attribute) {
            case ENGINE_TYPE:
                EngineType engine = getEngineType(value);
                criteria = new CarEngineCarCriteria(engine);
                break;

            case MANUFACTURER:
                criteria = new CarManufacturerCarCriteria(getManufacturer(value));
                break;

            case RATING:
                Float carRating = getRating(value);
                criteria = new CarRatingCarCriteria(carRating);
                break;
        }

        return criteria;
    }

    private static EngineType getEngineType(String value) throws InvalidCriteriaException {
        EngineType engine = null;
        if (value != null) {
            try {
                engine = EngineType.valueOf(value);
            } catch (IllegalArgumentException e) {
                throw new InvalidCriteriaException("Invalid engine type");
            }

        }
        return engine;
    }

    private static Float getRating(String value) throws InvalidCriteriaException {
        Float rating = Float.valueOf("0");
        try {
            rating = Float.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidCriteriaException("Invalid rating");
        }
        return rating;
    }


    private static ManufacturerDO getManufacturer(String value) throws InvalidCriteriaException {
        ManufacturerDO manufacturer = new ManufacturerDO(value);

        return manufacturer;
    }
}
