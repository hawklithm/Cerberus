package com.multiagent.hawklithm.sephiroth.DO;

import java.util.Date;
/**
 * 手术包子订单 tb_sub_order
sub_order_id					int
gmt_create					date				表项创建日期
gmt_modified				date				表项修改日期
Parent_id						int					父订单ID
package_type				int					医疗包类型
package_amount			int					医疗包数量
 * @author hawklithm
 *
 */
public class SqlSubPackageOrderDO {
	private int subOrderId;
	private Date gmtCreate;
	private Date gmtModified;
	private int parentId;
	private int packageType;
	private int packageAmount;
	public int getPackageAmount() {
		return packageAmount;
	}
	public void setPackageAmount(int packageAmount) {
		this.packageAmount = packageAmount;
	}
	public int getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(int subOrderId) {
		this.subOrderId = subOrderId;
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
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getPackageType() {
		return packageType;
	}
	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}
	
}
