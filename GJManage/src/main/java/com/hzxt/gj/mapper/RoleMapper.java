package com.hzxt.gj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hzxt.gj.pojo.Role;
import com.hzxt.gj.vo.RoleMenuTree;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

	public List<Integer> selectMenuIdsByIds(@Param("ids") Integer... ids);

	public Integer deleteRoleMenusById(Integer id);

	public RoleMenuTree selectRoleMenuById(Integer id);

}
