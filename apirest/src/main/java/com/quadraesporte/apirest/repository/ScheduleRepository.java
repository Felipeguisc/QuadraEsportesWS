package com.quadraesporte.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quadraesporte.apirest.models.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
	
	Schedule findById(long id);

}
