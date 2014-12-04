package com.wstester.plot2d;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class SoapInput {
    private String function;
    private int resolution;
    private DoubleProperty aProperty = new SimpleDoubleProperty(1);

    public SoapInput() {
    }

    public SoapInput(String function, int resolution, double a) {
        this.function = function;
        this.resolution = resolution;
    }
    
    

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * @return the resolution
     */
    public int getResolution() {
        return resolution;
    }

    /**
     * @param resolution the resolution to set
     */
    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
    
    public DoubleProperty aProperty() {
        return aProperty;
    }
    
    public void setA(double a) {
        aProperty().set(a);
    }
    
    public double getA() {
        return aProperty.get();
    }
}
