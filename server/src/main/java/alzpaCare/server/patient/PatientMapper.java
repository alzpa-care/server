package alzpaCare.server.patient;

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
