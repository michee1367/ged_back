package com.mich.ged.domain.interfaces.out;

import com.mich.ged.domain.models.NotificationModel;

public interface NotificationPort {
    void envoyerNotification(NotificationModel notification);
}
