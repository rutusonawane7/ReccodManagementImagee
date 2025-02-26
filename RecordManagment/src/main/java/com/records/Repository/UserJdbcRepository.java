package com.records.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.records.Bean.UserDetails;

@Repository
public class UserJdbcRepository {
	
	@Autowired
	private JdbcTemplate springJdbcTemplate;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static String INSERT_QUERY = 
			"""
			INSERT INTO USERSDETAILS(ID,NAME,PHOTO)
			VALUES(?,?,?);
			""";
	
	public   String SELECT_QUERY = "select * from usersdetails where id = :id";
			
	
	public void insert(UserDetails user) {
		
		String sql="insert into USERSDETAILS(ID,NAME,PHOTO) values(:id,:name,:photo)";
		MapSqlParameterSource param= new MapSqlParameterSource();
		param.addValue("id", user.getId());
		param.addValue("name", user.getName());
		param.addValue("photo", user.getPhotoBytes());
		namedParameterJdbcTemplate.update(sql, param);
		
//		springJdbcTemplate.update(INSERT_QUERY,user.getId(),user.getName(),user.getPhoto());
	}

	public UserDetails getUserById(int id)
	{
//		String Sql="select * from USERSDETAILS where id=2";
		MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
		
        try {
            return namedParameterJdbcTemplate.queryForObject(SELECT_QUERY, param, (rs, rowNum) ->
            {
                UserDetails user = new UserDetails();
                user.setId(rs.getInt("ID"));
                user.setName(rs.getString("NAME"));
                user.setPhotoBytes(rs.getBytes("PHOTO")); 
                return user;
            });
        } catch (Exception e)
        {
            System.out.println("User not found with ID: " + id);
            return null;
        }
            
        
	}

}	


