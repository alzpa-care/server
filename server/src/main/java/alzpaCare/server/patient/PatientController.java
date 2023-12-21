package alzpaCare.server.patient;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @PatchMapping
    public ResponseEntity<PatientResponse> patchPatient
            (@RequestBody @Valid PatientRequest patientRequest, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Patient patient = patientService.updatePatient(patientRequest, username);
        PatientResponse patientResponse = PatientMapper.toPatientResponse(patient);

        return new ResponseEntity<>(patientResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PatientResponse> getPatient(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Patient patient = patientService.findPatientByMember(username);

        PatientResponse patientResponse = PatientMapper.toPatientResponse(patient);

        return ResponseEntity.ok(patientResponse);
    }


}
