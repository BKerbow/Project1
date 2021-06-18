package dev.kerbow.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dev.kerbow.models.Editor;
import dev.kerbow.utils.HibernateUtil;
import dev.kerbow.utils.JDBCConnection;

public class EditorRepo implements GenericRepo<Editor> {
	private Connection conn = JDBCConnection.getConnection();

	@Override
	public Editor add(Editor e) {
		Session s = HibernateUtil.getSession();

		try {
			s.beginTransaction();
			s.save(e);
			s.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			s.close();
		}
		return null;
	}

	@Override
	public Editor getById(Integer id) {
		//Get a Session from the Session Factory
		Session s = HibernateUtil.getSession();

		Editor e = s.get(Editor.class, id);

		//ALWAYS close your session
		s.close();
		return e;
	}

	public Editor getByUsernameAndPassword(String username, String password) {
		Session s = HibernateUtil.getSession();
		Editor e = null;
		
		try {
			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Editor> cr = cb.createQuery(Editor.class);
			Root<Editor> root = cr.from(Editor.class);
			
			Predicate p1 = cb.equal(root.get("name"), username);
			Predicate p2 = cb.equal(root.get("password"), password);
			
			cr.select(root).where(cb.and(p1, p2));
			e = s.createQuery(cr).getSingleResult();
		} catch(HibernateException ex) {
			ex.printStackTrace();
		} finally {
			s.close();
		}
		return e;
	}

	@Override
	public Map<Integer, Editor> getAll() {

		String sql = "select * from editors;";

		try {
			Map<Integer, Editor> map = new HashMap<Integer, Editor>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Editor e = this.make(rs);
				map.put(e.getId(), e);
			}

			return map;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean update(Editor e) {
//		String sql = "";
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		}
		
		return false;
	}

	@Override
	public boolean delete(Editor e) {
		
		String sql = "delete from editors where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, e.getId());
			
			return ps.execute();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Editor make(ResultSet rs) throws SQLException {

		Editor e = new Editor();

		e.setId(rs.getInt("id"));
		e.setFirstName(rs.getString("first_name"));
		e.setLastName(rs.getString("last_name"));
		e.setUsername(rs.getString("username"));
		e.setPassword(rs.getString("password"));

		return e;
	}
}
