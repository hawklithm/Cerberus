package com.multiagent.hawklithm.gps.DO;

import java.util.Date;

/**
 *
tb_gps记录车辆行驶gps信息
gps_id					int
Gmt_create			timestamp		创建时间
Gmt_modified		Timestamp	修改时间	
vehicle_id				int					车辆id
longitude				double			经度
latitude					double			纬度
station_name			varchar			所在站点
time						Timestamp	时间
sub_trans_id			int				子运单id
 * @author hawklithm
 * 2013-12-30下午2:20:57
 */
public class GpsInfoDO {
	private Integer gpsId;
	private Date gmtCreate;
	private Date gmtModified;
	private Integer vehicleId;
	private Double longitude;
	private Double latitude;
	private String stationName;
	private Date time;
	private Integer subTransId;
	public Integer getSubTransId() {
		return subTransId;
	}
	public void setSubTransId(Integer subTransId) {
		this.subTransId = subTransId;
	}
	public Integer getGpsId() {
		return gpsId;
	}
	public void setGpsId(Integer gpsId) {
		this.gpsId = gpsId;
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
	public Integer getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
