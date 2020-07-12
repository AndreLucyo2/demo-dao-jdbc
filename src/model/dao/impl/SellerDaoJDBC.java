package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

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
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
			+ "FROM seller INNER JOIN department "
			+ "ON seller.DepartmentId = department.Id "
			+ "WHERE seller.Id = ?");

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
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{
			st = conn.prepareStatement(
			"SELECT seller.*,department.Name as DepName "
			+ "FROM seller INNER JOIN department "
			+ "ON seller.DepartmentId = department.Id "
			+ "ORDER BY Name");

			rs = st.executeQuery();

			// Declaração das listas:
			List<Seller> list = new ArrayList<>();

			// Ver seção 19:
			// Extrutura Map de chave e valor: Vazia
			// Chave=Id e valor= obj departamento
			// Adiciona uma chave e um obj departamento a cada loop
			// não permite repetição:
			Map<Integer, Department> map = new HashMap<>();

			// Percorre enquanto tiver valores:
			while (rs.next())
			{
				// Controle para não repetir o departamento:
				// Isso é necessario nao que seja instanciado na memoria apenas um obj, e os varios vendedores
				// aponem para o mesmo
				// caso tenha mais de um vendedor para o mesmo departamento
				// Testa se o pdepartamento ja exite: Pega o Id do ResultSet: se não existe ainda, retorna null
				Department dep = map.get(rs.getInt("DepartmentId"));
				// se for null:
				if (dep == null)
				{
					// Instancia o Departamento caso ainda nao esteja o Map:
					dep = instantiateDepartment(rs);

					// Salva o obj no Map com chave, para que da proxima vez no teste, valide que ja existe este Id
					map.put(rs.getInt("DepartmentId"), dep);

				}

				// instancia apenas um departamento para varios vendedores:
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);

			}
			
			return list;
		}
		catch (SQLException e)
		{
			throw new DbException(e.getMessage());
		}
		finally
		{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Seller> findByDepartment(Department department)
	{

		PreparedStatement st = null;
		ResultSet rs = null;

		try
		{

			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
			+ "FROM seller INNER JOIN department "
			+ "ON seller.DepartmentId = department.Id "
			+ "WHERE DepartmentId = ? "
			+ "ORDER BY Name");

			st.setInt(1, department.getId());

			rs = st.executeQuery();

			// Declaração das listas:
			List<Seller> list = new ArrayList<>();

			// Ver seção 19:
			// Extrutura Map de chave e valor: Vazia
			// Chave=Id e valor= obj departamento
			// Adiciona uma chave e um obj departamento a cada loop
			// não permite repetição:
			Map<Integer, Department> map = new HashMap<>();

			// Percorre enquanto tiver valores:
			while (rs.next())
			{
				// Controle para não repetir o departamento:
				// Isso é necessario nao que seja instanciado na memoria apenas um obj, e os varios vendedores
				// aponem para o mesmo
				// caso tenha mais de um vendedor para o mesmo departamento
				// Testa se o pdepartamento ja exite: Pega o Id do ResultSet: se não existe ainda, retorna null
				Department dep = map.get(rs.getInt("DepartmentId"));
				// se for null:
				if (dep == null)
				{
					// Instancia o Departamento caso ainda nao esteja o Map:
					dep = instantiateDepartment(rs);

					// Salva o obj no Map com chave, para que da proxima vez no teste, valide que ja existe este Id
					map.put(rs.getInt("DepartmentId"), dep);

				}

				// instancia apenas um departamento para varios vendedores:
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);

			}

			return list;

		}
		catch (SQLException e)
		{

			throw new DbException(e.getMessage());

		}
		finally
		{

			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}

	}

}
