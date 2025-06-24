package usst.hsy.PetWebApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usst.hsy.PetWebApi.entity.Pet;
import usst.hsy.PetWebApi.service.PetService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin
public class PetController {
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.findAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Pet>> getPetsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(petService.findByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Integer id) {
        return petService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createPet(@RequestBody Pet pet, @PathVariable Integer userId) {
        try {
            Pet createdPet = petService.create(pet, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Integer id, @RequestBody Pet pet) {
        if (!id.equals(pet.getId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "路径ID与宠物ID不匹配");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            Pet updatedPet = petService.update(pet);
            return ResponseEntity.ok(updatedPet);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Integer id) {
        try {
            petService.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "宠物已成功删除（软删除）");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "删除宠物失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<?> permanentDeletePet(@PathVariable Integer id) {
        try {
            petService.hardDeleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "永久删除宠物失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/count/user/{userId}")
    public ResponseEntity<Map<String, Long>> countPetsByUserId(@PathVariable Integer userId) {
        Long count = petService.countByUserId(userId);
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
} 