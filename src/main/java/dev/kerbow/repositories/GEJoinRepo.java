package dev.kerbow.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import dev.kerbow.models.Editor;
import dev.kerbow.models.GEJoin;
import dev.kerbow.models.Genre;
import dev.kerbow.utils.JDBCConnection;

public class GEJoinRepo implements GenericRepo<GEJoin>{
	private Connection conn = JDBCConnection.getConnection();

	@Override
	public GEJoin add(GEJoin j) {
		String sql = "insert into genre_editor_join values (default, ?, ?, ?, ?, ?) returning *;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, j.getGenre().getId());
			ps.setInt(2, j.getEditor().getId());
			ps.setBoolean(3, j.getSenior());
			ps.setBoolean(4, j.getGeneral());
			ps.setBoolean(5, j.getAssistant());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				j.setId(rs.getInt("id"));
				return j;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public GEJoin getById(Integer id) {
		String sql = "select gej.id, g.id as g_id, g.name, e.id as e_id, e.first_name, e.last_name, gej.senior, gej.assistant "
				+ "from genre_editor_join gej "
				+ "full join genres g "
				+ "on gej.genre = g.id "
				+ "full join editors e "
				+ "on gej.editor = e.id "
				+ "where gej.id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) this.make(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Map<Integer, GEJoin> getAll() {
		String sql = "select gej.id, g.id as g_id, g.name, e.id as e_id, e.first_name, e.last_name, gej.senior, gej.assistant "
				+ "from genre_editor_join gej "
				+ "full join genres g "
				+ "on gej.genre = g.id "
				+ "full join editors e "
				+ "on gej.editor = e.id "
				+ "order by gej.senior desc, gej.assistant desc;";
		try {
			Map<Integer, GEJoin> map = new HashMap<Integer, GEJoin>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				GEJoin j = this.make(rs);
				map.put(j.getId(), j);
			}
			
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(GEJoin j) {
		String sql = "update genre_editor_join set genre = ?, editor = ?, senior = ?,"
				+ "general = ?, assistant = ? where id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, j.getGenre().getId());
			ps.setInt(2, j.getEditor().getId());
			ps.setBoolean(3, j.getSenior());
			ps.setBoolean(4, j.getGeneral());
			ps.setBoolean(5, j.getAssistant());
			return ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean delete(GEJoin j) {
		String sql = "delete from genre_editor_join where id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, j.getId());
			return ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public GEJoin make(ResultSet rs) throws SQLException {
		GEJoin j = new GEJoin();
		j.setId(rs.getInt("id"));
		Genre g = (new GenreRepo()).getById(rs.getInt("genre"));
		j.setGenre(g);
		Editor e = (new EditorRepo()).getById(rs.getInt("editor"));
		j.setEditor(e);
		Boolean s = rs.getBoolean("senior");
		j.setSenior(s);
		Boolean gen = rs.getBoolean("general");
		j.setGeneral(gen);
		Boolean a = rs.getBoolean("assistant");
		j.setAssistant(a);
		return j;
	}
}
