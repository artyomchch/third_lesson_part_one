package org.example.DAO;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class CourseRowMapper implements RowMapper<Course>
{
	public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
		Course c= new Course();
		c.setId(rs.getInt("id"));
		c.setTitle(rs.getString("title"));
		c.setLength(rs.getInt("length"));
		c.setDescription(rs.getString("description"));
		return c;
	}
	
}

public class JdbcCourseDAO implements CourseDAO {
	private static final String SQL_SELECT_COURSE =
			 "SELECT id, title, length, description FROM courses";
	private static final String SQL_SELECT_COURSE_BY_ID =
			SQL_SELECT_COURSE+" WHERE id = ?";
	private static final String SQL_SELECT_COURSE_BY_TITLE =
			SQL_SELECT_COURSE + " WHERE title LIKE ?";
	private static final String SQL_INSERT_COURSE =
			"INSERT INTO Courses (title,length,description) VALUES (:title, :length, :description)";
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Course> findAll() {

		List<Course> courses = getJdbcTemplate().query(SQL_SELECT_COURSE,
				new BeanPropertyRowMapper(Course.class));

		return courses;
	}

	@Override
	public List<Course> findByTitle(String title) {
		return getJdbcTemplate().query(SQL_SELECT_COURSE_BY_TITLE,
				new Object[] { "%" +title + "%"},
				new BeanPropertyRowMapper<>(Course.class));
	}

	@Override
	public void insert(Course course) {

	}

	@Override
	public void update(Course course) {

	}

	@Override
	public void delete(int id) {

	}

	public Course findById(int id) {
		Course course = (Course)getJdbcTemplate().queryForObject(
				SQL_SELECT_COURSE_BY_ID, new Object[] { id }, 
				new CourseRowMapper());		
		return course;
				 
	}

}