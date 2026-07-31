package com.shrawan.hospitalmanagement.dto;

import java.math.BigDecimal;

public class DashboardResponse {
    private long totalPatients;
    private long totalDoctors;
    private long todaysAppointments;
    private BigDecimal revenue;
    private long pendingBills;
    private long completedAppointments;
    private long availableDoctors;

    public DashboardResponse() {}

    public DashboardResponse(long totalPatients, long totalDoctors, long todaysAppointments, BigDecimal revenue, long pendingBills, long completedAppointments, long availableDoctors) {
        this.totalPatients = totalPatients;
        this.totalDoctors = totalDoctors;
        this.todaysAppointments = todaysAppointments;
        this.revenue = revenue;
        this.pendingBills = pendingBills;
        this.completedAppointments = completedAppointments;
        this.availableDoctors = availableDoctors;
    }

    public long getTotalPatients() { return totalPatients; }
    public void setTotalPatients(long totalPatients) { this.totalPatients = totalPatients; }

    public long getTotalDoctors() { return totalDoctors; }
    public void setTotalDoctors(long totalDoctors) { this.totalDoctors = totalDoctors; }

    public long getTodaysAppointments() { return todaysAppointments; }
    public void setTodaysAppointments(long todaysAppointments) { this.todaysAppointments = todaysAppointments; }

    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }

    public long getPendingBills() { return pendingBills; }
    public void setPendingBills(long pendingBills) { this.pendingBills = pendingBills; }

    public long getCompletedAppointments() { return completedAppointments; }
    public void setCompletedAppointments(long completedAppointments) { this.completedAppointments = completedAppointments; }

    public long getAvailableDoctors() { return availableDoctors; }
    public void setAvailableDoctors(long availableDoctors) { this.availableDoctors = availableDoctors; }
}
