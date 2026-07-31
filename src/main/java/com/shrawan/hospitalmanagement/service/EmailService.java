package com.shrawan.hospitalmanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final String fromEmail;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${spring.mail.properties.mail.smtp.from:shrawan29yadav@gmail.com}") String fromEmail
    ) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    @Async
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            log.info("Email successfully sent from {} to {}", fromEmail, to);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage(), e);
        }
    }

    public void sendRegistrationEmail(String to, String fullName, String verificationToken) {
        String subject = "Welcome to Hospital Management System - Registration Successful";
        String body = String.format("Hello %s,\n\nThank you for registering with our Hospital Management System.\nYour verification token is: %s\n\nBest regards,\nHospital Admin", fullName, verificationToken);
        sendEmail(to, subject, body);
    }

    public void sendAppointmentConfirmation(String to, String patientName, String doctorName, String date, String time) {
        String subject = "Appointment Confirmation";
        String body = String.format("Dear %s,\n\nYour appointment with Dr. %s has been scheduled on %s at %s.\n\nThank you,\nHospital Care Team", patientName, doctorName, date, time);
        sendEmail(to, subject, body);
    }

    public void sendPasswordResetEmail(String to, String resetToken) {
        String subject = "Password Reset Request";
        String body = String.format("You have requested a password reset. Your reset token is: %s\n\nIf you did not request this, please ignore.", resetToken);
        sendEmail(to, subject, body);
    }

    public void sendBillGeneratedEmail(String to, String billNumber, String amount) {
        String subject = "Hospital Bill Generated - " + billNumber;
        String body = String.format("Your hospital bill %s has been generated for amount: ₹%s. Please pay online via our patient portal.", billNumber, amount);
        sendEmail(to, subject, body);
    }

    public void sendPaymentSuccessEmail(String to, String paymentId, String amount) {
        String subject = "Payment Confirmation - " + paymentId;
        String body = String.format("Payment of ₹%s received successfully. Payment ID: %s.\n\nThank you!", amount, paymentId);
        sendEmail(to, subject, body);
    }
}
