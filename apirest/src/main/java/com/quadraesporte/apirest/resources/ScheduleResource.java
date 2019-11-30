package com.quadraesporte.apirest.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quadraesporte.apirest.models.Schedule;
import com.quadraesporte.apirest.repository.ScheduleRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value="API REST Schedules")
@CrossOrigin(origins="*")
public class ScheduleResource {
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	@GetMapping("/schedules")
	@ApiOperation(value="Retorna uma lista com os agendamendos da Quadra de Esportes")
	public List<Schedule> listaSchedules(){
		return scheduleRepository.findAll();
	}
	
	@GetMapping("/schedule/{id}")
	@ApiOperation(value="Retorna um agendamento espeífico da Quadra de Esportes")
	public Schedule listaScheduleUnico(@PathVariable(value="id") long id){
		return scheduleRepository.findById(id);
	}
	
	@PostMapping("/schedule")
	@ApiOperation(value="Salva um agendamento da Quadra de Esportes")
	public Schedule salvaSchedule(@RequestBody Schedule schedule) {
		return scheduleRepository.save(schedule);
	}
	
	@DeleteMapping("/schedule")
	@ApiOperation(value="Deleta um agendamento na Quadra de Esportes")
	public void deletaSchedule(@RequestBody Schedule schedule) {
		scheduleRepository.delete(schedule);
	}
	
	@PutMapping("/schedule")
	@ApiOperation(value="Atualiza um agendamento na Quadra de Esportes")
	public Schedule atualizaSchedule(@RequestBody Schedule schedule) {
		return scheduleRepository.save(schedule);
	}

}
