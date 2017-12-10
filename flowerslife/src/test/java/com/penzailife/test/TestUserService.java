package com.penzailife.test;
import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.penzailife.dao.TbUserMapper;
import com.penzailife.dao.model.TbUser;

/**
 * 创建时间：2015-1-27 下午10:45:38
 * 
 * @author andy
 * @version 2.2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml" })
public class TestUserService {

	private static final Logger LOGGER = Logger
			.getLogger(TestUserService.class);

	@Autowired
	private TbUserMapper mapper;

	@Test
	public void testInstall() {
		TbUser userInfo = new TbUser();
		userInfo.setUsername("ForeverKing");
		userInfo.setPassword("28FC957CE04861B32231DC496B5BBE23");
		userInfo.setCreateby(1);
		userInfo.setCreatedate(new Date(System.currentTimeMillis()));
		mapper.insert(userInfo);
	}
	
	@Test
	public void testQueryById1() {
		TbUser userInfo = mapper.selectByPrimaryKey(1);
		LOGGER.info(JSON.toJSON(userInfo));
	}

	@Test
	public void testQueryAll() {
		List<TbUser> userInfos = mapper.selectAllUser();
		LOGGER.info(JSON.toJSON(userInfos));
	}

}
