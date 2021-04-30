package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ExportBank 
{
	 @Id @Column(unique=true)	
	public String URN;
	
	//public String Code;		
	public String Date1;
	public String Amount;
	public String MobStatus;
	
	public String getMobStatus() {
		return MobStatus;
	}
	public void setMobStatus(String mobStatus) {
		MobStatus = mobStatus;
	}
	/*public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}*/
	public String getURN() {
		return URN;
	}
	public void setURN(String uRN) {
		URN = uRN;
	}
	public String getDate1() {
		return Date1;
	}
	public void setDate1(String date) {
		Date1 = date;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	
	
	
}
