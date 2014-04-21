package com.multiagent.hawklithm.item.dataobject;


public class ExItemInfoDO extends ItemInfoDO {
	public static ExItemInfoDO getExtend(ItemInfoDO oldInfo){
		ExItemInfoDO ans=new ExItemInfoDO();
		ans.setItemId(oldInfo.getItemId());
		ans.setGmtCreate(oldInfo.getGmtCreate());
		ans.setGmtModified(oldInfo.getGmtModified());
		ans.setItemName(oldInfo.getItemName());
		ans.setItemType(oldInfo.getItemType());
		ans.setManufacturer(oldInfo.getManufacturer());
		ans.setInterconvertible(oldInfo.isInterconvertible());
		ans.setRemark(oldInfo.getRemark());
		return ans;
	}
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
