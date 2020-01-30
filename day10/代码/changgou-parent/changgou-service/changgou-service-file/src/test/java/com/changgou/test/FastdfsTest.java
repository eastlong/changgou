package com.changgou.test;

import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 描述
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.changgou.test *
 * @since 1.0
 */
public class FastdfsTest {

    //上传

    @Test
    public void upload() throws Exception {
        //1.创建一个配置文件 (配置服务器的地址 ,端口,连接的信息)

        //2.加载配置文件

        //参数:指定配置文件路径
        ClientGlobal.init("C:\\Users\\Administrator\\IdeaProjects\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");

        //3.创建trackerclient对象
        TrackerClient trackerClient = new TrackerClient();

        //4.创建trackerserver 对象
        TrackerServer trackerServer = trackerClient.getConnection();

        //5.创建stroageserver 对象
        StorageServer storageServer = null;

        //6.创建storageclient 对象
        //参数1:trackerserver的对象
        //参数2 :storageserver对象
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        //7.上传图片(使用stroageclient api 实现)
        //参数1 :指定本地文件路径
        //参数2 :指定文件的扩展名 不要带点
        //参数3 :元数据(文件的大小,文件时间错,文件的作者,像素.......) 可以为空
        String[] jpgs = storageClient.upload_file("C:\\Users\\Administrator\\Pictures\\timg.jpg", "jpg", null);

        for (String jpg : jpgs) {
            System.out.println(jpg);
        }

    }

    //下载图片

    @Test
    public void download() throws Exception {
        //1.创建一个配置文件 (配置服务器的地址 ,端口,连接的信息)
        //2.加载配置文件
        ClientGlobal.init("C:\\Users\\Administrator\\IdeaProjects\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");

        //3.创建trackerclient对象
        TrackerClient trackerClient = new TrackerClient();
        //4.创建trackerserver 对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建stroageserver 对象
        //6.创建storageclient 对象
        StorageClient storageClient = new StorageClient(trackerServer, null);
        //7.根据组名 和 文件名 下载图片

        //参数1:指定组名
        //参数2 :指定远程的文件名
        byte[] bytes = storageClient.download_file("group1", "M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg");

        //写流
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/12345678.jpg"));

        fileOutputStream.write(bytes);

        fileOutputStream.close();//测试用.实际上要在finally

    }

    //删除

    @Test
    public void delete() throws Exception {
        //1.创建一个配置文件 (配置服务器的地址 ,端口,连接的信息)
        //2.加载配置文件
        ClientGlobal.init("C:\\Users\\Administrator\\IdeaProjects\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");

        //3.创建trackerclient对象
        TrackerClient trackerClient = new TrackerClient();
        //4.创建trackerserver 对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建stroageserver 对象
        //6.创建storageclient 对象
        StorageClient storageClient = new StorageClient(trackerServer, null);
        //7.删除图片 (根据组名 和文件名来删除)

        int group1 = storageClient.delete_file("group1", "M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg");
        if (group1 == 0) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }

    }

    //根据组名 和文件名获取文件的信息
    @Test
    public void getFileinfo() throws Exception{
        //1.创建一个配置文件 (配置服务器的地址 ,端口,连接的信息)
        //2.加载配置文件
        ClientGlobal.init("C:\\Users\\Administrator\\IdeaProjects\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");

        //3.创建trackerclient对象
        TrackerClient trackerClient = new TrackerClient();
        //4.创建trackerserver 对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建stroageserver 对象
        //6.创建storageclient 对象
        StorageClient storageClient = new StorageClient(trackerServer, null);

        //参数1 指定组名
        //参数2 指定文件的路径
        FileInfo fileInfo = storageClient.get_file_info("group1", "M00/00/00/wKjThF1aYy6AOlitAAClQrJOYvs038.jpg");
        System.out.println(fileInfo.getSourceIpAddr()+";"+ fileInfo.getFileSize());
    }


    //根据组名称 获取该组对应的组服务信息
    @Test
    public void getStroageServerInfo() throws Exception{
        //1.创建一个配置文件 (配置服务器的地址 ,端口,连接的信息)
        //2.加载配置文件
        ClientGlobal.init("C:\\Users\\Administrator\\IdeaProjects\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");

        //3.创建trackerclient对象
        TrackerClient trackerClient = new TrackerClient();
        //4.创建trackerserver 对象
        TrackerServer trackerServer = trackerClient.getConnection();

        //参数1 指定traqckerserver 对象
        //参数2 指定组名
        StorageServer group1 = trackerClient.getStoreStorage(trackerServer, "group1");

        System.out.println(group1.getInetSocketAddress().getHostString()+"+:"+group1.getInetSocketAddress().getPort());

    }


    //根据文件名和组名和tracker 获取该文件所在的storage的服务器的数组信息
    @Test
    public void getserverinfo() throws Exception{
        //1.创建一个配置文件 (配置服务器的地址 ,端口,连接的信息)
        //2.加载配置文件
        ClientGlobal.init("C:\\Users\\Administrator\\IdeaProjects\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");

        //3.创建trackerclient对象
        TrackerClient trackerClient = new TrackerClient();
        //4.创建trackerserver 对象
        TrackerServer trackerServer = trackerClient.getConnection();

        ServerInfo[] group1s = trackerClient.getFetchStorages(trackerServer, "group1", "M00/00/00/wKjThF1aYy6AOlitAAClQrJOYvs038.jpg");
        for (ServerInfo group1 : group1s) {
            System.out.println(group1.getIpAddr()+":"+group1.getPort());
        }
    }

    //获取tracker的地址(ip 和端口)
    @Test
    public void getTrackerInfo() throws Exception{
        //1.创建一个配置文件 (配置服务器的地址 ,端口,连接的信息)
        //2.加载配置文件
        ClientGlobal.init("C:\\Users\\Administrator\\IdeaProjects\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");

        //3.创建trackerclient对象
        TrackerClient trackerClient = new TrackerClient();
        //4.创建trackerserver 对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //tracker 的ip的信息
        String hostString = trackerServer.getInetSocketAddress().getHostString();

        //http://192.168.211.132:8080/group1/M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg img
        int g_tracker_http_port = ClientGlobal.getG_tracker_http_port();
        System.out.println("http://"+hostString+":"+g_tracker_http_port);
    }



}
