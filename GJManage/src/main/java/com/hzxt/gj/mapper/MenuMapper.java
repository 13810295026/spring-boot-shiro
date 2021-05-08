package com.hzxt.gj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hzxt.gj.pojo.Menu;
import com.hzxt.gj.vo.MenuTree;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

	public List<String> selectPermissionsByIds(@Param("ids") Integer... ids);

	public List<MenuTree> selectByRoleId(Integer roleId);
}
