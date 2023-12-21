package alzpaCare.server.patient.mapper;

import alzpaCare.server.patient.response.PatientResponse;
import alzpaCare.server.patient.entity.Patient;

public class PatientMapper {

    public static PatientResponse toPatientResponse(Patient patient) {
        return new PatientResponse(
                patient.getPatientName(),
                patient.getDiseaseName(),
                patient.getBirth(),
                patient.getGrade(),
                patient.getFirstDiagnosisYear(),
                patient.getDiagnosisYear()
        );
    }


}
