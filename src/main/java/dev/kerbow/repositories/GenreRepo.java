package dev.kerbow.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dev.kerbow.models.Author;
import dev.kerbow.models.Editor;
import dev.kerbow.models.Genre;
import dev.kerbow.utils.HibernateUtil;
import dev.kerbow.utils.JDBCConnection;

public class GenreRepo implements GenericRepo<Genre>{
	private Connection conn = JDBCConnection.getConnection();

	@Override
	public Genre add(Genre g) {
		Session s = HibernateUtil.getSession();

		try {
			s.beginTransaction();
			s.save(g);
			s.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		return g;
	}

	@Override
	public Genre getById(Integer id) {
		//Get a Session from the Session Factory
		Session s = HibernateUtil.getSession();

		Genre g = s.get(Genre.class, id);

		//ALWAYS close your session
		s.close();
		return g;
	}

	public Genre getByName(String name) {
		Session s = HibernateUtil.getSession();
		Genre g = null;
		
		try {
			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Genre> cr = cb.createQuery(Genre.class);
			Root<Genre> root = cr.from(Genre.class);
			
			//cr.select(root).where(cb(root.get("name"), name));
			//g = s.createQuery(cr).getSingleResult();
		}catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		return g;
	}

	@Override
	public Map<Integer, Genre> getAll() {

		String sql = "select * from genres;";

		try {
			Map<Integer, Genre> map = new HashMap<Integer, Genre>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Genre g = this.make(rs);
				map.put(g.getId(), g);
			}

			return map;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean update(Genre g) {
		
		String sql = "update genres set senior_editor = ? where id = ? returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, g.getId());
			
			return ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean delete(Genre g) {
		
		String sql = "delete from genres where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, g.getId());
			
			return ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Genre make(ResultSet rs) throws SQLException {

		Genre g = new Genre();
		g.setId(rs.getInt("id"));
		g.setName(rs.getString("name"));

		return g;
	}
}
