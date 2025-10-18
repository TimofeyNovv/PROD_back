package com.example.prodBack8.config;

import com.example.prodBack8.model.entity.group.GroupEntity;
import com.example.prodBack8.model.entity.group.UsageLimit;
import com.example.prodBack8.model.entity.user.UserEntity;
import com.example.prodBack8.model.entity.user.UserRole;
import com.example.prodBack8.repository.GroupRepository;
import com.example.prodBack8.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createInitialData();
    }

    private void createInitialData() {
        // Создаем группу если ее нет
        GroupEntity group = createGroupIfNotExists();

        // Создаем пользователей с ЗАШИФРОВАННЫМИ паролями
        createUserIfNotExists("ad1", "tima", "nov", "tima", UserRole.ADMIN, null);
        createUserIfNotExists("std1", "gr", "sam", "tima", UserRole.STUDENT, group);
        createUserIfNotExists("std2", "sof", "shap", "tima", UserRole.STUDENT, group);
        createUserIfNotExists("std3", "dash", "non", "tima", UserRole.STUDENT, group);
        createUserIfNotExists("teach1", "gr", "smu", "tima", UserRole.TEACHER, group);

        log.info("Initial data loaded successfully");
    }

    private GroupEntity createGroupIfNotExists() {
        return groupRepository.findByName("Машинное обучение")
                .orElseGet(() -> {
                    UsageLimit usageLimit = UsageLimit.builder()
                            .maxSessionDurationMinutes(240)
                            .allowedDays("MON,FRI,SUN,SAT")
                            .dayStartTime("00:01")
                            .dayEndTime("23:59")
                            .build();

                    GroupEntity newGroup = GroupEntity.builder()
                            .name("Машинное обучение")
                            .GPUcount(4)
                            .currentGPUCount(16)
                            .distribution(25)
                            .usageLimit(usageLimit)
                            .build();

                    GroupEntity savedGroup = groupRepository.save(newGroup);
                    log.info("Created group: {}", savedGroup.getName());
                    return savedGroup;
                });
    }

    private void createUserIfNotExists(String username, String firstname,
                                       String lastname, String plainPassword,
                                       UserRole role, GroupEntity group) {
        if (userRepository.findByUsername(username).isEmpty()) {
            // Шифруем пароль перед сохранением
            String encodedPassword = passwordEncoder.encode(plainPassword);

            UserEntity user = UserEntity.builder()
                    .username(username)
                    .firstname(firstname)
                    .lastname(lastname)
                    .password(encodedPassword) // Сохраняем ЗАШИФРОВАННЫЙ пароль
                    .role(role)
                    .group(group)
                    .build();

            UserEntity savedUser = userRepository.save(user);
            log.info("Created user: {} with role: {}", savedUser.getUsername(), savedUser.getRole());
        }
    }
}