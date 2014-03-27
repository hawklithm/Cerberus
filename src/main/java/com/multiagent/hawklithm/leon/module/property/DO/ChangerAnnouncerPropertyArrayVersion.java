package com.multiagent.hawklithm.leon.module.property.DO;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.multiagent.hawklithm.item.dataobject.ItemInfoDO;

public class ChangerAnnouncerPropertyArrayVersion {
	private ItemInfoDO[] itemAdd;
	private ItemInfoDO[] itemRemove;
	private Integer[] packageAdd;
	private Integer[] packageRemove;
	protected Integer machineRFID;
//	protected Integer[] itemRFIDs;
//	protected Integer[] packageRFIDs;
	protected Integer staffRFID;
//	protected Integer cubage;

	public ChangerAnnouncerPropertyArrayVersion(ChangerAnnouncerProperty property) {
		itemAdd = property.getItemAdd().toArray(new ItemInfoDO[property.getItemAdd().size()]);
		itemRemove = property.getItemRemove().toArray(new ItemInfoDO[property.getItemRemove().size()]);
		packageAdd = property.getPackageAdd().toArray(new Integer[property.getPackageAdd().size()]);
		packageRemove = property.getPackageRmove().toArray(
				new Integer[property.getPackageRmove().size()]);
		machineRFID=property.getMachineRFID();
		staffRFID=property.getStaffRFID();
//		itemRFIDs = property.getItemRFIDs().toArray(new Integer[property.getItemRFIDs().size()]);
//		packageRFIDs = property.getPackageRFIDs().toArray(
//				new Integer[property.getItemRFIDs().size()]);
//		staffRFID = property.getStaffRFID();
//		cubage = property.getCubage();
	}

	public ChangerAnnouncerProperty toOriginalVersion() {
		ChangerAnnouncerProperty ret = new ChangerAnnouncerProperty();
		List<ItemInfoDO> itemAdd = new ArrayList<ItemInfoDO>();
		List<ItemInfoDO> itemRemove = new ArrayList<ItemInfoDO>();
		List<Integer> packageAdd = new ArrayList<Integer>();
		List<Integer> packageRmove = new ArrayList<Integer>();
//		 Set<ItemInfoDO> itemRFIDs=new HashSet<ItemInfoDO>();
//		 Set<Integer> packageRFIDs=new HashSet<Integer>();
		CollectionUtils.addAll(itemAdd, this.itemAdd);
		ret.setItemAdd(itemAdd);
		CollectionUtils.addAll(itemRemove, this.itemRemove);
		ret.setItemRemove(itemRemove);
		CollectionUtils.addAll(packageAdd, this.packageAdd);
		ret.setPackageAdd(packageAdd);
		CollectionUtils.addAll(packageRmove, this.packageRemove);
		ret.setPackageRmove(packageRmove);
//		CollectionUtils.addAll(itemRFIDs, this.itemRFIDs);
//		ret.setItemRFIDs(itemRFIDs);
//		CollectionUtils.addAll(packageRFIDs, this.packageRFIDs);
//		ret.setPackageRFIDs(packageRFIDs);
//		ret.setRfid(rfid);
		ret.setStaffRFID(staffRFID);
		ret.setMachineRFID(machineRFID);
//		ret.setCubage(cubage);
		
		return ret;
	}
	
	public boolean isDataNull(){
		if (itemAdd!=null&&itemAdd.length!=0){
			return false;
		}
		if (itemRemove!=null&&itemRemove.length!=0){
			return false;
		}
		if (packageAdd!=null&&packageAdd.length!=0){
			return false;
		}
		if (packageRemove!=null&&packageRemove.length!=0){
			return false;
		}
		return true;
	}
}
