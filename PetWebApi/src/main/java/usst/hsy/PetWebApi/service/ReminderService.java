package usst.hsy.PetWebApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usst.hsy.PetWebApi.entity.Pet;
import usst.hsy.PetWebApi.entity.Reminder;
import usst.hsy.PetWebApi.entity.Reminder.EventType;
import usst.hsy.PetWebApi.entity.User;
import usst.hsy.PetWebApi.repository.PetRepository;
import usst.hsy.PetWebApi.repository.ReminderRepository;
import usst.hsy.PetWebApi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReminderService(ReminderRepository reminderRepository, PetRepository petRepository, UserRepository userRepository) {
        this.reminderRepository = reminderRepository;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public List<Reminder> findAll() {
        return reminderRepository.findAll();
    }

    public List<Reminder> findByUserId(Integer userId) {
        return reminderRepository.findByUserId(userId);
    }

    public List<Reminder> findByPetId(Integer petId) {
        return reminderRepository.findByPetId(petId);
    }

    public List<Reminder> findPendingByUserId(Integer userId) {
        return reminderRepository.findByUserIdAndIsCompletedFalse(userId);
    }

    public List<Reminder> findByUserIdAndDateRange(Integer userId, LocalDateTime start, LocalDateTime end) {
        return reminderRepository.findByUserIdAndEventTimeBetween(userId, start, end);
    }

    public List<Reminder> findPendingByEventType(EventType eventType) {
        return reminderRepository.findByEventTypeAndIsCompletedFalse(eventType);
    }

    public Optional<Reminder> findById(Integer id) {
        return reminderRepository.findById(id);
    }

    public Reminder save(Reminder reminder) {
        return reminderRepository.save(reminder);
    }

    public Reminder create(Reminder reminder, Integer petId, Integer userId) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        Optional<User> optionalUser = userRepository.findById(userId);
        
        if (optionalPet.isPresent() && optionalUser.isPresent()) {
            Pet pet = optionalPet.get();
            User user = optionalUser.get();
            
            reminder.setPet(pet);
            reminder.setUser(user);
            reminder.setIsCompleted(false);
            
            return reminderRepository.save(reminder);
        } else {
            throw new RuntimeException("宠物或用户不存在！");
        }
    }

    public Reminder update(Reminder reminder) {
        Optional<Reminder> optionalReminder = reminderRepository.findById(reminder.getId());
        if (optionalReminder.isPresent()) {
            Reminder existingReminder = optionalReminder.get();
            
            // 更新字段
            existingReminder.setTitle(reminder.getTitle());
            existingReminder.setEventType(reminder.getEventType());
            existingReminder.setEventTime(reminder.getEventTime());
            existingReminder.setIsRecurring(reminder.getIsRecurring());
            existingReminder.setRecurrenceRule(reminder.getRecurrenceRule());
            existingReminder.setIsCompleted(reminder.getIsCompleted());
            
            return reminderRepository.save(existingReminder);
        } else {
            throw new RuntimeException("提醒不存在！");
        }
    }

    public Reminder markAsCompleted(Integer id) {
        Optional<Reminder> optionalReminder = reminderRepository.findById(id);
        if (optionalReminder.isPresent()) {
            Reminder reminder = optionalReminder.get();
            reminder.setIsCompleted(true);
            return reminderRepository.save(reminder);
        } else {
            throw new RuntimeException("提醒不存在！");
        }
    }

    public void deleteById(Integer id) {
        reminderRepository.deleteById(id);
    }
} 