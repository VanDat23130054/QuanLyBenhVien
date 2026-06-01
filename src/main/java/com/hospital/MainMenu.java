package com.hospital;

import com.hospital.viewmodel.PatientViewModel;
import com.hospital.viewmodel.AppointmentViewModel;
import com.hospital.view.PatientView;
import com.hospital.view.AppointmentView;

import javax.swing.*;
import java.awt.*;

/**
 * Main menu UI for the Hospital Management System
 * Implements MVVM pattern with ViewModels and Views
 */
public class MainMenu extends JFrame {
    private JTabbedPane tabbedPane;
    private PatientViewModel patientViewModel;
    private AppointmentViewModel appointmentViewModel;
    private PatientView patientView;
    private AppointmentView appointmentView;

    public MainMenu() {
        super("Hospital Management System - Main Menu");
        initViewModels();
        initUI();
    }

    private void initViewModels() {
        patientViewModel = new PatientViewModel();
        appointmentViewModel = new AppointmentViewModel();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> handleExit());
        fileMenu.add(exitItem);

        // Patients menu
        JMenu patientsMenu = new JMenu("Patients");
        JMenuItem viewPatients = new JMenuItem("View Patients");
        viewPatients.addActionListener(e -> tabbedPane.setSelectedIndex(0));
        patientsMenu.add(viewPatients);

        // Appointments menu
        JMenu appointmentsMenu = new JMenu("Appointments");
        JMenuItem viewAppointments = new JMenuItem("View Appointments");
        viewAppointments.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        appointmentsMenu.add(viewAppointments);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> showAbout());
        helpMenu.add(about);

        // Add menus to the bar
        menuBar.add(fileMenu);
        menuBar.add(patientsMenu);
        menuBar.add(appointmentsMenu);
        menuBar.add(Box.createHorizontalGlue()); // push Help to the right
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        // Create tabbed pane with views
        tabbedPane = new JTabbedPane();
        
        // Patient View
        patientView = new PatientView(patientViewModel);
        tabbedPane.addTab("Patients", patientView);
        
        // Appointment View
        appointmentView = new AppointmentView(appointmentViewModel);
        tabbedPane.addTab("Appointments", appointmentView);

        add(tabbedPane, BorderLayout.CENTER);

        // Load initial data
        patientViewModel.loadAllPatients();
        appointmentViewModel.loadAllAppointments();
    }

    private void handleExit() {
        int res = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(this,
            "Hospital Management System\n" +
            "Version 1.0\n\n" +
            "Architecture: MVVM (Model-View-ViewModel)\n" +
            "Database: SQL Server",
            "About",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showGui() {
        EventQueue.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            MainMenu frame = new MainMenu();
            frame.setVisible(true);
        });
    }
}
