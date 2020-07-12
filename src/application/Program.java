package application;

import java.sql.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program
{

	public static void main(String[] args)
	{
		Department obj = new Department(1, "Books");

		Seller newSeller = new Seller(21, "Bob", "bob@gmail.com", new Date(0), 4000.0, obj);

		// Chama o metodo estatico:
		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println("\n===  DEPARTAMENTO  ==============================================================");

		System.out.println(obj);

		System.out.println("\n===  VENDEDOR  ==================================================================");

		System.out.println(newSeller);
	}
}
