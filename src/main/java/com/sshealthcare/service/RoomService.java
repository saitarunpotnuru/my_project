package com.sshealthcare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sshealthcare.exception.InvalidIdException;
import com.sshealthcare.model.Receptionist;
import com.sshealthcare.model.Room;
import com.sshealthcare.repository.RoomRepository;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	public Room insert(Room room) {
		return roomRepository.save(room);
	}
	
	public Room getById(int rid) throws InvalidIdException {
		Optional<Room> optional =  roomRepository.findById(rid);
		if(!optional.isPresent()){
			throw new InvalidIdException("Room ID Invalid");
		}
		
		return optional.get();
	}
	
	public List<Room> getAllrooms(Pageable pageable) {
		return roomRepository.findAll(pageable).getContent();
	}
	

}
