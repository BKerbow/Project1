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

	@Override
	public GEJoin add(GEJoin t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GEJoin getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, GEJoin> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(GEJoin t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(GEJoin t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GEJoin make(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
