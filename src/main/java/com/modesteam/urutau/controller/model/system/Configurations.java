package com.modesteam.urutau.controller.model.system;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.modesteam.urutau.controller.model.Administrator;

public class Configurations {
	@Id
	private Long id;
	private String registerType;
	private List<Administrator> corcernedAdministrator;

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public List<Administrator> getCorcernedAdministrator() {
		return corcernedAdministrator;
	}

	public void setCorcernedAdministrator(
			List<Administrator> corcernedAdministrator) {
		this.corcernedAdministrator = corcernedAdministrator;
	}

}
