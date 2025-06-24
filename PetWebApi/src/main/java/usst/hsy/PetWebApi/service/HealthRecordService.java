package usst.hsy.PetWebApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usst.hsy.PetWebApi.entity.HealthRecord;
import usst.hsy.PetWebApi.entity.HealthRecord.RecordType;
import usst.hsy.PetWebApi.entity.Pet;
import usst.hsy.PetWebApi.repository.HealthRecordRepository;
import usst.hsy.PetWebApi.repository.PetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HealthRecordService {
    private final HealthRecordRepository healthRecordRepository;
    private final PetRepository petRepository;

    @Autowired
    public HealthRecordService(HealthRecordRepository healthRecordRepository, PetRepository petRepository) {
        this.healthRecordRepository = healthRecordRepository;
        this.petRepository = petRepository;
    }

    public List<HealthRecord> findAll() {
        return healthRecordRepository.findAll();
    }

    public List<HealthRecord> findByPetId(Integer petId) {
        return healthRecordRepository.findByPetId(petId);
    }

    public List<HealthRecord> findByPetIdAndRecordType(Integer petId, RecordType recordType) {
        return healthRecordRepository.findByPetIdAndRecordType(petId, recordType);
    }

    public List<HealthRecord> findByPetIdAndDateRange(Integer petId, LocalDate startDate, LocalDate endDate) {
        return healthRecordRepository.findByPetIdAndRecordDateBetween(petId, startDate, endDate);
    }

    public List<HealthRecord> findUpcomingDueDates(LocalDate startDate, LocalDate endDate) {
        return healthRecordRepository.findByNextDueDateBetween(startDate, endDate);
    }

    public Optional<HealthRecord> findById(Integer id) {
        return healthRecordRepository.findById(id);
    }

    public HealthRecord save(HealthRecord healthRecord) {
        return healthRecordRepository.save(healthRecord);
    }

    public HealthRecord create(HealthRecord healthRecord, Integer petId) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            healthRecord.setPet(pet);
            return healthRecordRepository.save(healthRecord);
        } else {
            throw new RuntimeException("宠物不存在！");
        }
    }

    public HealthRecord update(HealthRecord healthRecord) {
        Optional<HealthRecord> optionalRecord = healthRecordRepository.findById(healthRecord.getId());
        if (optionalRecord.isPresent()) {
            HealthRecord existingRecord = optionalRecord.get();
            
            // 更新字段
            existingRecord.setTitle(healthRecord.getTitle());
            existingRecord.setRecordType(healthRecord.getRecordType());
            existingRecord.setDescription(healthRecord.getDescription());
            existingRecord.setRecordDate(healthRecord.getRecordDate());
            existingRecord.setVetClinic(healthRecord.getVetClinic());
            existingRecord.setNextDueDate(healthRecord.getNextDueDate());
            
            return healthRecordRepository.save(existingRecord);
        } else {
            throw new RuntimeException("健康记录不存在！");
        }
    }

    public void deleteById(Integer id) {
        healthRecordRepository.deleteById(id);
    }
} 