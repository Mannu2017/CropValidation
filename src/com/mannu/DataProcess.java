package com.mannu;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;

public class DataProcess extends TimerTask {
	
	DataUtility utility=new DataUtility();

	@Override
	public void run() {
		HaarCascadeDetector detector = new HaarCascadeDetector();
		System.out.println("Welcome Mannu");
		List<AckDetails> adetails=utility.getData();
		if(adetails.size()>0) {
			for (AckDetails ad : adetails) {
				System.out.println("File Details: "+ad.getInwardNo()+"_"+ad.getAckNo());
				BufferedImage img;
				try {
					File phfil=new File(ad.getPhotoPath());
					img = ImageIO.read(phfil);
					List<DetectedFace> faces = detector.detectFaces(ImageUtilities.createFImage(img));
					   if (faces != null) {
					       Iterator < DetectedFace > dfi = faces.iterator();
					       String sta="";
						   if(dfi.hasNext()) {
							   sta="face found";
						   } else {
							   sta="No face found";
						   }
						   ImageInfo info=Sanselan.getImageInfo(phfil);
						   if(sta!="face found" || info.getPhysicalWidthDpi()!=200 || info.getPhysicalHeightDpi()!=200 || 
								   info.getWidth()!=204 || info.getHeight()!=204) {
							   if(info.getWidth()!=204 || info.getHeight()!=204) {
								   int status = utility.updateData(ad.getInwardNo(),ad.getAckNo(),"photocerror"); 
									  if(status==1) {
										  System.out.println("Photo Crop Error");
									  }
							   } else {
									  int status = utility.updateData(ad.getInwardNo(),ad.getAckNo(),"photo"); 
									  if(status==1) {
										  System.out.println("Photo Error");
									  }
							   }
						   } else {
							   int status = utility.updateData(ad.getInwardNo(),ad.getAckNo(),"photook"); 
								  if(status==1) {
									  System.out.println("Photo OK");
								  }
						   }
						   
					   }
				} catch (IOException | ImageReadException e) {
					e.printStackTrace();
				}
				
				try {
					ImageInfo info=Sanselan.getImageInfo(new File(ad.getSignaturePath()));
					   if(info.getPhysicalWidthDpi()!=200 || info.getPhysicalHeightDpi()!=200 || 
							   info.getWidth()!=333 || info.getHeight()!=137) {
						   System.out.println("Signature DPI X:"+info.getPhysicalWidthDpi()+" Y:"+info.getPhysicalHeightDpi()+" W:"+info.getWidth()+" H:"+info.getHeight());
						   int status = utility.updateData(ad.getInwardNo(),ad.getAckNo(),"sig"); 
							  if(status==1) {
								  System.out.println("Signature Update Done");
							  }
					} else {
						 int status = utility.updateData(ad.getInwardNo(),ad.getAckNo(),"sigok"); 
						  if(status==1) {
							  System.out.println("Signature Update Done");
						  }
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		SendMail sm=new SendMail();
		sm.start();
	}

}
