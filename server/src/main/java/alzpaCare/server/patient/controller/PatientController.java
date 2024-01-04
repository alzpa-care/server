package alzpaCare.server.patient.controller;

import alzpaCare.server.patient.entity.Patient;
import alzpaCare.server.patient.mapper.PatientMapper;
import alzpaCare.server.patient.request.PatientRequest;
import alzpaCare.server.patient.response.PatientResponse;
import alzpaCare.server.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @PatchMapping
    public ResponseEntity<PatientResponse> patchPatient
            (@RequestBody @Valid PatientRequest patientRequest, Authentication authentication) {

        String username = authentication.getName();

        Patient patient = patientService.updatePatient(patientRequest, username);
        PatientResponse patientResponse = PatientMapper.toPatientResponse(patient);

        return ResponseEntity.ok(patientResponse);
    }

    @GetMapping
    public ResponseEntity<PatientResponse> getPatient(Authentication authentication) {

        String username = authentication.getName();

        Patient patient = patientService.findPatientByMember(username);
        PatientResponse patientResponse = PatientMapper.toPatientResponse(patient);

        return ResponseEntity.ok(patientResponse);
    }


}
