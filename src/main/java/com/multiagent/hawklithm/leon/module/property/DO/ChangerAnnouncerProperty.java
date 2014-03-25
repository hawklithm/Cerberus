package com.multiagent.hawklithm.leon.module.property.DO;

import java.util.ArrayList;
import java.util.List;

import com.multiagent.hawklithm.item.dataobject.ItemInfoDO;

public class ChangerAnnouncerProperty {

	private List<ItemInfoDO> itemAdd=new ArrayList<ItemInfoDO>();
	private List<ItemInfoDO> itemRemove=new ArrayList<ItemInfoDO>();
	private List<Integer> packageAdd=new ArrayList<Integer>();
	private List<Integer> packageRmove=new ArrayList<Integer>();
	private Integer machineRFID;
	private Integer staffRFID;
	
	public ChangerAnnouncerProperty(){}
	public ChangerAnnouncerProperty(int rfid,int staffRfid){
		this.staffRFID=staffRfid;
		this.machineRFID=rfid;
	}
//	public ChangerAnnouncerProperty(ModuleProperty property){
//		this.staffRFID=property.getStaffRFID();
//		this.rfid=property.getRfid();
//	}
	
	public String getProperty() {
		return null;
	}
	
	public void addItem(ItemInfoDO item){
		itemAdd.add(item);
	}
	public void removeItem(ItemInfoDO item){
		itemRemove.add(item);
	}
	public void addPackage(int rfid){
		packageAdd.add(rfid);
	}
	public void removePackage(int rfid){
		packageRmove.add(rfid);
	}
	public List<ItemInfoDO> getItemAdd() {
		return itemAdd;
	}
	public void setItemAdd(List<ItemInfoDO> itemAdd) {
		this.itemAdd = itemAdd;
	}
	public List<ItemInfoDO> getItemRemove() {
		return itemRemove;
	}
	public void setItemRemove(List<ItemInfoDO> itemRemove) {
		this.itemRemove = itemRemove;
	}
	public List<Integer> getPackageAdd() {
		return packageAdd;
	}
	public void setPackageAdd(List<Integer> packageAdd) {
		this.packageAdd = packageAdd;
	}
	public List<Integer> getPackageRmove() {
		return packageRmove;
	}
	public void setPackageRmove(List<Integer> packageRmove) {
		this.packageRmove = packageRmove;
	}

	public Integer getMachineRFID() {
		return machineRFID;
	}

	public void setMachineRFID(Integer machineRFID) {
		this.machineRFID = machineRFID;
	}

	public Integer getStaffRFID() {
		return staffRFID;
	}

	public void setStaffRFID(Integer staffRFID) {
		this.staffRFID = staffRFID;
	}

}
