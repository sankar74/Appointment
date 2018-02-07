package com.app.appointment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.appointment.model.AppointmentDTO;
import com.app.appointment.service.AppointmentSvc;

@Controller
public class AppointmentController {
	
	@Autowired
	private AppointmentSvc appointmentSvc;
		
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String loadMainPage(){
		
		System.out.println("Load Main Page Call is made...");
		
		return "home";
	}
	

	
	@RequestMapping(value = "/db", method = RequestMethod.GET)
	public @ResponseBody String createSchema(){
		appointmentSvc.createSchema();
		return "Schema Created Successfully";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody String addAppointmentDtl(@RequestParam String date, @RequestParam String time, @RequestParam String desc){
		

		AppointmentDTO appointmentDTO = new AppointmentDTO();
		if((date != null && date != "") || (time != null && time != "") || (desc != null && desc != "")){
		appointmentDTO.setDate(date);
		appointmentDTO.setTime(time);
		appointmentDTO.setDesc(desc);
		appointmentSvc.saveAppointmentDtl(appointmentDTO);
		}else{
			return "Please enter the Appointment Details";
		}
		
		return "success";
	}
	
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody List<AppointmentDTO>  searAppointmentDtl(@RequestParam String searchParam){	
				
		List<AppointmentDTO> appointmentDTOs = appointmentSvc.getAppointmentDtl(searchParam);	
		
		System.out.println("searAppointmentDtl..."+searchParam);
		return appointmentDTOs;
	}

	public AppointmentSvc getAppointmentSvc() {
		return appointmentSvc;
	}

	public void setAppointmentSvc(AppointmentSvc appointmentSvc) {
		this.appointmentSvc = appointmentSvc;
	}
	

	
	
	

}
