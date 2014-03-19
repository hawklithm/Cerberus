package com.multiagent.hawklithm.transport.DO;

import java.util.Date;

import com.multiagent.hawklithm.gps.DO.GpsInfoDO;

public class TransportWithGPSInfoSubOrderDO {
	private Integer id;
	private Date gmtCreate;
	private Date gmtModified;
	private Integer parentId;
	private Integer vehicleId;
	private Integer subOrderId;
	private GpsInfoDO gps[];
	
	public TransportWithGPSInfoSubOrderDO(SqlTransportSubOrderDO subOrder){
		setId(subOrder.getId());
		setGmtCreate(subOrder.getGmtCreate());
		setGmtModified(subOrder.getGmtModified());
		setParentId(subOrder.getParentId());
		setVehicleId(subOrder.getVehicleId());
		setSubOrderId(subOrder.getSubOrderId());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Integer getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(Integer subOrderId) {
		this.subOrderId = subOrderId;
	}

	public GpsInfoDO[] getGps() {
		return gps;
	}

	public void setGps(GpsInfoDO[] gps) {
		this.gps = gps;
	}

}
