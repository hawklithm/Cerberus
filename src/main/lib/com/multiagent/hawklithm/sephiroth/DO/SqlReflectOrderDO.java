package com.multiagent.hawklithm.sephiroth.DO;

import java.util.Date;
/**
 * ������ tb_reflect_order
reflet_order_id				int
gmt_create					date				���������
gmt_modified				date				�����޸�����
Parent_id						int					������ID
item_type						int					��е��������
item_amount					int					��е����
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
