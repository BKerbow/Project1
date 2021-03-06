package dev.kerbow.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.kerbow.models.Author;
import dev.kerbow.models.Editor;
import dev.kerbow.models.Genre;
import dev.kerbow.models.Story;
import dev.kerbow.models.StoryType;
import dev.kerbow.utils.JDBCConnection;

public class StoryRepo implements GenericRepo<Story> {
	
	private Connection conn = JDBCConnection.getConnection();
	
	@Override
	public Story add(Story s) {
		
		String sql = "insert into stories values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning *";
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, s.getTitle());
			ps.setInt(2, s.getGenre().getId());
			ps.setInt(3, s.getType().getId());
			ps.setInt(4, s.getAuthor().getId());
			ps.setString(5, s.getDescription());
			ps.setString(6, s.getTagLine());
			ps.setDate(7, s.getCompletionDate());
			ps.setString(8, s.getApprovalStatus());
			ps.setString(9, s.getReason());
			ps.setDate(10, s.getSubmissionDate());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				s.setId(rs.getInt("id"));
				return s;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Story getById(Integer id) {
		
		String sql = "select * from stories where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) return this.make(rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Map<Integer, Story> getAll() {
		
		String sql = "select * from stories;";
		
		try {
			Map<Integer, Story> map = new HashMap<Integer, Story>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Story s = this.make(rs);
				map.put(s.getId(), s);
			}
			
			return map;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Story> getAllByAuthor(Integer a_id) {
		String sql = "select * from stories where author = ?;";
		try {
			List<Story> list = new ArrayList<Story>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(this.make(rs));
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public List<Story> getAllByGenreAndStatus(Genre g, String status) {
		String sql = "select * from stories where genre = ? and approval_status = ?;";
		try {
			List<Story> list = new ArrayList<Story>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, g.getId());
			ps.setString(2, status);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(this.make(rs));
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//This isn't working right, no idea why. Trying a different way
	public Map<Integer, Story> getByCommittee(String username, String password){
		System.out.println("In the getByCommitte method");
		String sql = "select * from stories s "
				+ "left join genres g on s.genre = g.id "
				+ "left join genre_editor_join gej on g.id = gej.genre "
				+ "left join editors e on gej.editor = e.id "
				+ "where e.username = ? and e.password = ?;";
		try {
			System.out.println("In the Try block");
			Map<Integer, Story> map = new HashMap<Integer, Story>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ps.setString(1, username);
			ps.setString(2, password);
			while (rs.next()) {
				Story s = this.make(rs);
				map.put(s.getId(), s);
			}
			return map;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	@Override
	public boolean update(Story s) {
		
		String sql = "update stories set title = ?, description = ?, tag_line = ?, completion_date = ?, approval_status = ?, reason = ? where id = ? returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, s.getTitle());
			ps.setString(2, s.getDescription());
			ps.setString(3, s.getTagLine());
			ps.setDate(4, s.getCompletionDate());
			ps.setString(5, s.getApprovalStatus());
			ps.setString(6, s.getReason());
			ps.setInt(7, s.getId());
			
			return ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean delete(Story s) {
		
		String sql = "delete from stories where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, s.getId());
			
			return ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Story make(ResultSet rs) throws SQLException {
		
		Story s = new Story();
		s.setId(rs.getInt("id"));
		s.setTitle(rs.getString("title"));
		Genre g = (new GenreRepo()).getById(rs.getInt("genre"));
		s.setGenre(g);
		StoryType st = (new StoryTypeRepo()).getById(rs.getInt("story_type"));
		s.setType(st);
		Author a = (new AuthorRepo()).getById(rs.getInt("author"));
		s.setAuthor(a);
		s.setDescription(rs.getString("description"));
		s.setTagLine(rs.getString("tag_Line"));
		s.setCompletionDate(rs.getDate("completion_Date"));
		s.setApprovalStatus(rs.getString("approval_Status"));
		s.setReason(rs.getString("reason"));
		s.setCompletionStatus(rs.getString("completion_Status"));
		
		return s;
	}
}
