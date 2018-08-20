package com.mannu;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataUtility {
	private Connection con;
	
	public DataUtility() {
		this.con=DbCon.DbCon();
	}

	public List<AckDetails> getData() {
		List<AckDetails> adetails=new ArrayList<AckDetails>();
		AckDetails ad=null;
		try {
			if(con.isClosed()) {
				this.con=DbCon.DbCon();
			}
			ResultSet rs=null;
			CallableStatement cs=con.prepareCall("{call pdfvalidate}");
			boolean sta=cs.execute();
			int rowe=0;
			while (sta || rowe !=-1) {
				if(sta) {
					rs=cs.getResultSet();
					break;
				} else {
					rowe=cs.getUpdateCount();
				}
				sta=cs.getMoreResults();
			}
			while (rs.next()) {
				ad=new AckDetails();
				ad.setInwardNo(rs.getInt(1));
				ad.setAckNo(rs.getString(2));
				ad.setUploadDate(rs.getString(3));
				ad.setCropBy(rs.getString(4));
				ad.setCropTime(rs.getString(5));
				ad.setPhotoPath(rs.getString(6));
				ad.setSignaturePath(rs.getString(7));
				adetails.add(ad);
			}
			cs.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			return adetails;
		}
		return adetails;
	}

	public int updateData(int inwardNo, String ackNo, String string) {
		int status=0;
		try {
			if(con.isClosed()) {
				this.con=DbCon.DbCon();
			}
			ResultSet rs=null;
			CallableStatement cs=con.prepareCall("{call upvalida (?,?,?)}");
			cs.setInt(1, inwardNo);
			cs.setString(2, ackNo);
			cs.setString(3, string);
			boolean sta=cs.execute();
			int rowe=0;
			while (sta || rowe !=-1) {
				if(sta) {
					rs=cs.getResultSet();
					break;
				} else {
					rowe=cs.getUpdateCount();
				}
				sta=cs.getMoreResults();
			}
			if(rs.next()) {
				status=rs.getInt(1);
			} else {
				status=0;
			}
			
		} catch (Exception e) {
			return status;
		}
		
		return status;
	}

	public List<AckDetails> getErrorData() {
		List<AckDetails> adetails=new ArrayList<AckDetails>();
		AckDetails ad=null;
		try {
			if(con.isClosed()) {
				this.con=DbCon.DbCon();
			}
			ResultSet rs=null;
			CallableStatement cs=con.prepareCall("{call geterror}");
			boolean sta=cs.execute();
			int rowe=0;
			while (sta || rowe !=-1) {
				if(sta) {
					rs=cs.getResultSet();
					break;
				} else {
					rowe=cs.getUpdateCount();
				}
				sta=cs.getMoreResults();
			}
			int i=0;
			while (rs.next()) {
				i=1+i;
				ad=new AckDetails();
				ad.setId(i);
				ad.setInwardNo(rs.getInt(1));
				ad.setAckNo(rs.getString(2));
				ad.setCropBy(rs.getString(3));
				ad.setCropTime(rs.getString(4));
				ad.setPhotoStatus(rs.getString(5));
				ad.setSignatureStatus(rs.getString(6));
				adetails.add(ad);
			}
			cs.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			return adetails;
		}
		return adetails;
	}

}
