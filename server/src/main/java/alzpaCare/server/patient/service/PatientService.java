package alzpaCare.server.patient.service;

import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import alzpaCare.server.patient.entity.Patient;
import alzpaCare.server.patient.repository.PatientRepository;
import alzpaCare.server.patient.request.PatientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public Patient updatePatient(PatientRequest patientRequest, String email) {
        Patient patient = patientRepository.findByMemberEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PATIENT_NOT_FOUND));

        Optional.ofNullable(patientRequest.patientName())
                .ifPresent(patient::setPatientName);
        Optional.ofNullable(patientRequest.diseaseName())
                .ifPresent(patient::setDiseaseName);
        Optional.ofNullable(patientRequest.birth())
                .ifPresent(patient::setBirth);
        Optional.ofNullable(patientRequest.grade())
                .ifPresent(patient::setGrade);
        Optional.ofNullable(patientRequest.firstDiagnosisYear())
                .ifPresent(patient::setFirstDiagnosisYear);
        Optional.ofNullable(patientRequest.diagnosisYear())
                .ifPresent(patient::setDiagnosisYear);

        return patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public Patient findPatientByMember(String email) {
        return patientRepository.findByMemberEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PATIENT_NOT_FOUND));

    }


}
