package alzpaCare.server.patient;

import java.time.Year;

public record PatientResponse(

        String patientName,

        String diseaseName,

        String birth,

        String grade,

        Year firstDiagnosisYear,

        Year diagnosisYear
) {
}
