package com.quadraesporte.apirest.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quadraesporte.apirest.models.Schedule;
import com.quadraesporte.apirest.repository.ScheduleRepository;

@RestController
@RequestMapping(value="/api")
public class ScheduleResource {
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	@GetMapping("/schedules")
	public List<Schedule> listaSchedules(){
		return scheduleRepository.findAll();
	}
	
	@GetMapping("/schedule/{id}")
	public Schedule listaScheduleUnico(@PathVariable(value="id") long id){
		return scheduleRepository.findById(id);
	}
	
	@PostMapping("/schedule")
	public Schedule salvaSchedule(@RequestBody Schedule schedule) {
		return scheduleRepository.save(schedule);
	}

}
