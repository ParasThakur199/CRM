package com.thoughtpearls.scheduler;

import com.thoughtpearls.model.Lead;
import com.thoughtpearls.repository.LeadRepository;
import com.thoughtpearls.send_email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class LeadReminderScheduler {

    @Autowired
    private EmailService emailService;
    @Autowired
    private LeadRepository leadRepository;

    @Scheduled(cron = "${cron}")
    public void sendReminderEmail()
    {
        Date currentDate = new Date();
        List<Lead> leadsWithUpcomingReminders = leadRepository.findByReminderDate(currentDate);
        leadsWithUpcomingReminders.stream()
                .forEach(lead -> {
                    String to = lead.getUser().getEmail();
                    String subject = "Reminder About your Lead: " + lead.getLeadName();
                    String body = "This is a reminder for your lead: " + lead.getLeadName()
                            + " , which was created on: " + lead.getCreatedOn().toLocalDate()
                            + " by you.\nThe " + "Reminder Topic is: " + lead.getReminderTopic()
                            + ".\nThe description provided by you for this lead is: " + lead.getDescription()
                            + ".\nThe current status of the lead is: " + lead.getStatus().getCode();
                    emailService.sendReminderEmail(to, subject, body);
                });
    }
}
