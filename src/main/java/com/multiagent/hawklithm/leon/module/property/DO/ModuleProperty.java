package com.multiagent.hawklithm.leon.module.property.DO;

import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;

public abstract class ModuleProperty implements PropertyCollector{
	protected int rfid=(int) (Math.random() * 100000);
	protected Set<Integer> itemRFIDs;
	protected Set<Integer> packageRFIDs;
	protected int staffRFID;
	protected int cubage;
	protected Gson gson = new Gson();

	public void addItem(Integer rfid){
		getItemRFIDs().add(rfid);
	}
	public void addPackage(Integer rfid){
		getPackageRFIDs().add(rfid);
	}
	public boolean removeItem(Integer rfid){
		return getItemRFIDs().remove(rfid);
	}
	public boolean removePackage(Integer rfid){
		return getPackageRFIDs().remove(rfid);
	}
	
	public Set<Integer> getPackageRFIDs() {
		return packageRFIDs;
	}

	public void setPackageRFIDs(Set<Integer> packageRFIDs) {
		this.packageRFIDs = packageRFIDs;
	}

	public int getRfid() {
		return rfid;
	}

	public void setRfid(int rfid) {
		this.rfid = rfid;
	}

	public Set<Integer> getItemRFIDs() {
		return itemRFIDs;
	}

	public void setItemRFIDs(Set<Integer> itemRFIDs) {
		this.itemRFIDs = itemRFIDs;
	}

	public int getStaffRFID() {
		return staffRFID;
	}

	public void setStaffRFID(int staffRFID) {
		this.staffRFID = staffRFID;
	}

	public int getCubage() {
		return cubage;
	}

	public void setCubage(int cubage) {
		this.cubage = cubage;
	}

	protected String getInfoInStringFormat() {
		String msg = "module RFID: " + this.getRfid() + ", staffRFID: " + this.getStaffRFID() + ", Cubage: " + this.getCubage();
		if (!CollectionUtils.isEmpty(getItemRFIDs())) {
			msg += ", item amount: " + this.getItemRFIDs().size();
			msg += ", item RFID: " + gson.toJson(this.getItemRFIDs());
		}
		if (!CollectionUtils.isEmpty(getPackageRFIDs())) {
			msg += ", package amount: ";
			msg += ", package RFID: " + gson.toJson(this.getPackageRFIDs());
		}
		return msg;
	}
}
