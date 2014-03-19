package com.multiagent.hawklithm.staff.DO;

import java.util.Date;

/**
 <table>
 <tr>
<td>staff_id</td><td>Int</td>
</tr>
 <tr>
<td>gmt_create	</td><td>date	</td><td>表项创建日期 </td>
</tr>
<tr>
<td>gmt_modified	</td>	<td>date	</td><td>表项修改日期 </td>
</tr>
<tr>
<td>staff_name	</td>	<td>varchar	</td><td>姓名 </td>
</tr>
<tr>
<td>staff_phone_number	</td>	<td>char</td><td>电话 </td>
</tr>
<tr>
<td>staff_gender	</td>	<td>char	</td><td>性别 </td>
</tr>
<tr>
<td>staff_age	</td>	<td>int	</td><td>年龄 </td>
</tr>
<tr>
<td>staff_department_id	</td>	<td>char	</td><td>所属科室，对应配置文件中参数 </td>
</tr>
</table>
 * @author hawklithm
 * 2013-12-25下午5:04:45
 */
public class StaffInfoDO {
	private Integer staffId;
	private Date gmtCreate;
	private Date gmtModified;
	private String staffName;
	private String staffPhoneNumber;
	private String staffGender;
	private Integer staffAge;
	private String staffDepartmentId;

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
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

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffPhoneNumber() {
		return staffPhoneNumber;
	}

	public void setStaffPhoneNumber(String staffPhoneNumber) {
		this.staffPhoneNumber = staffPhoneNumber;
	}

	public String getStaffGender() {
		return staffGender;
	}

	public void setStaffGender(String staffGender) {
		this.staffGender = staffGender;
	}

	public Integer getStaffAge() {
		return staffAge;
	}

	public void setStaffAge(Integer staffAge) {
		this.staffAge = staffAge;
	}

	public String getStaffDepartmentId() {
		return staffDepartmentId;
	}

	public void setStaffDepartmentId(String staffDepartmentId) {
		this.staffDepartmentId = staffDepartmentId;
	}
}
