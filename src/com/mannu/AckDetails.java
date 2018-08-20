package com.mannu;

public class AckDetails {
	
	private int InwardNo;
	private String AckNo;
	private String UploadDate;
	private String CropBy;
	private String CropTime;
	private String PhotoPath;
	private String SignaturePath;
	private int id;
	private String PhotoStatus;
	private String SignatureStatus;
	
	public String getPhotoStatus() {
		return PhotoStatus;
	}
	public void setPhotoStatus(String photoStatus) {
		PhotoStatus = photoStatus;
	}
	public String getSignatureStatus() {
		return SignatureStatus;
	}
	public void setSignatureStatus(String signatureStatus) {
		SignatureStatus = signatureStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInwardNo() {
		return InwardNo;
	}
	public void setInwardNo(int inwardNo) {
		InwardNo = inwardNo;
	}
	public String getAckNo() {
		return AckNo;
	}
	public void setAckNo(String ackNo) {
		AckNo = ackNo;
	}
	public String getUploadDate() {
		return UploadDate;
	}
	public void setUploadDate(String uploadDate) {
		UploadDate = uploadDate;
	}
	public String getCropBy() {
		return CropBy;
	}
	public void setCropBy(String cropBy) {
		CropBy = cropBy;
	}
	public String getCropTime() {
		return CropTime;
	}
	public void setCropTime(String cropTime) {
		CropTime = cropTime;
	}
	public String getPhotoPath() {
		return PhotoPath;
	}
	public void setPhotoPath(String photoPath) {
		PhotoPath = photoPath;
	}
	public String getSignaturePath() {
		return SignaturePath;
	}
	public void setSignaturePath(String signaturePath) {
		SignaturePath = signaturePath;
	}

	

}
