package com.bankservices.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.bankservices.utils.PropertiesManager;
import com.bankservices.utils.TransactionManager;

@Service
public class StorageService {

	private static Logger logger = LoggerFactory.getLogger(StorageService.class.getName());

	/*
	 * @Value("${application.bucket.name}") private String bucketName;
	 */

	@Autowired
	private AmazonS3 s3Client;

	@Autowired
	private PropertiesManager propertiesManager;
	
	public String uploadFile(MultipartFile file) {
		File fileObj = convertMultiPartFileToFile(file);
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String fileName = TransactionManager.getNewTransactionId() + "." + extension;
		String parentFolder = propertiesManager.getSystemProperty("AWS_S3_BUCKET_MAIN_DOCUMENT_FOLDER");
		String merOnboardingDoc = propertiesManager.getSystemProperty("AWS_S3_BUCKET_MERCHANT_ONBOARDIND_DOCUMENT_FOLDER");
		logger.info("uploadFile path s3 = "+parentFolder+merOnboardingDoc);
		s3Client.putObject(new PutObjectRequest(parentFolder+merOnboardingDoc, fileName, fileObj));
		
		fileObj.delete();
		return fileName;
	}

	public byte[] downloadFile(String fileName) {
		String parentFolder = propertiesManager.getSystemProperty("AWS_S3_BUCKET_MAIN_DOCUMENT_FOLDER");
		String merOnboardingDoc = propertiesManager.getSystemProperty("AWS_S3_BUCKET_MERCHANT_ONBOARDIND_DOCUMENT_FOLDER");
		logger.info("downloadFile path s3 = "+parentFolder+merOnboardingDoc);
		
		S3Object s3Object = s3Client.getObject(parentFolder+merOnboardingDoc, fileName);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		try {
			byte[] content = IOUtils.toByteArray(inputStream);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String deleteFile(String fileName) {
		String parentFolder = propertiesManager.getSystemProperty("AWS_S3_BUCKET_MAIN_DOCUMENT_FOLDER");
		logger.info("deleteFile path s3 = "+parentFolder+" "+fileName);
		s3Client.deleteObject(parentFolder, fileName);
		return fileName + " removed ...";
	}

	public File convertMultiPartFileToFile(MultipartFile file) {
		File convertedFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
			fos.write(file.getBytes());
		} catch (IOException e) {
			logger.error("Error converting multipartFile to file", e);
		}
		return convertedFile;
	}

	// save payout document
	public boolean uploadDocFile(String filePath, File fileObj) {
		try {
			try {
				logger.info("downloadFile path s3 = "+filePath+" "+fileObj.getName());
				S3Object s3Object = s3Client.getObject(filePath, fileObj.getName());
			}catch(Exception e) {
				s3Client.putObject(new PutObjectRequest(filePath, fileObj.getName(), fileObj));
				logger.info("downloadFile path s3 = "+filePath+" "+fileObj.getName());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public byte[] downloadFile(String fileName,String filePath) {
		logger.info("downloadFile path s3 = "+filePath+fileName);
		S3Object s3Object = s3Client.getObject(filePath, fileName);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		try {
			byte[] content = IOUtils.toByteArray(inputStream);
			return content;
		} catch (IOException e) {
			logger.info("error s3 bucket = "+e);
			e.printStackTrace();
		}
		return null;
	}
	
	public String uploadFile(MultipartFile file,String newFolderName) {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String fileName = TransactionManager.getNewTransactionId() + "." + extension;
		
		File fileObj = convertMultiPartFileToFile(file);
		String parentFolder = propertiesManager.getSystemProperty("AWS_S3_BUCKET_MAIN_DOCUMENT_FOLDER");
		String merOnboardingDoc = propertiesManager.getSystemProperty("AWS_S3_BUCKET_MERCHANT_ONBOARDIND_DOCUMENT_FOLDER");
		logger.info("uploadFile doc for upload path = " + parentFolder + merOnboardingDoc + newFolderName	+ "/" + fileName);
		
		s3Client.putObject(new PutObjectRequest(parentFolder+merOnboardingDoc+newFolderName, fileName, fileObj));
		
		fileObj.delete();
		return fileName;
	}
	
	public String deleteFile(String fileName,String filePath) {
		logger.info("deleteFile path s3 = "+filePath+"/"+fileName);
		try {
		s3Client.deleteObject(filePath, fileName);
		logger.info(fileName + " removed ...");
		return fileName + " removed ...";
		}catch (Exception e) {
			logger.info("error s3 bucket = "+e);
			e.printStackTrace();
			logger.info(fileName + " is not removed ...");
			return null;
		}
	}
	
	public String uploadFileGenric(MultipartFile file,String parentFolder,String subFolder, String newFolderName) {
		try {
			logger.info("file extension="+file.getContentType().split("/")[1]);
		String extension = file.getContentType().split("/")[1]; 
		String fileName = TransactionManager.getNewTransactionId() + "." + extension;
		
		File fileObj = convertMultiPartFileToFile(file);
		logger.info("uploadFile doc for upload path = " + parentFolder + subFolder + newFolderName	+ "/" + fileName);
		s3Client.putObject(new PutObjectRequest(parentFolder+subFolder+newFolderName, fileName, fileObj));
		
		fileObj.delete();
		return fileName;
		}catch (Exception e) {
			logger.info("Exception "+e);
			return null;
		}
		
	}
}
