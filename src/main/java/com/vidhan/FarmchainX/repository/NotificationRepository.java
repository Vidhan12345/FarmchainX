package com.vidhan.FarmchainX.repository;

import com.vidhan.FarmchainX.entity.Notification;
import com.vidhan.FarmchainX.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId ORDER BY n.timestamp DESC")
    List<Notification> findByUserIdOrderByTimestampDesc(@Param("userId") String userId);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.isRead = false ORDER BY n.timestamp DESC")
    List<Notification> findUnreadByUserId(@Param("userId") String userId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.isRead = false")
    Long countUnreadByUserId(@Param("userId") String userId);

    List<Notification> findByType(NotificationType type);
}
