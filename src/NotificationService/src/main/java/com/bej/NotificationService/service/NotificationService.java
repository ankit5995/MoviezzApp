package com.bej.NotificationService.service;


import com.bej.NotificationService.config.MovieDTO;
import com.bej.NotificationService.domain.Notifications;

public interface NotificationService {
    public Notifications getNotification();
    void saveNotifications(MovieDTO movieDTO);
}



