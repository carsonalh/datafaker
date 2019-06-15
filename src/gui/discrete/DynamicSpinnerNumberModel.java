package gui.discrete;

import com.sun.istack.internal.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class DynamicSpinnerNumberModel<T extends Number> extends SpinnerNumberModel {

    private ArrayList<ChangeListener> changeListeners;

    private T value;
    private T minimumValue;
    private T maximumValue;
    private T stepSize;

    public DynamicSpinnerNumberModel(@NotNull T initialValue, @NotNull T minimumValue, @NotNull T maximumValue, @NotNull T stepSize) {
        this.value = initialValue;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.stepSize = stepSize;

        this.changeListeners = new ArrayList<>();
    }

    private boolean inRange(T number) {
        double tempMin, tempMax, tempOther;

        tempMin = this.minimumValue.doubleValue();
        tempMax = this.maximumValue.doubleValue();
        tempOther = number.doubleValue();

        // The new value is between the range
        return tempMin <= tempOther && tempOther <= tempMax;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (inRange((T) value) && !value.equals(this.value)) {
            this.value = (T) value;

            fireChangeListeners();
        }
    }

    @Override
    public Object getNextValue() {
        T attemptedValue = (T) Double.valueOf(value.doubleValue() + stepSize.doubleValue());

        if (inRange(attemptedValue))
            return attemptedValue;
        else
            return value;
    }

    @Override
    public Object getPreviousValue() {
        T attemptedValue = (T) Double.valueOf(value.doubleValue() - stepSize.doubleValue());

        if (inRange(attemptedValue))
            return attemptedValue;
        else
            return value;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        this.changeListeners.add(l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        this.changeListeners.remove(l);
    }

    public T getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(T minimumValue) {
        if (maximumValue.doubleValue() < maximumValue.doubleValue())
            return;

        this.minimumValue = minimumValue;

        if (!inRange(value)) {
            value = minimumValue;
        }
    }

    public T getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(T maximumValue) {
        if (maximumValue.doubleValue() < maximumValue.doubleValue())
            return;

        this.maximumValue = maximumValue;

        if (!inRange(value)) {
            value = minimumValue;
        }
    }

    private void fireChangeListeners() {
        ChangeEvent e = new ChangeEvent(this);

        for (ChangeListener l : this.changeListeners)
            l.stateChanged(e);
    }

}
