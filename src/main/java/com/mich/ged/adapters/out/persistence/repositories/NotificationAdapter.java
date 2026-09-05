package com.mich.ged.adapters.out.persistence.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mich.ged.adapters.out.persistence.entities.NotificationEntity;
import com.mich.ged.adapters.out.persistence.mappers.GenericMapper;
import com.mich.ged.domain.interfaces.out.NotificationPort;
import com.mich.ged.domain.models.NotificationModel;

@Component
public class NotificationAdapter implements NotificationPort {

    private final SpringDataNotificationRepository notificationRepository;
    private final GenericMapper mapper;

    public NotificationAdapter(SpringDataNotificationRepository notificationRepository,
                               GenericMapper mapper) {
        this.notificationRepository = notificationRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void envoyerNotification(NotificationModel notification) {
        // 1. Sauvegarde en base de données pour l'inbox interne
        NotificationEntity entity = mapper.toEntity(notification);
        notificationRepository.save(entity);

        // 2. (Optionnel) Vous pouvez intégrer ici l'envoi en temps réel (WebSocket, SMS, Email, etc.)
    }
}