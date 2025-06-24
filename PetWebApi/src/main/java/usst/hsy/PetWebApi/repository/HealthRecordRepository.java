package usst.hsy.PetWebApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usst.hsy.PetWebApi.entity.HealthRecord;
import usst.hsy.PetWebApi.entity.HealthRecord.RecordType;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Integer> {
    List<HealthRecord> findByPetId(Integer petId);
    List<HealthRecord> findByPetIdAndRecordType(Integer petId, RecordType recordType);
    List<HealthRecord> findByPetIdAndRecordDateBetween(Integer petId, LocalDate startDate, LocalDate endDate);
    List<HealthRecord> findByNextDueDateBetween(LocalDate startDate, LocalDate endDate);
} 