package com.modesteam.urutau;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.modesteam.urutau.model.User;

@SessionScoped
@Named("userManager")
public class UserManager implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private User userLogged;
	
	/**
	 * Saves user in session
	 * 
	 * @param user to be save in session
	 */
	public void login(User user){
		setUserLogged(user);
	}
	
	/**
	 * Destroy userLogged.Makes possible the logging out. 
	 */
	public void logout(){
		setUserLogged(null);
	}

	public User getUserLogged() {
		if(userLogged == null) {
			throw new NullPointerException("Don't have user in session!");
		} else {
			return userLogged;
		}
	}

	public void setUserLogged(User userLogged) {
		this.userLogged = userLogged;
	}

	public boolean isLogged(){
		boolean isLogged = false;
		isLogged = (userLogged != null);
		return isLogged;
	}

}
