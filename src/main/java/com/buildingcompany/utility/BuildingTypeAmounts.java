package com.buildingcompany.utility;

import java.math.BigDecimal;

public class BuildingTypeAmounts<T> {
    private T object;
    private BigDecimal foundationAmountPerProjectSqrMeter;
    private BigDecimal amountPerProjectSqrMeter;
    private BigDecimal amountBase;

    public BuildingTypeAmounts() {}

    public BuildingTypeAmounts(T object, BigDecimal foundationAmountPerProjectSqrMeter, BigDecimal amountPerProjectSqrMeter,
            BigDecimal amountBase) {
        this.object = object;
        this.foundationAmountPerProjectSqrMeter = foundationAmountPerProjectSqrMeter;
        this.amountPerProjectSqrMeter = amountPerProjectSqrMeter;
        this.amountBase = amountBase;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public BigDecimal getFoundationAmountPerProjectSqrMeter() {
        if(foundationAmountPerProjectSqrMeter == null) {
            return BigDecimal.ZERO;
        }
        return foundationAmountPerProjectSqrMeter;
    }

    public void setFoundationAmountPerProjectSqrMeter(BigDecimal foundationAmountPerProjectSqrMeter) {
        this.foundationAmountPerProjectSqrMeter = foundationAmountPerProjectSqrMeter;
    }

    public BigDecimal getAmountPerProjectSqrMeter() {
        if(amountPerProjectSqrMeter == null) {
            return BigDecimal.ZERO;
        }
        return amountPerProjectSqrMeter;
    }

    public void setAmountPerProjectSqrMeter(BigDecimal amountPerProjectSqrMeter) {
        this.amountPerProjectSqrMeter = amountPerProjectSqrMeter;
    }

    public BigDecimal getAmountBase() {
        if(amountBase == null) {
            return BigDecimal.ZERO;
        }
        return amountBase;
    }

    public void setAmountBase(BigDecimal amountBase) {
        this.amountBase = amountBase;
    }

    @Override
    public String toString() {
        return "BuildingTypeAmounts [object=" + object + ", amountPerProjectSqrMeter="
                + amountPerProjectSqrMeter + ", foundationAmountPerProjectSqrMeter="
                + foundationAmountPerProjectSqrMeter + ", amountBase=" + amountBase + "]";
    }
}
