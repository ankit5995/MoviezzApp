package com.bej.NotificationService.repository;
import com.bej.NotificationService.domain.Notifications;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notifications,String> {


}
