package org.example.passbatch.job.pass;

import lombok.extern.slf4j.Slf4j;
import org.example.passbatch.config.TestBatchConfig;
import org.example.passbatch.repository.pass.BulkPassEntity;
import org.example.passbatch.repository.pass.BulkPassRepository;
import org.example.passbatch.repository.pass.BulkPassStatus;
import org.example.passbatch.repository.user.UserGroupMappingEntity;
import org.example.passbatch.repository.user.UserGroupMappingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {AddPassesJobConfig.class, TestBatchConfig.class, AddPassesTasklet.class})
public class AddPassesJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private BulkPassRepository bulkPassRepository;

    @Autowired
    private UserGroupMappingRepository userGroupMappingRepository;


    @Test
    public void test_addPassesJob() throws Exception {
        // given
        addBulkPassEntity();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobInstance jobInstance = jobExecution.getJobInstance();

        // then
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        assertEquals("addPassesJob", jobInstance.getJobName());

    }

    private void addBulkPassEntity() {
        final LocalDateTime now = LocalDateTime.now();
        final String userGroupId = RandomStringUtils.randomAlphabetic(6);
        final String userId = "A100" + RandomStringUtils.randomNumeric(4);

        BulkPassEntity bulkPassEntity = new BulkPassEntity();
        bulkPassEntity.setPackageSeq(1);
        bulkPassEntity.setUserGroupId(userGroupId);
        bulkPassEntity.setStatus(BulkPassStatus.READY);
        bulkPassEntity.setCount(10);
        bulkPassEntity.setStartedAt(now);
        bulkPassEntity.setEndedAt(now.plusDays(60));

        bulkPassRepository.save(bulkPassEntity);

        UserGroupMappingEntity userGroupMappingEntity = new UserGroupMappingEntity();
        userGroupMappingEntity.setUserGroupId(userGroupId);
        userGroupMappingEntity.setUserId(userId);
        userGroupMappingEntity.setUserGroupName("그룹");
        userGroupMappingEntity.setDescription("그룹 설명");

        userGroupMappingRepository.save(userGroupMappingEntity);

    }
}