package com.zongbang.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/****
 * @Author:www.itheima.com
 * @Description:ContentCategory构建
 * @Date www.itheima.com 19:13
 *****/
@ApiModel(description = "ContentCategory",value = "ContentCategory")
@Table(name="tb_content_category")
public class ContentCategory implements Serializable{

	@ApiModelProperty(value = "类目Id",required = false)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;//类目Id

	@ApiModelProperty(value = "分类名称",required = false)
    @Column(name = "name")
	private String name;//分类名称



	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public String getName() {
		return name;
	}

	//set方法
	public void setName(String name) {
		this.name = name;
	}


}
