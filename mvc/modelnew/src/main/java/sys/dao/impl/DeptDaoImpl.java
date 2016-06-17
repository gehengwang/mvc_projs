package sys.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sys.dao.DeptDao;
import sys.model.Dept;
import sys.model.UserInfo;

@Repository
public class DeptDaoImpl implements DeptDao{

	@Resource(name="namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate nameJdbcTemplate;
	
	@Override
	public Dept queryDeptObj(Dept dept) {
		// TODO Auto-generated method stub
		StringBuffer whereSql = new StringBuffer();

		if(!"".equals(dept.getDeptId())&&dept.getDeptId()!=null){
			whereSql.append(" and id=:deptId");
		}
		if(!"".equals(dept.getDeptName())&&dept.getDeptName()!=null){
			whereSql.append(" and dept_name=:deptName");
		}
		if(!"".equals(dept.getDeptCode())&&dept.getDeptCode()!=null){
			whereSql.append(" and dept_code=:deptCode ");
		}
		if(!"".equals(dept.getDeptEmail())&&dept.getDeptEmail()!=null){
			whereSql.append(" and dept_email=:deptEmail ");
		}
		if(!"".equals(dept.getDeptMobile())&&dept.getDeptMobile()!=null){
			whereSql.append(" and dept_mobile=:deptMobile ");
		}
		String sql = "select id deptId,dept_name deptName,dept_email deptEmail, "
				+ "dept_mobile deptMobile,dept_ip deptIp,dept_status deptStatus, "
				+ "dept_attr deptAttr from tb_dept where 1=1"+whereSql;
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(dept);
		Dept deptObj = new Dept();
		try{
			deptObj = nameJdbcTemplate.queryForObject(sql, sqlParam, new BeanPropertyRowMapper<Dept>(Dept.class));
		}catch(EmptyResultDataAccessException  e){
			return null;
		}
		
		return deptObj;	
	}

	@Override
	public List<Dept> queryDeptList(Dept dept){
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();

		if(!"".equals(dept.getDeptId())&&dept.getDeptId()!=null){
			whereSql.append(" and dept_id=:deptId");
		}
		if(!"".equals(dept.getDeptName())&&dept.getDeptName()!=null){
			whereSql.append(" and dept_name=:deptName");
		}
		if(!"".equals(dept.getDeptCode())&&dept.getDeptCode()!=null){
			whereSql.append(" and dept_code=:deptCode ");
		}
		if(!"".equals(dept.getDeptEmail())&&dept.getDeptEmail()!=null){
			whereSql.append(" and dept_email=:deptEmail ");
		}
		if(!"".equals(dept.getDeptMobile())&&dept.getDeptMobile()!=null){
			whereSql.append(" and dept_mobile=:deptMobile ");
		}
		if(!"".equals(dept.getDeptStatus())&&dept.getDeptStatus()!=null){
			whereSql.append(" and dept_status=:deptStatus ");
		}
		if(!"".equals(dept.getDeptAttr())&&dept.getDeptAttr()!=null){
			whereSql.append(" and dept_attr=:deptAttr ");
		}
		
		String sql = "select * from tb_dept where 1=1 "+whereSql;
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(dept);
		List<Map<String,Object>> deptMap = nameJdbcTemplate.queryForList(sql, sqlParam);
		List<Dept> deptList = new ArrayList<Dept>();
		for(Map<String,Object> map : deptMap){
			Dept deptObj = new Dept();
			try {
				BeanUtils.populate(deptObj, map);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			deptList.add(deptObj);
		}
		return deptList;
	}

	@Override
	public Map<String,Object> addDept(Dept dept) {
		// TODO 自动生成的方法存根
		String sql = "insert into tb_dept(dept_name,dept_mobile,dept_email, "
				+ "dept_ip,create_date,modify_date,apply_date,dept_status,dept_attr) "
				+ "values"
				+ "(:deptName,:deptMobile,:deptEmail,:deptIp,now(),now(),null,0,:deptAttr)";
		KeyHolder keyHolder=new GeneratedKeyHolder();
		SqlParameterSource param = new BeanPropertySqlParameterSource(dept);
		int rs = nameJdbcTemplate.update(sql, param, keyHolder);
		//自动生成主键
		Long deptId = (long) keyHolder.getKey().intValue();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("rs", rs);map.put("deptId", deptId);
		//String sqlCode = "update tb_dept set dept_code=concat('025',lpad(dept_id,3,'0')) where dept_id=:dept_id";
		//Map<String, Integer> map = Collections.singletonMap("dept_id", sequence);
		//namedParameterJdbcTemplate.update(sqlCode, map);
		return map;
	}
	
	public int updateDeptCode(Long deptId){
		String sql = "update tb_dept set dept_code=concat('025',lpad(id,3,'0')) where id=:deptId ";
		SqlParameterSource param = new MapSqlParameterSource("deptId",deptId);
		int rs = nameJdbcTemplate.update(sql, param);
		return rs;
	}

	@Override
	public int updateDept(Dept dept) {
		// TODO 自动生成的方法存根
		String sql = "update tb_dept set dept_email=:deptEmail,dept_mobile=:deptMobile where dept_id=:deptId";
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(dept);
		int rs = nameJdbcTemplate.update(sql, sqlParam);
		return rs;
	}

	@Override
	public int deleteDepts(String deptIds) {
		// TODO 自动生成的方法存根
		String sql = "delete from tb_dept where dept_id in(?)";
		SqlParameterSource sqlParam = new MapSqlParameterSource("deptIds", deptIds.replace(",", "','"));
		int rs = nameJdbcTemplate.update(sql,sqlParam);
		return rs;
	}

	@Override
	public List<?> deptListMap(UserInfo userInfo) {
		// TODO 自动生成的方法存根
		String sql = "select id deptId,dept_name deptName from tb_dept "
				+ "where id in(select dept_id from tb_user_bind where user_id = :userId) ";
		
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userInfo.getUser().getUserId());
		List<?> list = nameJdbcTemplate.queryForList(sql, map);
		return list;
	}

}