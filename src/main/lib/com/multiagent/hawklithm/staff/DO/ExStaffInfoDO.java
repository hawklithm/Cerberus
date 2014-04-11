package com.multiagent.hawklithm.staff.DO;

public class ExStaffInfoDO extends StaffInfoDO {
	private Integer staffAgeStart;
	private Integer staffAgeEnd;
	private Integer equipmentId;

	public Integer getStaffAgeStart() {
		return staffAgeStart;
	}

	public void setStaffAgeStart(Integer staffAgeStart) {
		this.staffAgeStart = staffAgeStart;
	}

	public Integer getStaffAgeEnd() {
		return staffAgeEnd;
	}

	public void setStaffAgeEnd(Integer staffAgeEnd) {
		this.staffAgeEnd = staffAgeEnd;
	}

	public Integer getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
}
