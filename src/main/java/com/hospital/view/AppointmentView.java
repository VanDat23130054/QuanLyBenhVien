package com.hospital.view;

import com.hospital.viewmodel.AppointmentViewModel;
import com.hospital.model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View for managing appointments
 */
public class AppointmentView extends BaseView implements PropertyChangeListener {
    private AppointmentViewModel viewModel;
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;

    public AppointmentView(AppointmentViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
    }

    @Override
    protected void initializeUI() {
        setLayout(new BorderLayout(PADDING, PADDING));
        setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // Top panel - Action buttons
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Center panel - Table
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel - Status bar
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, PADDING, PADDING));

        addButton = new JButton("Schedule Appointment");
        addButton.addActionListener(e -> handleAddAppointment());
        panel.add(addButton);

        editButton = new JButton("Edit Appointment");
        editButton.addActionListener(e -> handleEditAppointment());
        panel.add(editButton);

        deleteButton = new JButton("Cancel Appointment");
        deleteButton.addActionListener(e -> handleDeleteAppointment());
        panel.add(deleteButton);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> handleRefresh());
        panel.add(refreshButton);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(
            new String[]{"ID", "Patient ID", "Doctor ID", "Date & Time", "Reason", "Status", "Notes"},
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        appointmentTable = new JTable(tableModel);
        appointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Ready");
        panel.add(statusLabel, BorderLayout.WEST);
        return panel;
    }

    private void handleRefresh() {
        viewModel.loadAllAppointments();
    }

    private void handleAddAppointment() {
        showInfo("Schedule Appointment functionality to be implemented");
    }

    private void handleEditAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow < 0) {
            showError("Please select an appointment to edit");
            return;
        }
        showInfo("Edit Appointment functionality to be implemented");
    }

    private void handleDeleteAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow < 0) {
            showError("Please select an appointment to cancel");
            return;
        }

        int result = showConfirmation("Are you sure you want to cancel this appointment?");
        if (result == JOptionPane.YES_OPTION) {
            int appointmentId = (int) tableModel.getValueAt(selectedRow, 0);
            viewModel.deleteAppointment(appointmentId);
        }
    }

    @Override
    public void refresh() {
        viewModel.loadAllAppointments();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("appointments".equals(evt.getPropertyName())) {
            updateTable();
        } else if ("statusMessage".equals(evt.getPropertyName())) {
            statusLabel.setText(viewModel.getStatusMessage());
        } else if ("isLoading".equals(evt.getPropertyName())) {
            updateLoadingState();
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Appointment appointment : viewModel.getAppointments()) {
            tableModel.addRow(new Object[]{
                appointment.getAppointmentId(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getAppointmentDateTime(),
                appointment.getReason(),
                appointment.getStatus(),
                appointment.getNotes()
            });
        }
    }

    private void updateLoadingState() {
        boolean isLoading = viewModel.isIsLoading();
        addButton.setEnabled(!isLoading);
        editButton.setEnabled(!isLoading);
        deleteButton.setEnabled(!isLoading);
        refreshButton.setEnabled(!isLoading);
    }
}
