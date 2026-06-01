package com.hospital.view;

import com.hospital.viewmodel.PatientViewModel;
import com.hospital.model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View for managing patients
 */
public class PatientView extends BaseView implements PropertyChangeListener {
    private PatientViewModel viewModel;
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    private JTextField searchField;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton refreshButton;

    public PatientView(PatientViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
    }

    @Override
    protected void initializeUI() {
        setLayout(new BorderLayout(PADDING, PADDING));
        setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // Top panel - Search and action buttons
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
        
        panel.add(new JLabel("Search:"));
        searchField = new JTextField(15);
        panel.add(searchField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> handleSearch());
        panel.add(searchButton);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> handleRefresh());
        panel.add(refreshButton);

        addButton = new JButton("Add Patient");
        addButton.addActionListener(e -> handleAddPatient());
        panel.add(addButton);

        editButton = new JButton("Edit Patient");
        editButton.addActionListener(e -> handleEditPatient());
        panel.add(editButton);

        deleteButton = new JButton("Delete Patient");
        deleteButton.addActionListener(e -> handleDeletePatient());
        panel.add(deleteButton);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Create table
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Date of Birth", "Gender", "Phone", "Email", "Address"},
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        patientTable = new JTable(tableModel);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(patientTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Ready");
        panel.add(statusLabel, BorderLayout.WEST);
        return panel;
    }

    private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            showError("Please enter a search term");
            return;
        }
        viewModel.searchPatients(searchTerm);
    }

    private void handleRefresh() {
        viewModel.loadAllPatients();
    }

    private void handleAddPatient() {
        showInfo("Add Patient functionality to be implemented");
    }

    private void handleEditPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow < 0) {
            showError("Please select a patient to edit");
            return;
        }
        showInfo("Edit Patient functionality to be implemented");
    }

    private void handleDeletePatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow < 0) {
            showError("Please select a patient to delete");
            return;
        }
        
        int result = showConfirmation("Are you sure you want to delete this patient?");
        if (result == JOptionPane.YES_OPTION) {
            int patientId = (int) tableModel.getValueAt(selectedRow, 0);
            viewModel.deletePatient(patientId);
        }
    }

    @Override
    public void refresh() {
        viewModel.loadAllPatients();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("patients".equals(evt.getPropertyName())) {
            updateTable();
        } else if ("statusMessage".equals(evt.getPropertyName())) {
            statusLabel.setText(viewModel.getStatusMessage());
        } else if ("isLoading".equals(evt.getPropertyName())) {
            updateLoadingState();
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Patient patient : viewModel.getPatients()) {
            tableModel.addRow(new Object[]{
                patient.getPatientId(),
                patient.getFullName(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getAddress()
            });
        }
    }

    private void updateLoadingState() {
        boolean isLoading = viewModel.isIsLoading();
        searchButton.setEnabled(!isLoading);
        refreshButton.setEnabled(!isLoading);
        addButton.setEnabled(!isLoading);
        editButton.setEnabled(!isLoading);
        deleteButton.setEnabled(!isLoading);
    }
}
