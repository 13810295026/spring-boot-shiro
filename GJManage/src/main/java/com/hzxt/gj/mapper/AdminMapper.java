package com.hzxt.gj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hzxt.gj.pojo.Admin;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

	public Admin selectAdminRoleById(Integer id);

	public Admin selectByAccount(String account);

	public List<Integer> selectRoleIdsById(Integer id);

	public Integer deleteAdminRole(Integer id);
}
