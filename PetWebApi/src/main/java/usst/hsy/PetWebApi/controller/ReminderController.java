package usst.hsy.PetWebApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usst.hsy.PetWebApi.entity.Reminder;
import usst.hsy.PetWebApi.entity.Reminder.EventType;
import usst.hsy.PetWebApi.service.ReminderService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reminders")
@CrossOrigin
public class ReminderController {
    private final ReminderService reminderService;

    @Autowired
    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping
    public ResponseEntity<List<Reminder>> getAllReminders() {
        return ResponseEntity.ok(reminderService.findAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reminder>> getRemindersByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(reminderService.findByUserId(userId));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<Reminder>> getRemindersByPetId(@PathVariable Integer petId) {
        return ResponseEntity.ok(reminderService.findByPetId(petId));
    }

    @GetMapping("/user/{userId}/pending")
    public ResponseEntity<List<Reminder>> getPendingRemindersByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(reminderService.findPendingByUserId(userId));
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<Reminder>> getRemindersByUserIdAndDateRange(
            @PathVariable Integer userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        return ResponseEntity.ok(reminderService.findByUserIdAndDateRange(userId, startDateTime, endDateTime));
    }

    @GetMapping("/type/{eventType}/pending")
    public ResponseEntity<List<Reminder>> getPendingRemindersByEventType(
            @PathVariable EventType eventType) {
        return ResponseEntity.ok(reminderService.findPendingByEventType(eventType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reminder> getReminderById(@PathVariable Integer id) {
        return reminderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/pet/{petId}/user/{userId}")
    public ResponseEntity<?> createReminder(
            @RequestBody Reminder reminder,
            @PathVariable Integer petId,
            @PathVariable Integer userId) {
        try {
            Reminder createdReminder = reminderService.create(reminder, petId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReminder);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReminder(
            @PathVariable Integer id,
            @RequestBody Reminder reminder) {
        if (!id.equals(reminder.getId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "路径ID与提醒ID不匹配");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            Reminder updatedReminder = reminderService.update(reminder);
            return ResponseEntity.ok(updatedReminder);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> markReminderAsCompleted(@PathVariable Integer id) {
        try {
            Reminder completedReminder = reminderService.markAsCompleted(id);
            return ResponseEntity.ok(completedReminder);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReminder(@PathVariable Integer id) {
        try {
            reminderService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "删除提醒失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}