package com.dbmanagement.GymLife;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.entity.AccessLog;
import com.dbmanagement.GymLife.entity.BankAccount;
import com.dbmanagement.GymLife.entity.Equipment;
import com.dbmanagement.GymLife.entity.Manufacture;
import com.dbmanagement.GymLife.entity.Member;
import com.dbmanagement.GymLife.entity.Membership;
import com.dbmanagement.GymLife.entity.Transaction;
import com.dbmanagement.GymLife.entity.WorkSchedule;

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
			// updateBankAccount(appDAO); // remove associate member/manufacture, create new
			// bank account, associate to previous member/manufacture
			// deleteBankAccount(appDAO); // may not be available in the application
			// (restriction)
			// retrieveTransactionSend(appDAO);
			// retrieveTransactionReceive(appDAO);
			// findMemberOrManufactureByBankAccount(appDAO);

			// MANUFACTURE
			// createManufactureWithBankAccount(appDAO);
			// createBankAccountWithManufacture(appDAO);
			// findManufacture(appDAO);
			// updateManufacture(appDAO);
			// deleteManufacture(appDAO);

			// TRANSACTION
			// createTransaction(appDAO);
			// findTransactionById(appDAO);
			// findEquipmentFromTransaction(appDAO);
			// *once transaction has been created, cannot update, or delete it
			// *if gym want to reverse transaction then process a refund, add reverse
			// transaction

			// EQUIPMENT
			// createEquipment(appDAO);
			// findEquipment(appDAO);
			// updateEquipment(appDAO);
			// deleteEquipment(appDAO);

			// MEMBERSHIP
			// createMembership(appDAO);
			// findMembership(appDAO);
			// updateMembership(appDAO);
			// deleteMembership(appDAO);
			// findMembersFromMembership(appDAO);

			// MEMBER
			// createMember(appDAO);
			// findMember(appDAO);
			// updateMember(appDAO);
			// deleteMember(appDAO);
			// findAccessLogFromMember(appDAO);
			// findWorkSchedulesFromMember(appDAO);

			// ACCESS LOG
			// createAccessLog(appDAO);
			// findAccessLog(appDAO);
			// updateAccessLog(appDAO);

			// WORK SCHEDULE
			// createWorkSchedule(appDAO);
			// retrieveAllWorkSchedule(appDAO);
			// updateWorkSchedule(appDAO);
			// deleteWorkSchedule(appDAO);

			// TRAINING

		};
	}

	private void findWorkSchedulesFromMember(AppDAO appDAO) {
		// retrieve the correct member
		Member member = appDAO.findMemberById(1000004);

		// retrieve all work schedule
		appDAO.retrieveWorkSchedulesByMember(member);

		// display
		for (WorkSchedule ws : member.getWorkSchedules()) {
			System.out.println(ws);
		}
		System.out.println("Done.");
	}

	private void deleteWorkSchedule(AppDAO appDAO) {
		int id = 1;

		appDAO.deleteWorkScheduleById(id);
		System.out.println("Done.");
	}

	private void updateWorkSchedule(AppDAO appDAO) {
		// retrieve
		WorkSchedule ws = appDAO.findWorkScheduleById(1);
		// modify
		ws.setTimeEnd("23:00");

		// update to db
		appDAO.update(ws);
	}

	private void retrieveAllWorkSchedule(AppDAO appDAO) {
		List<WorkSchedule> ws = appDAO.retrieveAllWorkSchedule();

		for (WorkSchedule w : ws) {
			System.out.println(w);
		}

		System.out.println("Done.");

	}

	private void createWorkSchedule(AppDAO appDAO) {
		String workDate = "2023-07-28";

		Member member = appDAO.findMemberById(1000005);

		String timeStart = "15:00";
		String timeEnd = "19:00";

		WorkSchedule workSchedule = new WorkSchedule(workDate, member, timeStart, timeEnd);
		appDAO.save(workSchedule);

		System.out.println("Done.");
	}

	private void findAccessLogFromMember(AppDAO appDAO) {
		// retrieve correct member
		Member member = appDAO.findMemberById(1000001);

		// retrieve all access log associated
		appDAO.retrieveAccessLogsByMember(member);

		for (AccessLog a : member.getAccessLogs()) {
			System.out.println(a);
		}
		System.out.println("Done.");
	}

	private void updateAccessLog(AppDAO appDAO) {
		// retrieve
		AccessLog accessLog = appDAO.findAccessLogById(1);

		// modify
		accessLog.setTimeAccessOut("17:59");

		// save to db
		appDAO.update(accessLog);
	}

	private void findAccessLog(AppDAO appDAO) {
		int id = 1;
		AccessLog accessLog = appDAO.findAccessLogById(id);

		System.out.println(accessLog);
		System.out.println("Done.");
	}

	private void createAccessLog(AppDAO appDAO) {
		String date = "2023-07-31";

		// find the member
		Member member = appDAO.findMemberById(1000001);

		String timeAccessIn = "20:30";

		AccessLog accessLog = new AccessLog(date, member, timeAccessIn);
		appDAO.save(accessLog);

		System.out.println("Done.");

	}

	private void findMemberOrManufactureByBankAccount(AppDAO appDAO) {

		// retrieve the correct bank account
		String accountNumber = "12345678911234572";
		BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(accountNumber);

		// find an associate member (if any)
		Member member = appDAO.findMemberByBankAccount(bankAccount);
		if (member != null) {
			System.out.println(member);
		} else {
			// find an associate manufacture (if any)
			Manufacture manufacture = appDAO.findManufactureByBankAccount(bankAccount);
			if (manufacture != null) {
				System.out.println(manufacture);
			} else {
				System.out.println("No associate!");
			}
		}

		System.out.println("Done.");

	}

	private void findMembersFromMembership(AppDAO appDAO) {
		// retrieve the correct membership
		Membership membership = appDAO.findMembershipById(6);

		// retrieve all members
		appDAO.retrieveMembersByMembership(membership);

		// display
		for (Member m : membership.getMembers()) {
			System.out.println(m);
		}
		System.out.println("Done.");
	}

	private void deleteMember(AppDAO appDAO) {
		// retrieve
		int id = 1000003;

		// delete
		appDAO.deleteMemberById(id);

		System.out.println("Done.");
	}

	private void updateMember(AppDAO appDAO) {
		// retrieve
		int id = 1000001;
		Member member = appDAO.findMemberById(id);

		// modify/change
		// modify membership type: change to another type or set to null
		Membership membership = appDAO.findMembershipById(6);
		member.setMembershipType(membership);

		// modify bank account: change to another bank account
		BankAccount bankAccount = appDAO.findBankAccountByAccountNumber("12345678911234573");
		member.setBankAccountNumber(bankAccount);

		// update to db
		appDAO.update(member);
	}

	private void findMember(AppDAO appDAO) {
		int id = 1000001;

		Member member = appDAO.findMemberById(id);
		System.out.println(member);

		System.out.println("Done.");
	}

	private void createMember(AppDAO appDAO) {

		// create bank account for this member first
		String accountNumber = "12345678911234583";
		String bankName = "Wells Fargo";
		int routineNumber = 123456789;

		BankAccount bankAccount = new BankAccount(accountNumber, bankName, routineNumber);

		// create member
		String email = "testemail4@gmail.com";
		String userName = "testemail4";
		String password = "{bcrypt}abcdef123";
		String firstName = "Karen";
		String lastName = "West";
		String address = "3366Expresso Ct";
		String phoneNumber = "2097654689-1234";
		String dateOfBirth = "2000-12-16";
		String gender = "Male";

		String dateJoin = "2023-07-31";

		Member member = new Member(email, userName, password, firstName, lastName, address, phoneNumber, dateOfBirth,
				gender, dateJoin, bankAccount);

		// set membership type (an optional attribute)
		// (optional) (only required for gymmer, no need to manager, staff..)
		Membership membership = appDAO.findMembershipById(6);
		member.setMembershipType(membership);

		// save to db which will also save the bank account
		appDAO.save(member);
	}

	// MEMBERSHIP
	private void deleteMembership(AppDAO appDAO) {
		int id = 4;
		appDAO.deleteMembershipById(id);

		System.out.println("Done.");
	}

	private void updateMembership(AppDAO appDAO) {

		// retrieve
		Membership membership = appDAO.findMembershipById(6);

		// modify/change
		membership.setPrice(50);

		// update to db
		appDAO.update(membership);
		System.out.println("Done.");
	}

	private void findMembership(AppDAO appDAO) {
		int id = 4;

		Membership membership = appDAO.findMembershipById(id);

		System.out.println(membership);

		System.out.println("Done.");
	}

	private void createMembership(AppDAO appDAO) {
		String typeName = "Type 2";
		double price = 100;

		Membership membership = new Membership(typeName, price);
		appDAO.save(membership);

		System.out.println("Done.");
	}

	// EQUIPMENT
	private void findEquipmentFromTransaction(AppDAO appDAO) {

		// retrieve the transaction
		int transactionId = 7;
		Transaction transaction = appDAO.findTransactionById(transactionId);

		// check if it associates with an equipment
		try {
			System.out.println(transaction.getEquipment());
		} catch (Exception e) {
			// no equipment associates with the transaction
		}

		System.out.println("Done.");
	}

	private void deleteEquipment(AppDAO appDAO) {
		appDAO.deleteEquipmentBySerials("123456789");
	}

	private void updateEquipment(AppDAO appDAO) {
		// retrieve
		String serials = "123456789";
		Equipment equipment = appDAO.findEquipmentBySerials(serials);

		// adjust
		equipment.setTarget("Chest");

		// update to db
		appDAO.update(equipment);

		System.out.println("Done.");
	}

	private void findEquipment(AppDAO appDAO) {
		String serials = "123456789";
		Equipment equipment = appDAO.findEquipmentBySerials(serials);

		System.out.println(equipment);
		System.out.println("Done.");
	}

	private void createEquipment(AppDAO appDAO) {
		String serials = "123456789";
		String name = "Bench";

		// create a new transaction associate it this new equipment
		BankAccount gymAccount = appDAO.findBankAccountByAccountNumber("12345678911234572");
		BankAccount manufactureAccount = appDAO.findBankAccountByAccountNumber("12345678911234573");
		Transaction transaction = new Transaction(gymAccount, manufactureAccount, 80.0, "2023-07-30");

		// create
		Equipment equipment = new Equipment(serials, name, transaction);

		// save
		appDAO.save(equipment);

		System.out.println("Done.");
	}

	private void retrieveTransactionReceive(AppDAO appDAO) {
		// find the correct bank account
		BankAccount bankAccount = appDAO.findBankAccountByAccountNumber("12345678911234569");

		// set transaction receive to bank account
		appDAO.retrieveTransactionReceiveByBankAccount(bankAccount);

		// print out the result
		for (Transaction t : bankAccount.getTransactionReceive()) {
			System.out.println(t);
		}

		System.out.println("Done.");
	}

	private void retrieveTransactionSend(AppDAO appDAO) {
		// find the correct bank account
		BankAccount bankAccount = appDAO.findBankAccountByAccountNumber("12345678911234569");

		// set transaction send to bank account
		appDAO.retrieveTransactionSendByBankAccount(bankAccount);

		// print out the result
		for (Transaction t : bankAccount.getTransactionSend()) {
			System.out.println(t);
		}

		System.out.println("Done.");

	}

	private void findTransactionById(AppDAO appDAO) {
		int id = 1;
		Transaction t = appDAO.findTransactionById(id);
		System.out.println(t);

		System.out.println("Done.");
	}

	private void createTransaction(AppDAO appDAO) {
		BankAccount accountSend = appDAO.findBankAccountByAccountNumber("12345678911234573");
		BankAccount accountReceive = appDAO.findBankAccountByAccountNumber("12345678911234572");

		Transaction transaction = new Transaction(accountSend, accountReceive, 20.0, "2023-12-27");
		appDAO.save(transaction);
	}

	// delete manufacture will also delete its bank account if system has
	private void deleteManufacture(AppDAO appDAO) {
		int theId = 3;
		// find the Manufacture
		Manufacture manufacture = appDAO.findManufactureById(theId);

		// set the associate equipments with its manufacture_id to null?
		// find equipments
		// Manufacture manufacture = appDAO.findManufactureByBankAccount(bankAccount);
		// if (manufacture != null) {
		// // set its bankaccount to null
		// manufacture.setBankAccount(null);
		// appDAO.update(manufacture);
		// }

		System.out.println("Deleting manufacture: " + manufacture);
		appDAO.deleteManufactureById(theId);

		System.out.println("Done.");
	}

	private void updateManufacture(AppDAO appDAO) {
		// retrieve the correct manufacture
		Manufacture manufacture = appDAO.findManufactureById(2);

		// add bank account for this manufacture
		BankAccount bankAccount = appDAO.findBankAccountByAccountNumber("12345678911234570");
		manufacture.setBankAccount(bankAccount);

		// update the manufacture
		appDAO.update(manufacture);
	}

	private void findManufacture(AppDAO appDAO) {
		int theId = 2;

		System.out.println("Finding manufacture with Id: " + theId);
		Manufacture manufacture = appDAO.findManufactureById(theId);
		System.out.println(manufacture);

		System.out.println("Done.");
	}

	private void createBankAccountWithManufacture(AppDAO appDAO) {
		// create bank
		String accountNumber = "12345678911234571";
		String bankName = "Bank Of America";
		int routineNumber = 123456789;

		System.out.println("Saving account number: " + accountNumber);
		BankAccount bankAccount = new BankAccount(accountNumber, bankName, routineNumber);

		// create manufacture
		String name = "Encore Manufacture 2";
		String address = "8th Street, San Jose, CA, 95112";

		Manufacture manufacture = new Manufacture(name, address);
		manufacture.setBankAccount(bankAccount);

		bankAccount.setManufacture(manufacture);

		appDAO.save(bankAccount);

		System.out.println("Done.");
	}

	private void createManufactureWithBankAccount(AppDAO appDAO) {
		String name = "Encore Manufacture";
		String address = "8th Street, San Jose, CA, 95112";

		// create manufacture
		Manufacture manufacture = new Manufacture(name, address);

		// set a phone number for this manufacture
		String thisPhoneNumber = "5078674568-1234";
		manufacture.setPhoneNumber(thisPhoneNumber);

		// create a bank account for it
		String accountNumber = "12345678911234569";
		String bankName = "Bank Of America";
		int routineNumber = 123456789;
		BankAccount bankAccount = new BankAccount(accountNumber, bankName, routineNumber);

		// set this bank account for this manufacture
		manufacture.setBankAccount(bankAccount);

		// save this manufacture to database, this will also save the new bank account
		// because of the CASCADE set up
		appDAO.save(manufacture);

		System.out.println("Saving manufacture: " + manufacture);
		System.out.println("Done.");
	}

	private void deleteBankAccount(AppDAO appDAO) {
		String theAccountNumber = "12345678911234570";
		// find the bank account instance
		BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(theAccountNumber);

		// set the associate manufacture/member with bank account to null first
		// find manufacture
		// Manufacture manufacture = appDAO.findManufactureByBankAccount(bankAccount);
		Manufacture manufacture = bankAccount.getManufacture();
		if (manufacture != null) {
			// set its bankaccount to null
			manufacture.setBankAccount(null);
			appDAO.update(manufacture);
		}
		// if no manufacture, then FIND MEMBER
		else {
			// set its bankaccount to null
			// System.out.println("Null");
		}

		System.out.println("Deleting bank account: " + theAccountNumber);
		appDAO.deleteBankAccountByAccountNumber(theAccountNumber);

		System.out.println("Done.");
	}

	private void updateBankAccount(AppDAO appDAO) {

		// retrieve the bank account
		String theAccountNumber = "12345678911234571";
		System.out.println("Finding bank account: " + theAccountNumber);
		BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(theAccountNumber);

		// update new info
		// suppose we need to update routine number
		bankAccount.setRoutineNumber(123456788);

		appDAO.update(bankAccount);
	}

	private void findBankAccount(AppDAO appDAO) {
		String theAccountNumber = "12345678911234568";

		System.out.println("Finding bank account: " + theAccountNumber);
		BankAccount bankAccount = appDAO.findBankAccountByAccountNumber(theAccountNumber);
		System.out.println(bankAccount);

		// either check if it associate with a manufacture or a member
		System.out.println(bankAccount.getManufacture());

		System.out.println("Done.");
	}

	private void createBankAccount(AppDAO appDAO) {
		String accountNumber = "12345678911234573";
		String bankName = "Bank Of America";
		int routineNumber = 123456789;

		System.out.println("Saving account number: " + accountNumber);
		BankAccount bankAccount = new BankAccount(accountNumber, bankName, routineNumber);
		appDAO.save(bankAccount);

		System.out.println("Done.");

	}

}
