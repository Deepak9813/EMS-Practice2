package com.dpk.Practice1.service;

import com.dpk.Practice1.model.User;

public interface UserService {

	void userSignup(User user);

	User userLogin(String email, String psw);

	User getUserById(int id);

	User getUserByEmail(String email);

	User getUserByPhone(String phoneNumber);

	// ====== for forgot[change] password ==========
	void updateUser(User user);

}
