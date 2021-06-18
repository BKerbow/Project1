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

import dev.kerbow.models.Author;
import dev.kerbow.utils.HibernateUtil;
import dev.kerbow.utils.JDBCConnection;

public class AuthorRepo implements GenericRepo<Author> {
	private Connection conn = JDBCConnection.getConnection();
	
	@Override
	public Author add(Author a) {
		Session s = HibernateUtil.getSession();
		
		try {
			s.beginTransaction();
			s.save(a);
			s.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		return a;
	}

	@Override
	public Author getById(Integer id) {
		
		//Get a Session from the Session Factory
		Session s = HibernateUtil.getSession();
				
		Author a = s.get(Author.class, id);
				
		//ALWAYS close your session
		s.close();
		return a;
	}
	
	public Author getByUsernameAndPassword(String username, String password) {
		Session s = HibernateUtil.getSession();
		Author a = null;
		
		try {
			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Author> cr = cb.createQuery(Author.class);
			Root<Author> root = cr.from(Author.class);
			
			Predicate p1 = cb.equal(root.get("name"), username);
			Predicate p2 = cb.equal(root.get("password"), password);
			
			cr.select(root).where(cb.and(p1, p2));
			a = s.createQuery(cr).getSingleResult();
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		return a;
	}

	@Override
	public Map<Integer, Author> getAll() {
		String sql = "select * from authors;";
		
		try {
			Map<Integer, Author> map = new HashMap<Integer, Author>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Author a = make(rs);
				map.put(a.getId(), a);
			}
			
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(Author a) {
		String sql = "update authors set bio = ?, points = ? where id = ? returning *;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, a.getBio());
			ps.setInt(2, a.getPoints());
			ps.setInt(3, a.getId());
			return ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean delete(Author a) {
		String sql = "delete from authors where id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getId());
			return ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Author make(ResultSet rs) throws SQLException {
		Author a = new Author();
		a.setId(rs.getInt("id"));
		a.setFirstName(rs.getString("first_name"));
		a.setLastName(rs.getString("last_name"));
		a.setBio(rs.getString("bio"));
		a.setPoints(rs.getInt("points"));
		a.setUsername(rs.getString("username"));
		a.setPassword(rs.getString("password"));
		return a;
	}
}
