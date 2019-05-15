package yt.dubbo.watcher;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yunteng
 */
public class ZookeeperWatcher implements Watcher {
	@Override
	public void process(WatchedEvent watchedEvent) {

	}

	/** 原子计数器，用来统计process被调用的次数 *//*
	private AtomicInteger count = new AtomicInteger();//默认从0开始
	*//** session失效时间 *//*
	private int session_timeout = 120*1000; //120秒
	*//** zookeeper服务器连接地址 *//*
	private String connection_add =
			"10.112.100.87:2181,10.112.99.147:2181";
	*//** 测试数据根路径  *//*
	private String root_path = "/watcher";
	*//** 册数数据字节点路径 *//*
	private String children_path = "/watcher/children";
	*//** zookeeper实例变量 *//*
	private ZooKeeper zoo = null;
	*//** 信号量，用于阻塞主线程等待客户端连接zookeeper服务成功后通知主线程往下继续执行 *//*
	private CountDownLatch countDownLactch = new CountDownLatch(1);

	private String log_main = "【main】:";
	public ZookeeperWatcher(){}

	public void connectZookeeper(){
		try {
			close();
			zoo = new ZooKeeper(connection_add, session_timeout, this);
			countDownLactch.await();//等待客户端成功连接zookeeper服务器才继续往下执行
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 释放连接
	 * @throws InterruptedException
	 *//*
	public void close() {
		if(zoo != null){
			try {
				zoo.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void process(WatchedEvent watchedEvent) {
		System.out.println("开始执行process方法-----event:"+watchedEvent);
		delayMillis(1000);
		if(watchedEvent == null){
			return;
		}
		//取得连接状态
		Event.KeeperState state = watchedEvent.getState();
		//取得事件类型
		Event.EventType eventType = watchedEvent.getType();
		//哪一个节点路径发生变更
		String nodePath = watchedEvent.getPath();
		String log_process = "Watcher-【  "+count.incrementAndGet()+" 】";
		System.out.println(log_process+"收到Watcher的通知");
		System.out.println(log_process+"连接状态："+state);
		System.out.println(log_process+"事件类型："+eventType);

		connectZookeeperState(state , eventType , log_process , nodePath);
	}

	*//**
	 * 判断客户端连接zookeeper服务的连接状态
	 * @param state 服务器端返回的状态对象
	 * @param eventType 事件类型对象
	 * @param log_process 日记标识，标识是process方法里执行的日记
	 * @param nodePath 发生变化的节点
	 *//*
	public void connectZookeeperState(Event.KeeperState state,
									  Event.EventType eventType , String log_process , String nodePath){
		if(Event.KeeperState.SyncConnected == state ){//连接成功
			nodeEventType(eventType, log_process , nodePath);
		}
		else if(Event.KeeperState.Disconnected == state){
			System.out.println(log_process+"客户端连接zookeeper服务器端失败");
		}
		else if(Event.KeeperState.Expired == state){
			System.out.println(log_process+"客户端与zookeeper服务器端会话失败");
		}
		else if(Event.KeeperState.AuthFailed == state){
			System.out.println(log_process+"权限认证失败");
		}
		System.out.println("------------------------------------");
	}

	*//**
	 * 判断节点的事件类型
	 * @param eventType 事件类型对象
	 * @param log_process 日记标识，标识是process方法里执行的日记
	 *//*
	public void nodeEventType(Event.EventType eventType, String log_process, String nodePath ){
		// 没有任何节点，表示创建连接成功(客户端与服务器端创建连接成功后没有任何节点信息)
		if(Event.EventType.None == eventType){
			System.out.println(log_process+"成功链接zookeeper服务器");
			countDownLactch.countDown(); // 通知阻塞的线程可以继续执行
		}
		else if(Event.EventType.NodeCreated == eventType){ //当服务器端创建节点的时候触发
			System.out.println(log_process+" zookeeper服务端创建新的节点");
			delayMillis(2000);
			//zookeeper服务端创建一个新的节点后并对其进行监控,创建完后接着对该节点进行监控,没有此代码将不会在监控该节点
			exists(nodePath,true);
		}
		else if(Event.EventType.NodeDataChanged == eventType){ //被监控该节点的数据发生变更的时候触发
			System.out.println(log_process+"节点的数据更新");
			delayMillis(2000);
			//跟新完后接着对该节点进行监控,没有此代码将不会在监控该节点
			String updateNodeData = readNodeData(nodePath,true);
		}
		else if(Event.EventType.NodeChildrenChanged == eventType){
			// 对应本代码而言只能监控根节点的一级节点变更。如：在根节点直接创建一级节点，
			//或者删除一级节点时触发。如修改一级节点的数据，不会触发，创建二级节点时也不会触发。
			System.out.println("子节点发生变更");
			delayMillis(2000);
			System.out.println(log_process + "子节点列表：" + this.getChildren(root_path, true));
		}
		else if(Event.EventType.NodeDeleted == eventType){
			System.out.println(log_process+"节点："+nodePath+"被删除");
		}

		System.out.println("-------------------------------------");
	}

	*//**
	 * 判断节点是否存在
	 * @param nodePath 节点全路径
	 *//*
	public Stat exists(String nodePath, boolean needWatch) {
		try {
			return zoo.exists(nodePath, needWatch);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	*//**
	 * 获取到该节点下的一级子节点,如果needWatch为true，代表监控该节点下的一级节点
	 * (无论该节点是否有子节点，都对该节点的一级子节点进行监控)
	 * @param path 节点全路径
	 * @param needWatch 是否需要进行watche监控
	 * @return
	 *//*
	private List<String> getChildren(String path, boolean needWatch) {
		try {
			return zoo.getChildren(path, needWatch);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	*//**
	 * 获取节点的内容
	 * @param nodePath 节点的全路径
	 * @param needWatch 是否需要watch true:需要,false:不需要
	 * @return
	 *//*
	private String readNodeData(String nodePath,boolean needWatch) {
		String data = "";
		try {
			//zookeeper watch事件只触发一次,//zookeeper watch事件只触发一次,
			//参数2：为true时表示持续性的去监控，监控的watcher实例为上一个watcher对象
			//还有另一种持续性监控的方法就行创建一个Watcher对象。
			zoo.getData(nodePath, needWatch, null);
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
		return data;
	}

	*//**
	 * 休眠多少毫秒
	 *//*
	public void delayMillis(int millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 创建一个新的节点
	 * @param path 节点的全路径
	 * @param data 节点的数据内容
	 * @param watcher Watcher监控节点的一个实例对象
	 * @return true：创建成功,false：创建失败
	 *//*
	public boolean createPath(String path , String data , Watcher watcher){
		Stat stat = null;
		try {
			//在创建节点之前判断节点是否存在，无论节点是否存在，确保该节点被监控，从而达到在创建节点后watcher能得到服务器端返回的数据信息。
			if(watcher == null){
				stat = exists(path, true);
				// 设置监控（因为zookeeper的监控都是一次性的，要想持续性的监控，第二个参数需要设置为ture）
				//zoo.exists(path, true) ;
			}else{
				stat = zoo.exists(path, watcher) ; // 或者传递一个新的Watcher对象，两则的其别是，
				//为true时watcher对象是上一个监控的上下文对象，对于本代码来说是this对象.
			}
			if(stat == null){
				zoo.create(path, //要创建的节点路径
						data.getBytes(), //节点存储的数据内容
						ZooDefs.Ids.OPEN_ACL_UNSAFE, // 节点权限类型
						CreateMode.PERSISTENT //存储节点的方式（持久化模式）
				);
			}else{
				System.out.println("节点已经存在，无法再创建");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	*//**
	 * 更新节点的内容
	 * @param nodePath 节点的全路径
	 * @param data 要更新的内容
	 * @return true：更新成功,false：更新失败
	 *//*
	public boolean setDate(String nodePath , String data ){
		try {
			Stat stat = zoo.setData(nodePath, data.getBytes(), -1); //忽略所有版本
			System.out.println(log_main+"更新数据成功，path："
					+ nodePath + ", stat: " +stat);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	*//**
	 * 删除节点
	 * @param path 字节点全路径
	 *//*
	public void deleteNode(String path){
		try {
			zoo.delete(path, -1); // -1 忽略所有版本号
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*//**
	 * 删除所有测试数据的节点
	 * @param neetWatch 是否需要watcher
	 *//*
	public void deleteAllTestPath(boolean neetWatch){
		if(this.exists(children_path, neetWatch) != null){
			deleteNode(children_path);
		}
		if(this.exists(root_path, neetWatch) != null){
			this.deleteNode(root_path);
		}
	}

	*//**
	 * <B>方法名称：</B>测试zookeeper监控</BR>
	 * <B>概要说明:</B>主要是测试watch功能</BR>
	 * @param args
	 *//*
	public static void main(String[] args) {
		//创建ZookeeperWatcher实例,为了对服务器端进行节点监控
		ZookeeperWatcher watcher = new ZookeeperWatcher();
		//连接zookeeper服务器
		watcher.connectZookeeper();
		//清除所有测试节点
		watcher.deleteAllTestPath(false);

		if(watcher.createPath(watcher.root_path, "根节点的内容", null) == true){
			watcher.delayMillis(1*1000); // 休息一秒

//			//读取刚创建的节点数据
			System.out.println(watcher.log_main+"---------read root path------");
//			// 创建节点成功后，process方法已经对该节点进行读取数据，并对该节点进行监控操作。
////			watcher.readNodeData(watcher.root_path, true);
//			//读取子节点（实际的目的是对该父节点下的子节点进行监控,无论子节点是否存在）
			System.out.println(watcher.log_main+"----- read children path ----");
			watcher.getChildren(watcher.root_path, true);
//			//更新数据
//			watcher.setDate(watcher.root_path, "更新父节点的内容信息");
//			watcher.delayMillis(2000);
//			//创建子节点
//			watcher.createPath(watcher.children_path, "子节点的数据内容", null);
//			watcher.delayMillis(2000);
//			//子节点的数据变更
//			watcher.setDate(watcher.children_path, "子节点的数据变更");
		}

		watcher.delayMillis(50*60*1000);
		watcher.deleteAllTestPath(false);
		watcher.close(); //释放连接
	}*/
}
