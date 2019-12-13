package com.quadraesporte.apirest.resources;

import java.sql.Timestamp;
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
@RequestMapping(value = "/api")
@Api(value = "API REST Schedules")
@CrossOrigin(origins = "*")
public class ScheduleResource {

	@Autowired
	ScheduleRepository scheduleRepository;

	@GetMapping("/schedules")
	@ApiOperation(value = "Retorna uma lista com os agendamendos da Quadra de Esportes")
	public List<Schedule> listaSchedules() {
		List<Schedule> lista = scheduleRepository.findAll();
		for (Schedule schedule : lista) {
			schedule.setSenha("");
		}
		return lista;
	}

	@GetMapping("/schedule/{id}")
	@ApiOperation(value = "Retorna um agendamento espeífico da Quadra de Esportes")
	public Schedule listaScheduleUnico(@PathVariable(value = "id") long id) {
		Schedule schedule = scheduleRepository.findById(id);
		schedule.setSenha("");
		return schedule;
	}

	@PostMapping("/schedule")
	@ApiOperation(value = "Salva um agendamento da Quadra de Esportes, faz verificação de erros,"
			+ " caso ocorra retorna um objeto com o campo nome=error e a descrição do erro no campo mensagem.")
	public Schedule salvaSchedule(@RequestBody Schedule schedule) {
		Schedule vazio = new Schedule();
		List<Schedule> lista = scheduleRepository.findAll();
		// Define o id para zero pra gerar um id aleatório
		schedule.setId(0);
		// Recebe o horário atual para comparar com o horário marcado para o evento
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		// Verifica se os horários são válidos
		if (schedule.getHora_inicio().before(timestamp) || schedule.getHora_fim().before(schedule.getHora_inicio())) {
			vazio.setNome("error");
			vazio.setMensagem("Horário inválido");
			return vazio;
		}
		for (Schedule lschedule : lista) {
			if (schedule.getHora_inicio().after(lschedule.getHora_inicio())
					&& schedule.getHora_inicio().before(lschedule.getHora_fim())) {
				vazio.setNome("error");
				vazio.setMensagem("Horário de inicío inválido, outro evento já está programado neste horário!");
				return vazio;
			}
			if (schedule.getHora_fim().after(lschedule.getHora_inicio())
					&& schedule.getHora_fim().before(lschedule.getHora_fim())) {
				vazio.setNome("error");
				vazio.setMensagem("Horário de fim inválido, outro evento já está programado neste horário!");
				return vazio;
			}
			for (long i = schedule.getHora_inicio().getTime(); i < schedule.getHora_fim().getTime(); i += 60000) {
				if (lschedule.getHora_inicio().getTime() < i && i < lschedule.getHora_fim().getTime()) {
					vazio.setNome("error");
					vazio.setMensagem("Horário inválido, outro evento já está programado no intervalo deste horário!");
					return vazio;
				}
			}
		}
		return scheduleRepository.save(schedule);
	}

	@DeleteMapping("/schedule")
	@ApiOperation(value = "Deleta um agendamento na Quadra de Esportes, precisa apenas dos campos Id e Senha do Schedule.")
	public void deletaSchedule(@RequestBody Schedule schedule) {
		Schedule schedule_banco = scheduleRepository.findById(schedule.getId());
		if (schedule_banco.getSenha().equals(schedule.getSenha())) {
			scheduleRepository.delete(schedule);
		}
	}

	@DeleteMapping("/schedule/{id}/{senha}")
	@ApiOperation(value = "Deleta um agendamento na Quadra de Esportes usando um id e a senha.")
	public void deletaScheduleId(@PathVariable(value = "id") long id, @PathVariable(value = "senha") String senha) {
		Schedule schedule = scheduleRepository.findById(id);
		if (schedule.getSenha().equals(senha)) {
			scheduleRepository.delete(schedule);
		}
	}

	@PutMapping("/schedule")
	@ApiOperation(value = "Atualiza um agendamento na Quadra de Esportes")
	public Schedule atualizaSchedule(@RequestBody Schedule schedule) {
		Schedule vazio = new Schedule();
		List<Schedule> lista = scheduleRepository.findAll();
		Schedule schedule_banco = scheduleRepository.findById(schedule.getId());
		// Verifica se a senha passada é igual a senha salva no banco
		if (!schedule_banco.getSenha().equals(schedule.getSenha())) {
			vazio.setNome("error");
			vazio.setMensagem("Senha inválida!");
			return vazio;
		}
		// Recebe o horário atual para comparar com o horário marcado para o evento
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		// Verifica se os horários são válidos
		if (schedule.getHora_inicio().before(timestamp) || schedule.getHora_fim().before(schedule.getHora_inicio())) {
			vazio.setNome("error");
			vazio.setMensagem("Horário inválido");
			return vazio;
		}
		for (Schedule lschedule : lista) {
			// Se o objeto da lista for o próprio horario, pula a verificação
			if (schedule.getId() != lschedule.getId()) {
				if (schedule.getHora_inicio().after(lschedule.getHora_inicio())
						&& schedule.getHora_inicio().before(lschedule.getHora_fim())) {
					vazio.setNome("error");
					vazio.setMensagem("Horário de inicío inválido, outro evento já está programado neste horário!");
					return vazio;
				}
				if (schedule.getHora_fim().after(lschedule.getHora_inicio())
						&& schedule.getHora_fim().before(lschedule.getHora_fim())) {
					vazio.setNome("error");
					vazio.setMensagem("Horário de fim inválido, outro evento já está programado neste horário!");
					return vazio;
				}
				for (long i = schedule.getHora_inicio().getTime(); i < schedule.getHora_fim().getTime(); i += 60000) {
					if (lschedule.getHora_inicio().getTime() < i && i < lschedule.getHora_fim().getTime()) {
						vazio.setNome("error");
						vazio.setMensagem(
								"Horário inválido, outro evento já está programado no intervalo deste horário!");
						return vazio;
					}
				}
			}
		}
		return scheduleRepository.save(schedule);
	}

}
