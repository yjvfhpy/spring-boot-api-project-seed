package com.company.project.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.cache.RedisCache;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.User;
import com.company.project.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by CodeGenerator on 2017/06/30.
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	@Resource
	private RedisCache redis;

	@Value("${com.mical.name}")
	private String name;
	
	@Value("${com.mical.desc}")
	private String desc;
	
	protected static Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/add")
	public Result add(User user) {
		userService.save(user);
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/delete")
	public Result delete(Integer id) {
		userService.deleteById(id);
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/update")
	public Result update(User user) {
		userService.update(user);
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/detail")
	public Result detail(Integer id) {
		User user = userService.findById(id);
		return ResultGenerator.genSuccessResult(user);
	}

	@PostMapping("/list")
	public Result list(Integer page, Integer size) {
		logger.info(desc);
		PageHelper.startPage(page, size);
		String key = RedisCache.CAHCENAME + "|users|" + page + "|" + size;
		List<User> list = (List<User>) redis.getListCache(key, User.class);
		if (list != null) {
			logger.info("get cache with key:" + list);
		} else {
			list = userService.findAll();
			redis.putListCacheWithExpireTime(key, list, RedisCache.CAHCETIME);
			logger.info("put cache with key:" + list);
		}

		PageInfo<User> pageInfo = new PageInfo<>(list);
		return ResultGenerator.genSuccessResult(pageInfo);
	}
}