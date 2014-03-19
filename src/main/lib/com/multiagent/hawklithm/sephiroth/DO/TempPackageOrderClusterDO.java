package com.multiagent.hawklithm.sephiroth.DO;

import java.util.ArrayList;
import java.util.List;

public class TempPackageOrderClusterDO {
	private TempMainPackageOrderDO mainOrder;
	private List<TempSubPackageOrderDO> subOrders = new ArrayList<TempSubPackageOrderDO>();
	private List<TempReflectOrderDO> reflectOrders = new ArrayList<TempReflectOrderDO>();

	public TempMainPackageOrderDO getMainOrder() {
		return mainOrder;
	}

	public void setMainOrder(TempMainPackageOrderDO mainOrder) {
		this.mainOrder = mainOrder;
	}

	public List<TempSubPackageOrderDO> getSubOrders() {
		return subOrders;
	}

	public void setSubOrders(List<TempSubPackageOrderDO> subOrders) {
		this.subOrders = subOrders;
	}

	public List<TempReflectOrderDO> getReflectOrders() {
		return reflectOrders;
	}

	public void setReflectOrders(List<TempReflectOrderDO> reflectOrders) {
		this.reflectOrders = reflectOrders;
	}

	public void addSubOrder(TempSubPackageOrderDO order) {
		if (subOrders == null) {
			subOrders = new ArrayList<TempSubPackageOrderDO>();
		}
		subOrders.add(order);
	}

	public void addReflectOrder(TempReflectOrderDO order) {
		if (reflectOrders == null) {
			reflectOrders = new ArrayList<TempReflectOrderDO>();
		}
		reflectOrders.add(order);
	}
}
