package com.hospital.view;

import javax.swing.*;

/**
 * Base class for all views in the application
 */
public abstract class BaseView extends JPanel {
    protected static final int PADDING = 10;
    protected static final int COMPONENT_HEIGHT = 30;

    public BaseView() {
        initializeUI();
    }

    /**
     * Initialize UI components - to be implemented by subclasses
     */
    protected abstract void initializeUI();

    /**
     * Refresh the view with updated data
     */
    public abstract void refresh();

    /**
     * Show an information message to the user
     */
    protected void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show an error message to the user
     */
    protected void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Show a confirmation dialog
     */
    protected int showConfirmation(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.YES_NO_OPTION);
    }
}
