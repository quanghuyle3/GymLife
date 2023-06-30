package com.dbmanagement.GymLife;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.entity.BankAccount;

@SpringBootApplication
public class GymLifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymLifeApplication.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return runner -> {
			// BANK ACCOUNT
			// createBankAccount(appDAO);
			// findBankAccount(appDAO);
			deleteBankAccount(appDAO);
		};
	}

	private void deleteBankAccount(AppDAO appDAO) {
		String theAccountNumber = "12345678911234568";

		System.out.println("Deleting bank account: " + theAccountNumber);
		appDAO.deleteBankAccountByAccountNumber(theAccountNumber);

		System.out.println("Done.");
	}

	private void findBankAccount(AppDAO appDAO) {
		String theAccountNumber = "12345678911234568";

		System.out.println("Finding bank account: " + theAccountNumber);
		BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(theAccountNumber);
		System.out.println(bankAccount);

		System.out.println("Done.");
	}

	private void createBankAccount(AppDAO appDAO) {
		String accountNumber = "12345678911234568";
		String bankName = "Bank Of America";
		int routineNumber = 123456789;

		System.out.println("Saving account number: " + accountNumber);
		BankAccount bankAccount = new BankAccount(accountNumber, bankName, routineNumber);
		appDAO.save(bankAccount);

		System.out.println("Done.");

	}

}
