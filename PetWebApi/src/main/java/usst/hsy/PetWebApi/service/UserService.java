package usst.hsy.PetWebApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usst.hsy.PetWebApi.entity.User;
import usst.hsy.PetWebApi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User register(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在！");
        }
        if (existsByEmail(user.getEmail())) {
            throw new RuntimeException("邮箱已被使用！");
        }
        // 实际应用中应该对密码进行加密处理
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    public Optional<User> login(String username, String password) {
        Optional<User> optionalUser = findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // 实际应用中应该使用passwordEncoder.matches(password, user.getPassword())
            if (password.equals(user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public User update(User user) {
        Optional<User> optionalUser = findById(user.getId());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // 检查用户名是否被其他用户占用
            if (!existingUser.getUsername().equals(user.getUsername()) && 
                existsByUsername(user.getUsername())) {
                throw new RuntimeException("用户名已存在！");
            }

            // 检查邮箱是否被其他用户占用
            if (!existingUser.getEmail().equals(user.getEmail()) && 
                existsByEmail(user.getEmail())) {
                throw new RuntimeException("邮箱已被使用！");
            }

            // 更新字段
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setNickname(user.getNickname());
            existingUser.setAvatarUrl(user.getAvatarUrl());
            
            // 密码只有在提供时才更新
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                // 实际应用中应该对密码进行加密处理
                // existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                existingUser.setPassword(user.getPassword());
            }

            return save(existingUser);
        } else {
            throw new RuntimeException("用户不存在！");
        }
    }
} 