package com.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@NoArgsConstructor@ToString@AllArgsConstructor
public class LoginReturn {
	int id;
	int accountType;
	String errorMessage;
}
