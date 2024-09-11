package org.example.passbatch.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

@Getter
@Setter
@ToString
public class JobLauncherRequest {
    private String name;
    private JobParameters jobParameters;

    public JobParameters getJobParameters() {
        return new JobParametersBuilder(this.jobParameters).toJobParameters();

    }

}
