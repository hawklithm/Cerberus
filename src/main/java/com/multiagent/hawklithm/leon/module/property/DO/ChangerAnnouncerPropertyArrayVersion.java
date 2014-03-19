package com.multiagent.hawklithm.leon.module.property.DO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

public class ChangerAnnouncerPropertyArrayVersion {
	private Integer[] itemAdd;
	private Integer[] itemRemove;
	private Integer[] packageAdd;
	private Integer[] packageRmove;
	protected Integer rfid;
	protected Integer[] itemRFIDs;
	protected Integer[] packageRFIDs;
	protected Integer staffRFID;
	protected Integer cubage;

	public ChangerAnnouncerPropertyArrayVersion(ChangerAnnouncerProperty property) {
		itemAdd = property.getItemAdd().toArray(new Integer[property.getItemAdd().size()]);
		itemRemove = property.getItemRemove().toArray(new Integer[property.getItemRemove().size()]);
		packageAdd = property.getPackageAdd().toArray(new Integer[property.getPackageAdd().size()]);
		packageRmove = property.getPackageRemove().toArray(
				new Integer[property.getPackageRemove().size()]);
		rfid = property.getRfid();
		itemRFIDs = property.getItemRFIDs().toArray(new Integer[property.getItemRFIDs().size()]);
		packageRFIDs = property.getPackageRFIDs().toArray(
				new Integer[property.getItemRFIDs().size()]);
		staffRFID = property.getStaffRFID();
		cubage = property.getCubage();
	}

	public ChangerAnnouncerProperty toOriginalVersion() {
		ChangerAnnouncerProperty ret = new ChangerAnnouncerProperty();
		List<Integer> itemAdd = new ArrayList<Integer>();
		List<Integer> itemRemove = new ArrayList<Integer>();
		List<Integer> packageAdd = new ArrayList<Integer>();
		List<Integer> packageRmove = new ArrayList<Integer>();
		 Set<Integer> itemRFIDs=new HashSet<Integer>();
		 Set<Integer> packageRFIDs=new HashSet<Integer>();
		CollectionUtils.addAll(itemAdd, this.itemAdd);
		ret.setItemAdd(itemAdd);
		CollectionUtils.addAll(itemRemove, this.itemRemove);
		ret.setItemRemove(itemRemove);
		CollectionUtils.addAll(packageAdd, this.packageAdd);
		ret.setPackageAdd(packageAdd);
		CollectionUtils.addAll(packageRmove, this.packageRmove);
		ret.setPackageRemove(packageRmove);
		CollectionUtils.addAll(itemRFIDs, this.itemRFIDs);
		ret.setItemRFIDs(itemRFIDs);
		CollectionUtils.addAll(packageRFIDs, this.packageRFIDs);
		ret.setPackageRFIDs(packageRFIDs);
		ret.setRfid(rfid);
		ret.setStaffRFID(staffRFID);
		ret.setCubage(cubage);
		
		return ret;
	}
}
