package com.hq.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hq.common.http.HTTPTools;
import com.hq.common.tools.DictTools;
import com.hq.common.tools.EventTools;
import com.hq.common.tools.JsonTools;
import com.hq.common.tools.LockTools;
import com.hq.common.tools.RuntimeTools;
import com.hq.manage.base.select.SelectService;
import com.hq.manage.sys.dict.DictService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

/**
 * @author 高振中
 * @summary 静态类初始化
 * @date 2024-07-21 21:44:15
 **/
@Configuration
public class ConfigInit {

	@Value("${spring.profiles.active}")
	private String active;

	@Resource
	private ObjectMapper mapper;
	@Resource
	private RestTemplate restTemplate;
	@Resource
	private RedisTemplate<String, Integer> redisTemplate;// 注入redis模板类
	@Resource
	private DictService dictService;
	@Resource
	private SelectService selectService;

	@Resource
	private ApplicationContext applicationContext;

	@Value("${code.envPre:}")
	private String codeEnvPre;

	@PostConstruct
	public void init() {
		DictTools.dictService = dictService;
		DictTools.selectService = selectService;

		JsonTools.mapper = mapper;
		HTTPTools.restTemplate = restTemplate;
		LockTools.redisTemplate = redisTemplate;

		EventTools.applicationContext = applicationContext;
		RuntimeTools.active = active;

	}
}