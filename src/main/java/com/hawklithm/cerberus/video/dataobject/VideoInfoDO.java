package com.hawklithm.cerberus.video.dataobject;

import java.util.Date;

/**
 * 存放video信息
 * @author hawklithm
 * 2014-5-21下午3:32:17
 */
public class VideoInfoDO {
	private byte[] data;
	private String name;
	private Date startTime;
	private Date endTime;
	private Integer machineId;
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getMachineId() {
		return machineId;
	}
	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}
}
