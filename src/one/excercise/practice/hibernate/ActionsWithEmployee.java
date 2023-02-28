package one.excercise.practice.hibernate;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import one.excercise.practice.hibernate.entity.Employee;


public class ActionsWithEmployee
{
	private static int z = 1;
	
	static Scanner input = new Scanner(System.in);
	
	static SessionFactory factory = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(Employee.class)
			.buildSessionFactory();
	
	static Session session = factory.getCurrentSession();
	
	public static void main(String[] args) throws Exception
	{
		try 
		{
			while(z != 0) menu();
			
		}
		finally 
		{
			factory.close();
		}
	}
	
	public static void menu() throws Exception
	{
		System.out.println("\n-------------------------------");
		System.out.println("MENU\n[1]->Add new Employee.\n[2]->Read Employee data.\n[3]->Update Employee data."
							+ "\n[4]->Delete Employee.\n[5]->Quit.");
		System.out.println("-------------------------------\n");
		
		
		int a = input.nextInt();
		
		switch(a)
		{
			case 1:
				addNewEmployee();
				break;
			case 2:
				readEmployee();
				break;
			case 3:
				updateEmployee();
				break;
			case 4:
				deleteEmployee();
				break;
			default:
				System.out.println("\nYou've quit the EmployeeManager!");
				z=0;
				input.close();
				return;
		}
	}
	
	private static void addNewEmployee() 
	{
		session = factory.getCurrentSession();
		
		session.beginTransaction();
		
		System.out.println("Type employee's first name: ");
		String firstName = input.next();
		
		System.out.println("Type employee's last name: ");
		String lastName = input.next();
		
		System.out.println("Type employee's company: ");
		String company = input.next();
		
		Employee myEmployee = new Employee(firstName, lastName, company);
		
		System.out.println("Saving the employee object");
		session.save(myEmployee);
		
		session.getTransaction().commit();
		System.out.println("Employee added to the MySql table successfully!");

	}
	
	private static void readEmployee() 
	{
		session = factory.getCurrentSession();
		
		session.beginTransaction();
		
		List<Employee> theEmployees;
		
		System.out.println("Search employees by the company: ");
		String company = input.next();
		
		theEmployees = session.createQuery("FROM Employee e WHERE e.company='" + company + "'").getResultList();
		System.out.println("\n\nEmployees who works in " + company + " company:");
		displayStudents(theEmployees);
		
		session.getTransaction().commit();
	}
	
	
	private static void updateEmployee() 
	{
		session = factory.getCurrentSession();
		
		session.beginTransaction();
		
		List<Employee> theEmployees = session.createQuery("FROM Employee").getResultList();
		System.out.println("\nList of employees: \n");
		displayStudents(theEmployees);
		
		System.out.println("\nSelect with of the Employees you want to update by id: ");
		int id = input.nextInt();
		
		System.out.println("\nSelect what you want to update: ");
		String update = input.next();
		
		System.out.println("\nSelect to what you want to update " + update + ": ");
		String updated = input.next();
		
		session.createQuery("UPDATE Employee SET " + update + "='" + updated + "' WHERE id="+ id)
			.executeUpdate();
		
		session.getTransaction().commit();
	}
	
	private static void deleteEmployee()
	{
		session = factory.getCurrentSession();
		
		session.beginTransaction();
		
		List<Employee> theEmployees = session.createQuery("FROM Employee").getResultList();
		System.out.println("\nList of employees: \n");
		displayStudents(theEmployees);
		
		System.out.println("\nSelect with of the Employees you want to delete by id: ");
		int id = input.nextInt();
		
		session.createQuery("DELETE FROM Employee WHERE id=" +id)
			.executeUpdate();
		
		System.out.println("\nYou deleted Employee with id=" + id);
		
		session.getTransaction().commit();
	}
	
	private static void displayStudents(List<Employee> theEmployees)
	{
		for(Employee employee: theEmployees)
		{
			System.out.println(employee);
		}
	}
}
