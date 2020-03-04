package com.zongbang.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zongbang.dao.OauthAccessTokenMapper;
import com.zongbang.pojo.OauthAccessToken;
import com.zongbang.service.OauthAccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:OauthAccessToken业务层接口实现类
 * @Date www.itheima.com 0:16
 *****/
@Service
public class OauthAccessTokenServiceImpl implements OauthAccessTokenService {

@Autowired
private OauthAccessTokenMapper oauthAccessTokenMapper;


/**
 * OauthAccessToken条件+分页查询
 * @param oauthAccessToken 查询条件
 * @param page 页码
 * @param size 页大小
 * @return 分页结果
 */
@Override
public PageInfo<OauthAccessToken> findPage(OauthAccessToken oauthAccessToken, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(oauthAccessToken);
        //执行搜索
        return new PageInfo<OauthAccessToken>(oauthAccessTokenMapper.selectByExample(example));
        }

/**
 * OauthAccessToken分页查询
 * @param page
 * @param size
 * @return
 */
@Override
public PageInfo<OauthAccessToken> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<OauthAccessToken>(oauthAccessTokenMapper.selectAll());
        }

/**
 * OauthAccessToken条件查询
 * @param oauthAccessToken
 * @return
 */
@Override
public List<OauthAccessToken> findList(OauthAccessToken oauthAccessToken){
        //构建查询条件
        Example example = createExample(oauthAccessToken);
        //根据构建的条件查询数据
        return oauthAccessTokenMapper.selectByExample(example);
        }


/**
 * OauthAccessToken构建查询对象
 * @param oauthAccessToken
 * @return
 */
public Example createExample(OauthAccessToken oauthAccessToken){
        Example example=new Example(OauthAccessToken.class);
        Example.Criteria criteria = example.createCriteria();
        if(oauthAccessToken!=null){
        // 
        if(!StringUtils.isEmpty(oauthAccessToken.getTokenId())){
        criteria.andEqualTo("tokenId",oauthAccessToken.getTokenId());
        }
        // 
        if(!StringUtils.isEmpty(oauthAccessToken.getToken())){
        criteria.andEqualTo("token",oauthAccessToken.getToken());
        }
        // 
        if(!StringUtils.isEmpty(oauthAccessToken.getAuthenticationId())){
        criteria.andEqualTo("authenticationId",oauthAccessToken.getAuthenticationId());
        }
        // 
        if(!StringUtils.isEmpty(oauthAccessToken.getUserName())){
        criteria.andEqualTo("userName",oauthAccessToken.getUserName());
        }
        // 
        if(!StringUtils.isEmpty(oauthAccessToken.getClientId())){
        criteria.andEqualTo("clientId",oauthAccessToken.getClientId());
        }
        // 
        if(!StringUtils.isEmpty(oauthAccessToken.getAuthentication())){
        criteria.andEqualTo("authentication",oauthAccessToken.getAuthentication());
        }
        // 
        if(!StringUtils.isEmpty(oauthAccessToken.getRefreshToken())){
        criteria.andEqualTo("refreshToken",oauthAccessToken.getRefreshToken());
        }
        }
        return example;
        }

/**
 * 删除
 * @param id
 */
@Override
public void delete(String id){
        oauthAccessTokenMapper.deleteByPrimaryKey(id);
        }

/**
 * 修改OauthAccessToken
 * @param oauthAccessToken
 */
@Override
public void update(OauthAccessToken oauthAccessToken){
        oauthAccessTokenMapper.updateByPrimaryKey(oauthAccessToken);
        }

/**
 * 增加OauthAccessToken
 * @param oauthAccessToken
 */
@Override
public void add(OauthAccessToken oauthAccessToken){
        oauthAccessTokenMapper.insert(oauthAccessToken);
        }

/**
 * 根据ID查询OauthAccessToken
 * @param id
 * @return
 */
@Override
public OauthAccessToken findById(String id){
        return  oauthAccessTokenMapper.selectByPrimaryKey(id);
        }

/**
 * 查询OauthAccessToken全部数据
 * @return
 */
@Override
public List<OauthAccessToken> findAll() {
        return oauthAccessTokenMapper.selectAll();
        }
        }
