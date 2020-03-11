package com.sia.rabbitmqplus.demo.bean;

import com.sia.rabbitmqplus.api.SIA;
import com.sia.rabbitmqplus.binding.PropertyHelper;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;

/**
 * @author lipengfei
 */
public class SendP2P {
	{
		try {
			sendTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 这是新提供的API，发送消息的类型为String（比如JSON格式）
	 * 函数原型：
	 * boolean send(String exchangeName, String queueName, String message)
	 * 有两种模式支持：
	 * 1，点对点模式，该模式下exchangeName设为null，指定发送的queueName
	 * 注意，该模式下可能由于网络原因发送失败，注意处理失败情况
	 * 2，发布订阅模式，该模式下指定发送的exchangeName，queueName设为""（空串）
	 * 注意，该模式下没有反馈，只要不是网络问题，均能成功
	 * 下面是点对点模式的实例
	 * 发送的队列名称，必须指定，发送之前，确认接收者已启动
	 */
	private final static String QUEUE_NAME = "sia_client_test_send_p2p";

	public void sendTest() throws IOException, Exception {
		int size = 9;
		while (true) {
			for (int i = size; i >= 0; i--) {
				// 发送的消息
				String msg = "" + i;
				// 注意处理失败 的情况，一般是由于网络原因断线重连，整个过程大概需要10秒钟才能恢复，注意与发布订阅模式相区别
				while (!SIA.send(null, QUEUE_NAME, msg))
					Thread.sleep(10 * 1000);

				Thread.sleep(10);
			}

			Thread.sleep(10 * 1000);
		}
	}
}
