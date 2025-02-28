package com.pentalog.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.pentalog.model.Notification;

@Component
public interface NotificationRepository extends Repository<Notification, Long> {

	public List<Notification> findBy();

	public void save(Notification notification);
}
