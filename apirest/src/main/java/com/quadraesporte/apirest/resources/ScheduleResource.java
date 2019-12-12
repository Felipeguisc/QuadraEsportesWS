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
		List<Schedule> lista = scheduleRepository.findAll();
		for (Schedule schedule : lista) {
			schedule.setSenha("");
		}
		return lista;
	}
	
	@GetMapping("/schedule/{id}")
	@ApiOperation(value="Retorna um agendamento espeífico da Quadra de Esportes")
	public Schedule listaScheduleUnico(@PathVariable(value="id") long id){
		Schedule schedule = scheduleRepository.findById(id);
		schedule.setSenha("");
		return schedule;
	}
	
	@PostMapping("/schedule")
	@ApiOperation(value="Salva um agendamento da Quadra de Esportes")
	public Schedule salvaSchedule(@RequestBody Schedule schedule) {
		schedule.setId(0);
		return scheduleRepository.save(schedule);
	}
	
	@DeleteMapping("/schedule")
	@ApiOperation(value="Deleta um agendamento na Quadra de Esportes, precisa apenas dos campos Id e Senha do Schedule.")
	public boolean deletaSchedule(@RequestBody Schedule schedule) {
		Schedule schedule_banco = scheduleRepository.findById(schedule.getId());
		if(schedule_banco.getSenha().equals(schedule.getSenha())) {
			scheduleRepository.delete(schedule);
			return true;
		}
		return false;
	}
	
	@DeleteMapping("/schedule/{id}/{senha}")
	@ApiOperation(value="Deleta um agendamento na Quadra de Esportes usando um id e a senha.")
	public boolean deletaScheduleId(@PathVariable(value="id") long id, @PathVariable(value="senha") String senha) {
		Schedule schedule = scheduleRepository.findById(id);
		if(schedule.getSenha().equals(senha)) {
			scheduleRepository.delete(schedule);
			return true;
		}
		return false;
	}
	
	@PutMapping("/schedule")
	@ApiOperation(value="Atualiza um agendamento na Quadra de Esportes")
	public Schedule atualizaSchedule(@RequestBody Schedule schedule) {
		Schedule schedule_banco = scheduleRepository.findById(schedule.getId());
		if(schedule_banco.getSenha().equals(schedule.getSenha())) {
			return scheduleRepository.save(schedule);
		}
		Schedule vazio = null;
		return vazio;
	}

}
