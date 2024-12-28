package io.github.ngspace.autoclicker;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class DoubleSpinner extends JSpinner {

    private static final long serialVersionUID = 1L;
    private static final double STEP_RATIO = 0.1;

    public DoubleSpinner() {
        // Model setup
    	SpinnerNumberModel model = new SpinnerNumberModel(0.0, -1000.0, 1000.0, 0.1);
        this.setModel(model);

        model.setStepSize(STEP_RATIO);
    }

    /**
     * Returns the current value as a Double
     */
    public double getDouble() {return ((Number)getValue()).doubleValue();}
}