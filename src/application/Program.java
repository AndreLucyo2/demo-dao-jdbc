package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args)
	{

		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println("=== TEST 1: seller metodo findById ========================================================");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		System.out.println("\n=== TEST 2: seller findByDepartment - lista vendedores por departamento =================");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		// Para cada seller obj na minha lista list imprime na tela o obj
		for (Seller obj : list)
		{
			System.out.println(obj);
		}

		System.out.println("\n=== TEST 3: seller findAll - Lista todos os VENDEDORES sem restrição ====================");
		list = sellerDao.findAll();
		// Para cada seller obj na minha lista list imprime na tela o obj
		for (Seller obj : list)
		{
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 4: seller insert ===============================================================");
		//Cria um objeto vendedor de teste:
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
		//Faz o insert:
		sellerDao.insert(newSeller);
		//Retorna o que foi inserido:
		System.out.println("Inserted! New id = " + newSeller.getId());
		

	}
}
