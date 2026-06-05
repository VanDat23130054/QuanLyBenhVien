package util;

public class Constants {
    // Role types
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_ACCOUNTANT = 2;
    public static final int ROLE_RECEPTIONIST = 3;
    public static final int ROLE_PATIENT = 4;
    public static final int ROLE_DOCTOR = 5;
    public static final int ROLE_PHARMACIST = 6;

    // Appointment status
    public static final String APPOINTMENT_PENDING = "Cho kham";
    public static final String APPOINTMENT_COMPLETED = "Da kham";
    public static final String APPOINTMENT_CANCELLED = "Da huy";

    // Prescription status
    public static final String PRESCRIPTION_PENDING = "Chua cap phat";
    public static final String PRESCRIPTION_DISTRIBUTED = "Da cap phat";

    // Invoice status
    public static final String INVOICE_UNPAID = "Chua thanh toan";
    public static final String INVOICE_PAID = "Da thanh toan";

    // Hospital stay status
    public static final String ADMISSION_ACTIVE = "Dang nam vien";
    public static final String ADMISSION_DISCHARGED = "Da xuat vien";

    // Attendance status
    public static final String ATTENDANCE_PRESENT = "Co mat";
    public static final String ATTENDANCE_ABSENT = "Vang";
    public static final String ATTENDANCE_LATE = "Tre";
}
