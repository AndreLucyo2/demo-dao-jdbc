package model.dao;

import model.dao.impl.SellerDaoJDBC;

//Fabrica de DAO
//Tem operações staticas para instanciar os DAOs
public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC();
	}
	
}
