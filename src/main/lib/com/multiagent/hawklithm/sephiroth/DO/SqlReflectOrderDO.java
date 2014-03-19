package com.multiagent.hawklithm.sephiroth.DO;

import java.util.Date;
/**
 * 返件单 tb_reflect_order
reflet_order_id				int
gmt_create					date				表项创建日期
gmt_modified				date				表项修改日期
Parent_id						int					父订单ID
item_type						int					器械类型类型
item_amount					int					器械数量
 * @author hawklithm
 *
 */
public class SqlReflectOrderDO {
	private Integer reflectOrderId;
	private Date gmtCreate;
	private Date gmtModified;
	private Integer parentId;
	private Integer itemType;
	private Integer itemAmount;
	public Integer getReflectOrderId() {
		return reflectOrderId;
	}
	public void setReflectOrderId(Integer reflectOrderId) {
		this.reflectOrderId = reflectOrderId;
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
	public Integer getItemType() {
		return itemType;
	}
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	public Integer getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(Integer itemAmount) {
		this.itemAmount = itemAmount;
	}
}
