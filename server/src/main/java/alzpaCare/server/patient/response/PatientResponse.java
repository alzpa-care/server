package alzpaCare.server.patient.response;

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
