package usst.hsy.PetWebApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usst.hsy.PetWebApi.entity.Pet;
import usst.hsy.PetWebApi.entity.User;
import usst.hsy.PetWebApi.repository.PetRepository;
import usst.hsy.PetWebApi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Autowired
    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public List<Pet> findByUserId(Integer userId) {
        return petRepository.findByUserIdAndIsActiveTrue(userId);
    }

    public Optional<Pet> findById(Integer id) {
        return petRepository.findById(id);
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet create(Pet pet, Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            pet.setUser(user);
            pet.setIsActive(true);
            return petRepository.save(pet);
        } else {
            throw new RuntimeException("用户不存在！");
        }
    }

    public Pet update(Pet pet) {
        Optional<Pet> optionalPet = petRepository.findById(pet.getId());
        if (optionalPet.isPresent()) {
            Pet existingPet = optionalPet.get();
            
            // 更新可修改的字段
            existingPet.setName(pet.getName());
            existingPet.setSpecies(pet.getSpecies());
            existingPet.setBreed(pet.getBreed());
            existingPet.setGender(pet.getGender());
            existingPet.setBirthDate(pet.getBirthDate());
            existingPet.setAvatarUrl(pet.getAvatarUrl());
            existingPet.setNotes(pet.getNotes());
            
            return petRepository.save(existingPet);
        } else {
            throw new RuntimeException("宠物不存在！");
        }
    }

    public void deleteById(Integer id) {
        // 软删除
        Optional<Pet> optionalPet = petRepository.findById(id);
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            pet.setIsActive(false);
            petRepository.save(pet);
        } else {
            throw new RuntimeException("宠物不存在！");
        }
    }

    // 硬删除（如有需要）
    public void hardDeleteById(Integer id) {
        petRepository.deleteById(id);
    }

    public Long countByUserId(Integer userId) {
        return petRepository.countByUserId(userId);
    }
} 