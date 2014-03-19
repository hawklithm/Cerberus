//package com.multiagent.hawklithm.transport.DO;
//
//import java.util.List;
//
//import com.multiagent.hawklithm.gps.DO.GpsInfoDO;
//
///**
// * 对子运单和gps信息整合
// * 
// * @author hawklithm 2013-12-30下午3:31:30
// */
//public class SqlTransportSubOrderCombineWithGPSDO {
//	private SqlTransportSubOrderDO subOrder;
//	private List<GpsInfoDO> gpsInfos;
//
//	public SqlTransportSubOrderCombineWithGPSDO() {
//	}
//
//	public SqlTransportSubOrderCombineWithGPSDO(SqlTransportSubOrderDO subOrder, List<GpsInfoDO> gpsInfos) {
//		this.subOrder = subOrder;
//		this.gpsInfos = gpsInfos;
//	}
//
//	public void addGpsInfo(GpsInfoDO info) {
//		gpsInfos.add(info);
//	}
//
//	public Integer getId() {
//		return subOrder.getId();
//	}
//
//	public void setId(Integer id) {
//		subOrder.setId(id);
//	}
//
//	public Integer getParentId() {
//		return subOrder.getParentId();
//	}
//
//	public void setParentId(Integer parentId) {
//		subOrder.setParentId(parentId);
//	}
//
//	public Integer getVehicleId() {
//		return subOrder.getVehicleId();
//	}
//
//	public void setVehicleId(Integer vehicleId) {
//		subOrder.setVehicleId(vehicleId);
//	}
//
//	public Integer getSubOrderId() {
//		return subOrder.getSubOrderId();
//	}
//
//	public void setSubOrderId(Integer subOrderId) {
//		subOrder.setSubOrderId(subOrderId);
//	}
//
//	public SqlTransportSubOrderDO getSubOrder() {
//		return subOrder;
//	}
//
//	public void setSubOrder(SqlTransportSubOrderDO subOrder) {
//		this.subOrder = subOrder;
//	}
//
//	public List<GpsInfoDO> getGpsInfos() {
//		return gpsInfos;
//	}
//
//	public void setGpsInfos(List<GpsInfoDO> gpsInfos) {
//		this.gpsInfos = gpsInfos;
//	}
//
//}
