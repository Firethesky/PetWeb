package usst.hsy.PetWebApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usst.hsy.PetWebApi.entity.HealthRecord;
import usst.hsy.PetWebApi.entity.HealthRecord.RecordType;
import usst.hsy.PetWebApi.service.HealthRecordService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/health-records")
@CrossOrigin
public class HealthRecordController {
    private final HealthRecordService healthRecordService;

    @Autowired
    public HealthRecordController(HealthRecordService healthRecordService) {
        this.healthRecordService = healthRecordService;
    }

    @GetMapping
    public ResponseEntity<List<HealthRecord>> getAllHealthRecords() {
        return ResponseEntity.ok(healthRecordService.findAll());
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<HealthRecord>> getHealthRecordsByPetId(@PathVariable Integer petId) {
        return ResponseEntity.ok(healthRecordService.findByPetId(petId));
    }

    @GetMapping("/pet/{petId}/type/{recordType}")
    public ResponseEntity<List<HealthRecord>> getHealthRecordsByPetIdAndType(
            @PathVariable Integer petId,
            @PathVariable RecordType recordType) {
        return ResponseEntity.ok(healthRecordService.findByPetIdAndRecordType(petId, recordType));
    }

    @GetMapping("/pet/{petId}/date-range")
    public ResponseEntity<List<HealthRecord>> getHealthRecordsByPetIdAndDateRange(
            @PathVariable Integer petId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(healthRecordService.findByPetIdAndDateRange(petId, startDate, endDate));
    }

    @GetMapping("/upcoming-due")
    public ResponseEntity<List<HealthRecord>> getUpcomingDueDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(healthRecordService.findUpcomingDueDates(startDate, endDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthRecord> getHealthRecordById(@PathVariable Integer id) {
        return healthRecordService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/pet/{petId}")
    public ResponseEntity<?> createHealthRecord(
            @RequestBody HealthRecord healthRecord,
            @PathVariable Integer petId) {
        try {
            HealthRecord createdRecord = healthRecordService.create(healthRecord, petId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRecord);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHealthRecord(
            @PathVariable Integer id,
            @RequestBody HealthRecord healthRecord) {
        if (!id.equals(healthRecord.getId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "路径ID与健康记录ID不匹配");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            HealthRecord updatedRecord = healthRecordService.update(healthRecord);
            return ResponseEntity.ok(updatedRecord);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHealthRecord(@PathVariable Integer id) {
        try {
            healthRecordService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "删除健康记录失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 