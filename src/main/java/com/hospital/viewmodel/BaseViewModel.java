package com.hospital.viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Base ViewModel class with property change notification support
 * Used for binding views to observable data
 */
public abstract class BaseViewModel {
    protected PropertyChangeSupport propertyChangeSupport;

    public BaseViewModel() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Add a property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove a property change listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Add a listener for a specific property
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove a listener for a specific property
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Fire a property change event
     */
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
