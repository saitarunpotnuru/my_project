package com.sshealthcare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sshealthcare.exception.InvalidIdException;
import com.sshealthcare.model.Admission;
import com.sshealthcare.model.Doctor;
import com.sshealthcare.repository.AdmissionRepository;

@Service
public class AdmissionService {
	
	@Autowired
	private AdmissionRepository admissionRepository;
	public Admission assignAdmission(Admission admission) {
		
		return admissionRepository.save(admission);
	}
	
	public Admission getById(int admissionId) throws InvalidIdException {
		Optional<Admission> optional =  admissionRepository.findById(admissionId);
		if(!optional.isPresent()){
			throw new InvalidIdException("Doctor ID Invalid");
		}
		
		return optional.get();
	}

	public List<Admission> getAlladmissions(Pageable pageable) {
		return admissionRepository.findAll(pageable).getContent();
	}
	
	public Admission getOne(int id) throws InvalidIdException{
		Optional<Admission> optional =  admissionRepository.findById(id);
		if(!optional.isPresent()){
			throw new InvalidIdException("Admission ID Invalid");
		}
		
		return optional.get();
	}

}
