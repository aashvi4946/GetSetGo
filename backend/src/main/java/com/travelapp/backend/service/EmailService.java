package com.travelapp.backend.service;

import com.travelapp.backend.entity.Booking;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendBookingConfirmationEmail(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getUser().getEmail());
        message.setSubject("Booking Confirmation for " + booking.getTour().getName());
        message.setText("Dear " + booking.getUser().getFirstName() + ",\n\n"
                + "Your booking for the tour '" + booking.getTour().getName() + "' has been approved!\n\n"
                + "Tour Details:\n"
                + "Destination: " + booking.getTour().getDestination() + "\n"
                + "Start Date: " + booking.getTour().getStartDate() + "\n"
                + "End Date: " + booking.getTour().getEndDate() + "\n\n"
                + "We look forward to seeing you!\n\n"
                + "Best regards,\nThe GetSetGo Team");
        emailSender.send(message);
    }
}
