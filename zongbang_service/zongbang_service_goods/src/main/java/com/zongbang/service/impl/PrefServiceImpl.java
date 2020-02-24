package com.zongbang.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zongbang.dao.PrefMapper;
import com.zongbang.pojo.Pref;
import com.zongbang.service.PrefService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/****
 * @Author:www.itheima.com
 * @Description:Pref业务层接口实现类
 * @Date www.itheima.com 0:16
 *****/
@Service
public class PrefServiceImpl implements PrefService {

    @Resource
    private PrefMapper prefMapper;


    /**
     * Pref条件+分页查询
     *
     * @param pref 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Pref> findPage(Pref pref, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(pref);
        //执行搜索
        return new PageInfo<Pref>(prefMapper.selectByExample(example));
    }

    /**
     * Pref分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Pref> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<Pref>(prefMapper.selectAll());
    }

    /**
     * Pref条件查询
     *
     * @param pref
     * @return
     */
    @Override
    public List<Pref> findList(Pref pref) {
        //构建查询条件
        Example example = createExample(pref);
        //根据构建的条件查询数据
        return prefMapper.selectByExample(example);
    }


    /**
     * Pref构建查询对象
     *
     * @param pref
     * @return
     */
    public Example createExample(Pref pref) {
        Example example = new Example(Pref.class);
        Example.Criteria criteria = example.createCriteria();
        if (pref != null) {
            // ID
            if (!StringUtils.isEmpty(pref.getId())) {
                criteria.andEqualTo("id", pref.getId());
            }
            // 分类ID
            if (!StringUtils.isEmpty(pref.getCateId())) {
                criteria.andEqualTo("cateId", pref.getCateId());
            }
            // 消费金额
            if (!StringUtils.isEmpty(pref.getBuyMoney())) {
                criteria.andEqualTo("buyMoney", pref.getBuyMoney());
            }
            // 优惠金额
            if (!StringUtils.isEmpty(pref.getPreMoney())) {
                criteria.andEqualTo("preMoney", pref.getPreMoney());
            }
            // 活动开始日期
            if (!StringUtils.isEmpty(pref.getStartTime())) {
                criteria.andEqualTo("startTime", pref.getStartTime());
            }
            // 活动截至日期
            if (!StringUtils.isEmpty(pref.getEndTime())) {
                criteria.andEqualTo("endTime", pref.getEndTime());
            }
            // 类型
            if (!StringUtils.isEmpty(pref.getType())) {
                criteria.andEqualTo("type", pref.getType());
            }
            // 状态
            if (!StringUtils.isEmpty(pref.getState())) {
                criteria.andEqualTo("state", pref.getState());
            }
        }
        return example;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Transactional
    @Override
    public void delete(Integer id) {
        prefMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Pref
     *
     * @param pref
     */
    @Transactional
    @Override
    public void update(Pref pref) {
        prefMapper.updateByPrimaryKey(pref);
    }

    /**
     * 增加Pref
     *
     * @param pref
     */
    @Transactional
    @Override
    public void add(Pref pref) {
        prefMapper.insert(pref);
    }

    /**
     * 根据ID查询Pref
     *
     * @param id
     * @return
     */
    @Override
    public Pref findById(Integer id) {
        return prefMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Pref全部数据
     *
     * @return
     */
    @Override
    public List<Pref> findAll() {
        return prefMapper.selectAll();
    }
}
