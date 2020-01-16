package com.tim.delayschedule;

import org.apache.zookeeper.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

/**
 * 功能描述 : TODO
 *
 * @Author : wang hui
 * @Email : 793147654@qq.com
 * @Date : 2020-01-14 16:24
 */
@Ignore
public class ZookeeperTest {

    @Test
    public void zookeeperClient() throws IOException, KeeperException, InterruptedException {

        // 创建一个与服务器的连接
        ZooKeeper zk = new ZooKeeper("176.122.169.95:2184",
                3000, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("已经触发了" + event.getType() + "事件！");
            }
        });
// 创建一个目录节点s
        zk.create("/testRootPath", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);

// 创建一个子目录节点
        zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/testRootPath", false, null)));
// 取出子目录节点列表
        System.out.println(zk.getChildren("/testRootPath", true));
// 修改子目录节点数据
        zk.setData("/testRootPath/testChildPathOne", "modifyChildDataOne".getBytes(), -1);
        System.out.println("目录节点状态：[" + zk.exists("/testRootPath", true) + "]");
// 创建另外一个子目录节点
        zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/testRootPath/testChildPathTwo", true, null)));
// 删除子目录节点
        zk.delete("/testRootPath/testChildPathTwo", -1);
        zk.delete("/testRootPath/testChildPathOne", -1);
// 删除父目录节点
        zk.delete("/testRootPath", -1);
// 关闭连接
        zk.close();

    }


    @Test
    public void leaderElection() throws InterruptedException, IOException {
        ZooKeeper zk = new ZooKeeper("176.122.169.95:2184",
                3000, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("已经触发了" + event.getType() + "事件！");
            }
        });

        String rootZnode = "/wanghui";
        byte[] leader = null;
        try {

            leader = zk.getData(rootZnode + "/leader", true, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (leader != null) {
            /**
             * TODO slave要做的事
             */
//            following();
        } else {
            String newLeader = null;
            try {
                byte[] localhost = InetAddress.getLocalHost().getAddress();
                newLeader = zk.create(rootZnode + "/leader", localhost,
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (newLeader != null) {
                /**
                 * TODO leader要做的事
                 */
            } else {
//                mutex.wait();
                System.out.println("领导选举失败！");
            }
        }
    }

    @Test
    public void zookeeperMemberChange() throws IOException {
        ZooKeeper zk = new ZooKeeper("176.122.169.95:2184",
                3000, null);

        String rootZnode = "/wanghui";

        try {
            zk.create(rootZnode + "/server", "data".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            zk.getChildren(rootZnode,new Watcher() {
                // 监控所有被触发的事件
                public void process(WatchedEvent event) {
                    System.out.println(rootZnode + "已经触发了" + event.getType() + "事件！");
                    try {
                        zk.getChildren(rootZnode,this);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true);

    }
}
