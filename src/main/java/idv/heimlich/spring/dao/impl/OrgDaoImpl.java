package idv.heimlich.spring.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import idv.heimlich.spring.dao.OrgDao;
import idv.heimlich.spring.domain.OrgDo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("orgDao")
public class OrgDaoImpl implements OrgDao {
	
	private JdbcTemplate jdbcTemplate;
	
	protected static RowMapper<OrgDo> CONVERTER = new RowMapper<OrgDo>() {

		public OrgDo mapRow(ResultSet rs, int rowNum) throws SQLException {
			OrgDo orgDo = new OrgDo();
			orgDo.setId(rs.getInt("id"));
			orgDo.setName(rs.getString("name"));
			orgDo.setYear(rs.getInt("year"));
			orgDo.setCode(rs.getString("code"));
			return orgDo;
		}
		
	};
	
	public RowMapper<OrgDo> getConverter() {
		return CONVERTER;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
	}

	public boolean create(OrgDo orgDo) {
		String sql = "INSERT INTO org (id, name, year, code) "
				+ " VALUES (nvl((select max(ID) from org), 0) + 1, ?, ?, ?) ";
		Object[] args = new Object[] {orgDo.getName(), orgDo.getYear(), orgDo.getCode()};
		return this.jdbcTemplate.update(sql, args) == 1;
	}

	public OrgDo getOrg(Integer id) {
		String sql = "SELECT ID, NAME, YEAR, CODE FROM org WHERE id = ? ";
		Object[] args = new Object[] {id};
		OrgDo orgDo = this.jdbcTemplate.queryForObject(sql, args, CONVERTER);
		return orgDo;
	}

	public List<OrgDo> getAllOrgs() {
		String sql = "SELECT * FROM org ";
//		List<OrgDo> orgList = jdbcTemplate.query(sqlQuery, new OrgRowMapper());
		List<OrgDo> orgList = this.jdbcTemplate.query(sql, CONVERTER);
		return orgList;
	}

	public boolean delete(OrgDo orgDo) {
		String sql = "DELETE FROM org WHERE id = ? ";
		Object[] args = new Object[] {orgDo.getId()};
		return this.jdbcTemplate.update(sql, args) == 1;
	}

	public boolean update(OrgDo orgDo) {
		String sql = "UPDATE org SET code = ? WHERE id = ? ";
		Object[] args = new Object[] {orgDo.getCode(), orgDo.getId()};
		return this.jdbcTemplate.update(sql, args) == 1;
	}

	/**
	 * Delete 逐筆刪
	 * Truncate table 全刪，TABLE會留
	 * Drop table 全刪，TABLE不留
	 */
	public void cleanup() {
		String sql = "TRUNCATE TABLE org ";
		this.jdbcTemplate.execute(sql);
	}

}
