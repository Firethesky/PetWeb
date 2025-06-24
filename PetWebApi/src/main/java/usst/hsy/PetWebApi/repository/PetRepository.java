package usst.hsy.PetWebApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usst.hsy.PetWebApi.entity.Pet;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByUserId(Integer userId);
    List<Pet> findByUserIdAndIsActiveTrue(Integer userId);
    Long countByUserId(Integer userId);
} 