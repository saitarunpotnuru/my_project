package com.sshealthcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sshealthcare.exception.InvalidIdException;
import com.sshealthcare.model.Admission;
import com.sshealthcare.model.Doctor;
import com.sshealthcare.model.Patient;
import com.sshealthcare.model.Room;
import com.sshealthcare.service.AdmissionService;
import com.sshealthcare.service.DoctorService;
import com.sshealthcare.service.PatientService;
import com.sshealthcare.service.RoomService;

@RestController
@RequestMapping("/admission")
public class AdmissionController {

	@Autowired
	private RoomService roomService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private AdmissionService admissionService;
	
	

	// adding admissions with roomId, patientId, DoctorId
	@PostMapping("/add/{rid}/{patientId}/{did}")
	public ResponseEntity<?> assignAdmission(@PathVariable("rid") int rid, @PathVariable("patientId") int patientId,
			@PathVariable("did") int did, @RequestBody Admission admission) {

		// fetch room object from DB by roomId
		try {
			Room room = roomService.getById(rid);
			// attach room to admission
			admission.setRoom(room);
			
			// fetch patient object from DB by patientId
			Patient patient = patientService.getOne(patientId);
			
			// attach patient to admission
			admission.setPatient(patient);
			
			// fetch doctor object from DB by doctorId
			Doctor doctor = doctorService.getById(did);
			
			// attach doctor to admission
			admission.setDoctor(doctor);
			
			// save the product in DB
			admission = admissionService.save(admission);
			
			return ResponseEntity.ok().body(admission);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}

	}
	
	
	
	

	// getting all admissions
	@GetMapping("/all")
	public List<Admission> getAllAdmissions(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "1000000") Integer size) {

		Pageable pageable = PageRequest.of(page, size);
		return admissionService.getAlladmissions(pageable);
	}

	// getting admissions by Id
	@GetMapping("/getone/{id}")
	public ResponseEntity<?> getOne(@PathVariable("id") int id) {

		// fetch admission object using given admissionId
		try {
			Admission admission = admissionService.getOne(id);
			return ResponseEntity.ok().body(admission);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	// updating admissions by id
	@PutMapping("/update/{id}") // :update: which record to update? give me new value for update
	public ResponseEntity<?> updateAdmission(@PathVariable("id") int id, @RequestBody Admission newAdmission) {
		try {
			// validate id
			Admission Admission = admissionService.getOne(id);
			if (newAdmission.getDischargeDate() != null)
				Admission.setDischargeDate(newAdmission.getDischargeDate());
			if (newAdmission.getStatus() != null)
				Admission.setStatus(newAdmission.getStatus());

			Admission = admissionService.insertAdmission(Admission);
			return ResponseEntity.ok().body(Admission);

		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
