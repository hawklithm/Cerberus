package com.multiagent.hawklithm.rpc.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.multiagent.hawklithm.davinci.rpc.DO.RPCSystemProtocol;

public class RPCLockManager {
	Map<UUID, DataTogether> map = new HashMap<UUID, DataTogether>();

	private class DataTogether {
		private CountDownLatch lock;
		private RPCSystemProtocol msg;

		public DataTogether(CountDownLatch lock, RPCSystemProtocol msg) {
			this.lock = lock;
			this.msg = msg;
		}

		public DataTogether(CountDownLatch lock) {
			this.lock = lock;
		}

		public CountDownLatch getLock() {
			return lock;
		}

		public void setLock(CountDownLatch lock) {
			this.lock = lock;
		}

		public RPCSystemProtocol getMsg() {
			return msg;
		}

		public void setMsg(RPCSystemProtocol msg) {
			this.msg = msg;
		}

		public void releaseLock() {
			lock.countDown();
		}

	}

	public RPCSystemProtocol waitforAnswer(UUID uuid) throws InterruptedException {
		DataTogether data = new DataTogether(new CountDownLatch(1));
		map.put(uuid, data);
		if (!data.getLock().await(10, TimeUnit.SECONDS)) {
			throw new InterruptedException();
		}
		return data.getMsg();
	}

	public void AnswerReceived(UUID uuid, RPCSystemProtocol msg) {
		while (!map.containsKey(uuid)) {
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		DataTogether data = map.get(uuid);
		data.setMsg(msg);
		data.releaseLock();
	}
}
