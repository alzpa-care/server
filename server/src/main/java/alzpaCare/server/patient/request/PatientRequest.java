package alzpaCare.server.patient.request;

import alzpaCare.server.patient.entity.Patient;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Year;

public record PatientRequest(

        @Size(max = 20, message = "이름은 20글자 이내로 입력해주세요.")
        String patientName,

        @Size(max = 20, message = "병명은 20글자 이내로 입력해주세요.")
        String diseaseName,

        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "'YYYY-MM-DD' 형식으로 입력해주세요.")
        String birth,

        @Size(max = 20, message = "등급은 10글자 이내로 입력해주세요.")
        String grade,

        Year firstDiagnosisYear,

        Year diagnosisYear
){

        public Patient toEntity() {
            return Patient.builder()
                    .patientName(patientName())
                    .diseaseName(diseaseName())
                    .birth(birth())
                    .grade(grade())
                    .firstDiagnosisYear(firstDiagnosisYear())
                    .diagnosisYear(diagnosisYear())
                    .build();
        }


}
