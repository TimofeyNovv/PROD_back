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
        GroupEntity group1 = createGroup1IfNotExists();
        GroupEntity group2 = createGroup2IfNotExists();
        GroupEntity group3 = createGroup3IfNotExists();

        // Создаем пользователей с ЗАШИФРОВАННЫМИ паролями
        createUserIfNotExists("ad1", "tima1", "nov1", "tima", UserRole.ADMIN, null, 240);
        createUserIfNotExists("std1", "gr1", "sam1", "tima", UserRole.STUDENT, group1, 240);
        createUserIfNotExists("std2", "sof1", "shap1", "tima", UserRole.STUDENT, group1, 240);
        createUserIfNotExists("std3", "dash1", "non1", "tima", UserRole.STUDENT, group1, 240);
        createUserIfNotExists("teach1", "gr1", "smu1", "tima", UserRole.TEACHER, group1, 240);

        createUserIfNotExists("ad2", "tima2", "nov2", "tima", UserRole.ADMIN, null,240);
        createUserIfNotExists("std4", "gr2", "sam2", "tima", UserRole.STUDENT, group2, 240);
        createUserIfNotExists("std5", "sof2", "shap2", "tima", UserRole.STUDENT, group2, 240);
        createUserIfNotExists("std6", "dash2", "non2", "tima", UserRole.STUDENT, group2,240);
        createUserIfNotExists("teach2", "gr2", "smu2", "tima", UserRole.TEACHER, group2, 240);

        createUserIfNotExists("ad3", "tima3", "nov3", "tima", UserRole.ADMIN, null, 240);
        createUserIfNotExists("std7", "gr3", "sam3", "tima", UserRole.STUDENT, group3, 240);
        createUserIfNotExists("std8", "sof3", "shap3", "tima", UserRole.STUDENT, group3, 240);
        createUserIfNotExists("std9", "dash3", "non3", "tima", UserRole.STUDENT, group3, 240);
        createUserIfNotExists("teach3", "gr3", "smu3", "tima", UserRole.TEACHER, group3,  240);


        log.info("Initial data loaded successfully");
    }

    private GroupEntity createGroup1IfNotExists() {
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
                            .membersCount(4)
                            .usageLimit(usageLimit)
                            .build();

                    GroupEntity savedGroup = groupRepository.save(newGroup);
                    log.info("Created group: {}", savedGroup.getName());
                    return savedGroup;
                });
    }
    private GroupEntity createGroup2IfNotExists() {
        return groupRepository.findByName("Искусственный интеллект")
                .orElseGet(() -> {
                    UsageLimit usageLimit = UsageLimit.builder()
                            .maxSessionDurationMinutes(240)
                            .allowedDays("MON,FRI,SUN,SAT")
                            .dayStartTime("00:01")
                            .dayEndTime("23:59")
                            .build();

                    GroupEntity newGroup = GroupEntity.builder()
                            .name("Искусственный интеллект")
                            .GPUcount(2)
                            .currentGPUCount(4)
                            .distribution(25)
                            .membersCount(4)
                            .usageLimit(usageLimit)
                            .build();

                    GroupEntity savedGroup = groupRepository.save(newGroup);
                    log.info("Created group: {}", savedGroup.getName());
                    return savedGroup;
                });
    }
    private GroupEntity createGroup3IfNotExists() {
        return groupRepository.findByName("Бекенд разработка")
                .orElseGet(() -> {
                    UsageLimit usageLimit = UsageLimit.builder()
                            .maxSessionDurationMinutes(240)
                            .allowedDays("MON,FRI,SUN,SAT")
                            .dayStartTime("00:01")
                            .dayEndTime("23:59")
                            .build();

                    GroupEntity newGroup = GroupEntity.builder()
                            .name("Бекенд разработка")
                            .GPUcount(4)
                            .currentGPUCount(16)
                            .distribution(25)
                            .membersCount(4)
                            .usageLimit(usageLimit)
                            .build();

                    GroupEntity savedGroup = groupRepository.save(newGroup);
                    log.info("Created group: {}", savedGroup.getName());
                    return savedGroup;
                });
    }



    private void createUserIfNotExists(String username, String firstname,
                                       String lastname, String plainPassword,
                                       UserRole role, GroupEntity group, Integer maxDur) {
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
                    .remainingUsageTimeGPU(maxDur)
                    .build();

            UserEntity savedUser = userRepository.save(user);
            log.info("Created user: {} with role: {}", savedUser.getUsername(), savedUser.getRole());
        }
    }
}