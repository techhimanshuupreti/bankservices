package com.bankservices.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionManager {

	public static final String DATE_FORMAT = "MMddHHmmss";
	public static final String CHANDAN_DATE_FORMAT = "ddHHmmss";
	public static final int MIN_TRANSACTION_ID = 100;
	public static final int MAX_TRANSACTION_ID = 999;

	public static final TransactionIdGenerator transactionIdGenerator = new TransactionIdGenerator(MIN_TRANSACTION_ID,
			MAX_TRANSACTION_ID);
	public static String serverId = "";

	public TransactionManager() {
	}

	private static Logger logger = LoggerFactory.getLogger(TransactionManager.class.getName());

	public static String getNewTransactionId() {

		if (serverId.equals("")) {

			InetAddress ip;
			try {

				ip = InetAddress.getLocalHost();
				String cleanIp = ip.getHostAddress().replace(".", "");
				serverId = cleanIp.substring(cleanIp.length() - 2, cleanIp.length());

			} catch (UnknownHostException e) {

				logger.error("Exception in TransactionManager", e);

			}

		}

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return transactionId.toString();
	}

	public static String getId() {

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		// transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return transactionId.toString();
	}

	public static String getMerchantId() {

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		// transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "MR" + transactionId.toString();
	}

	public static String getUserId() {

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		// transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "UR" + transactionId.toString();
	}

	public static String getAccountId() {

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		// transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "AC" + transactionId.toString();
	}

	public static String getDepositId() {

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		// transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "DP" + transactionId.toString();
	}

	public static String getBeneficiaryRequirementId() {

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		// transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "BR" + transactionId.toString();
	}

	public static String getBeneficiaryId() {

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		// transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "BE" + transactionId.toString();
	}

	public static String getTransferQuoteId() {

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		// transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "TQ" + transactionId.toString();
	}

	public static String getTransferId() {

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		// transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "TF" + transactionId.toString();
	}
	
	public static String getDocumentId() {

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		// transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "DC" + transactionId.toString();
	}
	
	public static String getTransactionId() {

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		// transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "TX" + transactionId.toString();
	}
	public static String getTransferApprovalId() {

		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		// transactionId.append(serverId);
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "TP" + transactionId.toString();
	}
	
	public static String getCredentialId() {
		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "CR" + transactionId.toString();
	}
	
	public static String getBillingId() {
		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "BL" + transactionId.toString();
	}
	
	public static String getFeeId() {
		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "FE" + transactionId.toString();
	}
	
	public static String getInvoiceId() {
		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "INV" + transactionId.toString();
	}
	
	public static String getInvoiceDetailsId() {
		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "INVDT" + transactionId.toString();
	}
	
	public static String getMerchantUserId() {
		final LocalDateTime currentTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CHANDAN_DATE_FORMAT);
		final StringBuilder transactionId = new StringBuilder();
		transactionId.append(transactionIdGenerator.next());
		transactionId.append((Year.now().toString()).substring(3));
		transactionId.append(currentTime.format(formatter));
		return "MRUR" + transactionId.toString();
	}
}
