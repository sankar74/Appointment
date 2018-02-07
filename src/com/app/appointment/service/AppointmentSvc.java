package com.app.appointment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.appointment.dao.AppointmentDao;
import com.app.appointment.model.AppointmentDTO;

@Service
public class AppointmentSvc {
	
	@Autowired
	private AppointmentDao appointmentDao;

	public void saveAppointmentDtl(AppointmentDTO appointmentDTO){
		try{
		appointmentDao.saveAppointmentDtl(appointmentDTO);
		}catch(Exception exception){
			System.out.println(exception);
		}
		
	}

	public List<AppointmentDTO> getAppointmentDtl(String searchParam) {
		
		List<AppointmentDTO> appointmentDTOs = appointmentDao.getAppointmentDtl(searchParam);
		
		for(AppointmentDTO appointmentDTO : appointmentDTOs){
			if(appointmentDTO.getDate() != null && appointmentDTO.getDate() != ""){
			String[] dateTime = appointmentDTO.getDate().split("#");			
			if(dateTime.length == 2){
			appointmentDTO.setDate(dateTime[0]);
			appointmentDTO.setTime(dateTime[1]);
			}else if(dateTime.length == 1){
				appointmentDTO.setDate(dateTime[0]);
			}
			}
		}
		
		return appointmentDTOs;
	}

	public AppointmentDao getAppointmentDao() {
		return appointmentDao;
	}

	public void setAppointmentDao(AppointmentDao appointmentDao) {
		this.appointmentDao = appointmentDao;
	}

	public void createSchema() {
		appointmentDao.createSchema();
		
	}
	
	

}
