package com.penzailife.dao;

import java.util.List;

import com.penzailife.dao.model.TbUser;

public interface TbUserMapper {

	int deleteByPrimaryKey(Integer userid);

	int insert(TbUser record);

	int insertSelective(TbUser record);

	TbUser selectByPrimaryKey(Integer userid);

	List<TbUser> selectAllUser();

	int updateByPrimaryKeySelective(TbUser record);

	int updateByPrimaryKey(TbUser record);
}