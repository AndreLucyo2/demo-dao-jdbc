package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao
{

	private Connection conn;

	// Força a injeção de dependencia:
	// Ao instanciar ja vem com a conexão definida:
	public SellerDaoJDBC(Connection conn)
	{
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller obj)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id)
	{

		PreparedStatement st = null;
		ResultSet rs = null;

		try
		{
			// Inicia o prepareStatement:
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			// Passa o id para consulta
			st.setInt(1, id);

			// Recebe o resultado:
			rs = st.executeQuery();

			// testa se retornou algum resultado:
			if (rs.next())
			{
				// Tranforma os dados da tabela em objetos
				// DEPARTAMENTO
				Department dep = instantiateDepartment(rs);

				// VENDEDOR:
				Seller obj = instantiateSeller(rs, dep);

				return obj;
			}

			// se nao vem resultado retorna null
			return null;
		}
		catch (SQLException e)// Captura e exceção de SQL
		{
			throw new DbException(e.getMessage());
		}
		finally
		{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	/**
	 * Passar os dados do resultset para o objeto, propaga a exceção caso de erro
	 * 
	 * @param rs
	 * @param dep
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException
	{
		
		// VENDEDOR
		Seller obj = new Seller();
		// Nome das colunas da consulta:
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	/**
	 * Passar os dados do resultset para o objeto, propaga a exceção caso de erro
	 * 
	 * @param rs
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	private Department instantiateDepartment(ResultSet rs) throws SQLException
	{
		// DEPARTAMENTO
		Department obj = new Department();
		// Nome das colunas da consulta
		obj.setId(rs.getInt("DepartmentId"));
		obj.setName(rs.getString("DepName"));
		return obj;
	}

	@Override
	public List<Seller> findAll()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
