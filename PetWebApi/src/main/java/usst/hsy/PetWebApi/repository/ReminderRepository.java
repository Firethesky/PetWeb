package usst.hsy.PetWebApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usst.hsy.PetWebApi.entity.Reminder;
import usst.hsy.PetWebApi.entity.Reminder.EventType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Integer> {
    List<Reminder> findByUserId(Integer userId);
    List<Reminder> findByPetId(Integer petId);
    List<Reminder> findByUserIdAndIsCompletedFalse(Integer userId);
    List<Reminder> findByUserIdAndEventTimeBetween(Integer userId, LocalDateTime start, LocalDateTime end);
    List<Reminder> findByEventTypeAndIsCompletedFalse(EventType eventType);
} 