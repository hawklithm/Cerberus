package com.multiagent.hawklithm.sephiroth.DO;


/**
 * 子订单临时数据信息
 * @author hawklithm
 *
 */
public class TempSubPackageOrderDO {
	private Integer parentId;
	private Integer packageType;
	private Integer packageAmount;
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getPackageType() {
		return packageType;
	}
	public void setPackageType(Integer packageType) {
		this.packageType = packageType;
	}
	public Integer getPackageAmount() {
		return packageAmount;
	}
	public void setPackageAmount(Integer packageAmount) {
		this.packageAmount = packageAmount;
	}
	
}
