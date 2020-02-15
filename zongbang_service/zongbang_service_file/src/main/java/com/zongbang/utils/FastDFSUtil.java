package com.zongbang.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
 * @program: zongbang_parent
 * @description: 文件工具类
 * @author: LiaoHui
 * @create: 2020-02-07 20:59
 **/
public final class FastDFSUtil {

    static {
        String path = new ClassPathResource("fdfs_client.conf").getPath();
        try {
            ClientGlobal.init(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /** 
   * @Description: 文件上传
   * @Author: LiaoHui 
   * @Date: 2020/2/9 
   * @Param: [fastDFSFile] 
   * @return: java.lang.String 
   */
    public static String[] uploadFile(FastDFSFile fastDFSFile) throws Exception {
        StorageClient storageClient = getStorageClient();
        return storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), null);
    }

    private static StorageClient getStorageClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        //通过trackerServer创建storageClient对象
        return new StorageClient(trackerServer,null);
    }

    private static TrackerServer getTrackerServer() throws IOException {
        //创建TrackerClient
        TrackerClient client = new TrackerClient();
        //通过TrackerClient获取连接
        return client.getConnection();
    }


    /**
    * @Description: 获取文件信息
    * @Author: LiaoHui
    * @Date: 2020/2/9
    * @Param: [groupName, remoteFileName]
    * @return: org.csource.fastdfs.FileInfo
    */
    public static FileInfo getFile(String groupName,String remoteFileName) throws IOException, MyException {
        StorageClient storageClient = getStorageClient();
        return storageClient.get_file_info(groupName, remoteFileName);
    }


    /**
    * @Description: 文件下载
    * @Author: LiaoHui
    * @Date: 2020/2/9
    * @Param: [groupName, remoteFileName]
    * @return: java.io.InputStream
    */
    public static InputStream downloadFile(String groupName, String remoteFileName) throws IOException, MyException {
        StorageClient storageClient = getStorageClient();
        byte[] bytes = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(bytes);
    }

    /**
    * @Description: 文件删除
    * @Author: LiaoHui
    * @Date: 2020/2/9
    * @Param: [groupName, remoteFileName]
    * @return: void
    */
    public static void deleteFile(String groupName, String remoteFileName) throws Exception {
        StorageClient storageClient = getStorageClient();
        storageClient.delete_file(groupName, remoteFileName);
    }

    /**
    * @Description: 获取Storage信息
    * @Author: LiaoHui
    * @Date: 2020/2/9
    * @Param: []
    * @return: org.csource.fastdfs.StorageServer
    */
    public static StorageServer getStorage() throws IOException {
        //创建TrackerClient
        TrackerClient client = new TrackerClient();
        //通过TrackerClient获取连接
        TrackerServer trackerServer = client.getConnection();
        return client.getStoreStorage(trackerServer);
    }

    /**
    * @Description: 获取storage的IP和端口信息
    * @Author: LiaoHui
    * @Date: 2020/2/9
    * @Param: [groupName, remoteFileName]
    * @return: org.csource.fastdfs.ServerInfo[]
    */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName) throws IOException {
        //创建TrackerClient
        TrackerClient client = new TrackerClient();
        //通过TrackerClient获取连接
        TrackerServer trackerServer = client.getConnection();
        return client.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    /** 
    * @Description: 获取tracker信息 
    * @Author: LiaoHui 
    * @Date: 2020/2/9
    * @Param: [] 
    * @return: java.lang.String 
    */
    public static String getTrackerInfo() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        int tracker_http_port = ClientGlobal.getG_tracker_http_port();
        String ip = trackerServer.getInetSocketAddress().getHostString();
        return "http//"+ip+tracker_http_port;
    }
}
