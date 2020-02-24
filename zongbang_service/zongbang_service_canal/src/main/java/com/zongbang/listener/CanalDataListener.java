package com.zongbang.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.*;

/**
 * @ClassName: CanalDataListener
 * @Description: TODO
 * @Author: HuiLiao
 * @Date: 2020/2/24 20:17
 */


@CanalEventListener
public class CanalDataListener  {


    /**
     * @Author HuiLiao
     * @Description 监听增加数据
     * @Date 20:33 2020/2/24
     * @Param [entryType, rowData] entryType 当前操作的类型  rowData 发生变更的一行数据
     * @return void
     **/
    @InsertListenPoint
    public void onEventInsert(CanalEntry.EntryType entryType,CanalEntry.RowData rowData){
        rowData.getAfterColumnsList().forEach(column -> System.err.println("添加:列名:"+ column.getName() +"--------变更的数据:" + column.getValue()));
    }

    /**
     * @Author HuiLiao
     * @Description 修改数据的监听
     * @Date 20:36 2020/2/24
     * @Param [entryType, rowData]
     * @return void
     **/
    @UpdateListenPoint
    public void onEventUpdate(CanalEntry.EntryType entryType,CanalEntry.RowData rowData){
        rowData.getBeforeColumnsList().forEach(column -> System.err.println("修改前:列名:"+ column.getName() +"--------变更的数据:" + column.getValue()));
        rowData.getAfterColumnsList().forEach(column -> System.err.println("修改后:列名:"+ column.getName() +"--------变更的数据:" + column.getValue()));
    }

    /**
     * @Author HuiLiao
     * @Description 删除数据的监听
     * @Date 20:36 2020/2/24
     * @Param [entryType, rowData]
     * @return void
     **/
    @DeleteListenPoint
    public void onEventDelete(CanalEntry.EntryType entryType,CanalEntry.RowData rowData){
        rowData.getBeforeColumnsList().forEach(column -> System.err.println("删除:列名:"+ column.getName() +"--------变更的数据:" + column.getValue()));
    }

    /**
     * @Author HuiLiao
     * @Description 自定义监听
     * @Date 20:36 2020/2/24
     * @Param [entryType, rowData]
     * @return void
     **/
    @ListenPoint(
            eventType = {CanalEntry.EventType.DELETE,CanalEntry.EventType.UPDATE},  //监听类型
            schema = {"zongbang_content"},        //监控的数据
            table = {"tb_content"},               //监控的表
            destination = "example"               //指定实例的地址
    )
    public void onEvent(CanalEntry.EntryType entryType,CanalEntry.RowData rowData){
        rowData.getBeforeColumnsList().forEach(column -> System.err.println("自定义操作前:列名:"+ column.getName() +"--------变更的数据:" + column.getValue()));
        rowData.getAfterColumnsList().forEach(column -> System.err.println("自定义操作后:列名:"+ column.getName() +"--------变更的数据:" + column.getValue()));
    }
}
