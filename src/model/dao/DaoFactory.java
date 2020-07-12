package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

//Fabrica de DAO
//Tem opera��es staticas para instanciar os DAOs
public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
}
