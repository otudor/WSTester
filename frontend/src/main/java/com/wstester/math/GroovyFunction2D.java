/* 
 * GroovyFunction2D.java
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2009–2012 Steinbeis Forschungszentrum (STZ Ölbronn),
 * Copyright (c) 2006–2012 by Michael Hoffer
 * 
 * This file is part of Visual Reflection Library (VRL).
 *
 * VRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * see: http://opensource.org/licenses/LGPL-3.0
 *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
 *
 * VRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * This version of VRL includes copyright notice and attribution requirements.
 * According to the LGPL this information must be displayed even if you modify
 * the source code of VRL. Neither the VRL Canvas attribution icon nor any
 * copyright statement/attribution may be removed.
 *
 * Attribution Requirements:
 *
 * If you create derived work you must do three things regarding copyright
 * notice and author attribution.
 *
 * First, the following text must be displayed on the Canvas:
 * "based on VRL source code". In this case the VRL canvas icon must be removed.
 * 
 * Second, the copyright notice must remain. It must be reproduced in any
 * program that uses VRL.
 *
 * Third, add an additional notice, stating that you modified VRL. In addition
 * you must cite the publications listed below. A suitable notice might read
 * "VRL source code modified by YourName 2012".
 * 
 * Note, that these requirements are in full accordance with the LGPL v3
 * (see 7. Additional Terms, b).
 *
 * Publications:
 *
 * M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
 * A Framework for Declarative GUI Programming on the Java Platform.
 * Computing and Visualization in Science, 2011, in press.
 */
package com.wstester.math;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * A function defined by a groovy string. It can be used to define functions at
 * runtime. The performance will be significantly worse than native java code.
 * But it is much more flexible.
 * </p>
 * <p>
 * The function must consist of an expression that does not use other variables
 * than <code>x</code> and <code>y</code>. All functions from
 * <code>java.lang.Math</code> can be used.
 * </p>
 * <p>
 * Example:
 * <pre>
 * <code>
 * </code> sin(sqrt(x*x+y*y))
 * </code>
 * </p>
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class GroovyFunction2D implements Serializable, Function2D {

    private String expression;
    private  Script script;
    private String xVarName = "x";
    private String yVarName = "y";
    private final Map<String, Double> properties = new HashMap<>();

    /**
     * Constructor.
     */
    public GroovyFunction2D() {
    }

    /**
     * Constructor.
     *
     * @param expression the expression that defines the function
     */
    public GroovyFunction2D(String expression) {
        setExpression(expression);
    }

    /**
     * Constructor.
     *
     * @param expression expression the expression that defines the function
     * @param xVarName custom name of the x variable
     * @param yVarName custom name of the y variable
     */
    public GroovyFunction2D(String expression, String xVarName, String yVarName) {
        setExpression(expression);
        setXVarName(xVarName);
        setYVarName(yVarName);
    }

    /**
     * Returns the expression of the function
     *
     * @return the expression of the function
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Defines the expression of the function.
     *
     * @param expression the expression to set
     */
    public final void setExpression(String expression) {
        this.expression = expression;
        GroovyShell shell = new GroovyShell();
        script = shell.parse("import static java.lang.Math.*; result = ("
                + expression + ") as double");
    }

    @Override
    public double run(double x, double y) {
        getScript().setProperty(getXVarName(), x);
        getScript().setProperty(getYVarName(), y);

        for (String name : properties.keySet()) {
            getScript().setProperty(name, properties.get(name));
        }

        getScript().run();

        return (double) getScript().getProperty("result");
    }

    /**
     * Returns the groovy script that is used to evaluate the expression.
     *
     * @return the groovy script that is used to evaluate the expression
     */
    public Script getScript() {
        return script;
    }

    /**
     * @return the xValueName
     */
    public String getXVarName() {
        return xVarName;
    }

    /**
     * @param xValueName the xValueName to set
     */
    public final void setXVarName(String xValueName) {
        this.xVarName = xValueName;
    }

    /**
     * @return the yValueName
     */
    public String getYVarName() {
        return yVarName;
    }

    /**
     * @param yValueName the yValueName to set
     */
    public final void setYVarName(String yValueName) {
        this.yVarName = yValueName;
    }

    /**
     * Set additional property.
     *
     * @param name property name
     * @param value property value
     */
    public void setProperty(String name, double value) {
        properties.put(name, value);
    }
}
