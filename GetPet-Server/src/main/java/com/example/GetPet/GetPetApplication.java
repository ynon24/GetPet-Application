package com.example.GetPet;

import com.example.GetPet.database.DatabaseHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GetPetApplication {

	public static void main(String[] args) {
		try {
			DatabaseHelper db = new DatabaseHelper();
			db.createDB();
			SpringApplication.run(GetPetApplication.class, args);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
