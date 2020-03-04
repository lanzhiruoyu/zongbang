package com.zongbang.controller;

import com.github.pagehelper.PageInfo;
import com.zongbang.pojo.OauthAccessToken;
import com.zongbang.pojo.Result;
import com.zongbang.pojo.StatusCode;
import com.zongbang.service.OauthAccessTokenService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:
 * @Date www.itheima.com 0:18
 *****/
@Api(value = "OauthAccessTokenController")
@RestController
@RequestMapping("/oauthAccessToken")
@CrossOrigin
public class OauthAccessTokenController {

    @Autowired
    private OauthAccessTokenService oauthAccessTokenService;

    /***
     * OauthAccessToken分页条件搜索实现
     * @param oauthAccessToken
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "OauthAccessToken条件分页查询",notes = "分页条件查询OauthAccessToken方法详情",tags = {"OauthAccessTokenController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "OauthAccessToken对象",value = "传入JSON数据",required = false) OauthAccessToken oauthAccessToken, @PathVariable  int page, @PathVariable  int size){
        //调用OauthAccessTokenService实现分页条件查询OauthAccessToken
        PageInfo<OauthAccessToken> pageInfo = oauthAccessTokenService.findPage(oauthAccessToken, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * OauthAccessToken分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "OauthAccessToken分页查询",notes = "分页查询OauthAccessToken方法详情",tags = {"OauthAccessTokenController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用OauthAccessTokenService实现分页查询OauthAccessToken
        PageInfo<OauthAccessToken> pageInfo = oauthAccessTokenService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param oauthAccessToken
     * @return
     */
    @ApiOperation(value = "OauthAccessToken条件查询",notes = "条件查询OauthAccessToken方法详情",tags = {"OauthAccessTokenController"})
    @PostMapping(value = "/search" )
    public Result<List<OauthAccessToken>> findList(@RequestBody(required = false) @ApiParam(name = "OauthAccessToken对象",value = "传入JSON数据",required = false) OauthAccessToken oauthAccessToken){
        //调用OauthAccessTokenService实现条件查询OauthAccessToken
        List<OauthAccessToken> list = oauthAccessTokenService.findList(oauthAccessToken);
        return new Result<List<OauthAccessToken>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "OauthAccessToken根据ID删除",notes = "根据ID删除OauthAccessToken方法详情",tags = {"OauthAccessTokenController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //调用OauthAccessTokenService实现根据主键删除
        oauthAccessTokenService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改OauthAccessToken数据
     * @param oauthAccessToken
     * @param id
     * @return
     */
    @ApiOperation(value = "OauthAccessToken根据ID修改",notes = "根据ID修改OauthAccessToken方法详情",tags = {"OauthAccessTokenController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "OauthAccessToken对象",value = "传入JSON数据",required = false) OauthAccessToken oauthAccessToken,@PathVariable String id){
        //设置主键值
        oauthAccessToken.setAuthenticationId(id);
        //调用OauthAccessTokenService实现修改OauthAccessToken
        oauthAccessTokenService.update(oauthAccessToken);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增OauthAccessToken数据
     * @param oauthAccessToken
     * @return
     */
    @ApiOperation(value = "OauthAccessToken添加",notes = "添加OauthAccessToken方法详情",tags = {"OauthAccessTokenController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "OauthAccessToken对象",value = "传入JSON数据",required = true) OauthAccessToken oauthAccessToken){
        //调用OauthAccessTokenService实现添加OauthAccessToken
        oauthAccessTokenService.add(oauthAccessToken);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询OauthAccessToken数据
     * @param id
     * @return
     */
    @ApiOperation(value = "OauthAccessToken根据ID查询",notes = "根据ID查询OauthAccessToken方法详情",tags = {"OauthAccessTokenController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @GetMapping("/{id}")
    public Result<OauthAccessToken> findById(@PathVariable String id){
        //调用OauthAccessTokenService实现根据主键查询OauthAccessToken
        OauthAccessToken oauthAccessToken = oauthAccessTokenService.findById(id);
        return new Result<OauthAccessToken>(true,StatusCode.OK,"查询成功",oauthAccessToken);
    }

    /***
     * 查询OauthAccessToken全部数据
     * @return
     */
    @ApiOperation(value = "查询所有OauthAccessToken",notes = "查询所OauthAccessToken有方法详情",tags = {"OauthAccessTokenController"})
    @GetMapping
    public Result<List<OauthAccessToken>> findAll(){
        //调用OauthAccessTokenService实现查询所有OauthAccessToken
        List<OauthAccessToken> list = oauthAccessTokenService.findAll();
        return new Result<List<OauthAccessToken>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
