/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wstester.plot2d;

import com.wstester.math.GroovyFunction1D;
import com.wstester.math.MathUtil;
import eu.mihosoft.vrl.workflow.VNode;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Plotter2D {

    private VNode node;

    public Node plot(FunctionInput functionInput, LineChart<Number, Number> lineChart) {
        //
        GroovyFunction1D function =
                new GroovyFunction1D(functionInput.getFunction(), "x");
        function.setProperty("a", functionInput.getA());

        Node chart = MathUtil.evaluateFunction(
                lineChart, "f(x)=" + functionInput.getFunction(),
                function, 0, 10, functionInput.getResolution());
        return chart;
    }

    /**
     * @return the node
     */
    public VNode getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(VNode node) {
        this.node = node;
    }
}
