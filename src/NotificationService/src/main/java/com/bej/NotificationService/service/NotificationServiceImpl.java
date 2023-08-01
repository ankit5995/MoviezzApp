package com.bej.NotificationService.service;


import com.bej.NotificationService.config.MovieDTO;
import com.bej.NotificationService.domain.Notifications;
import com.bej.NotificationService.repository.NotificationRepository;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;
    private String emaill ;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notifications getNotification() {

        System.out.println("before email  :"+emaill);
        return notificationRepository.findById(emaill).orElse(null);
    }

    @RabbitListener(queues = "movieService-queue")
    @Override
    public void saveNotifications(MovieDTO movieDTO) {
        System.out.println("inside saveNotificaitons  ");
        Notifications notifications =new Notifications();

        if (movieDTO.getJsonObject() != null ){
            String email=movieDTO.getJsonObject().get("emailId").toString();
            emaill = email;
            System.out.println("inside saveNotificaitons  "+email);
            if(notificationRepository.findById(email).isEmpty())
            {
                notifications.setEmailId(email);
            }
            notifications.setNotificationMessage("User Registered Successfully");
            notifications.setRegisterDetails(movieDTO.getJsonObject());
            notificationRepository.save(notifications);
        }

    }
}

