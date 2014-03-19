package com.multiagent.hawklithm.sephiroth.DO;
/**
 * ¶©µ¥´Ø
 * @author hawklithm
 *
 */
public class SqlPackageOrderClusterDO {
	private SqlMainPackageOrderDO mainOrder;
	private SqlSubPackageOrderDO[] subOrders;
	private SqlReflectOrderDO[] reflectOrders;
	public Integer getOrderId(){
		return mainOrder.getOrderId();
	}
	public SqlMainPackageOrderDO getMainOrder() {
		return mainOrder;
	}
	public void setMainOrder(SqlMainPackageOrderDO mainOrder) {
		this.mainOrder = mainOrder;
	}
	public SqlSubPackageOrderDO[] getSubOrders() {
		return subOrders;
	}
	public void setSubOrders(SqlSubPackageOrderDO[] subOrders) {
		this.subOrders = subOrders;
	}
	public SqlReflectOrderDO[] getReflectOrders() {
		return reflectOrders;
	}
	public void setReflectOrders(SqlReflectOrderDO[] reflectOrders) {
		this.reflectOrders = reflectOrders;
	}
}
