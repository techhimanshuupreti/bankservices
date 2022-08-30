package com.bankservices.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bankservices.constants.CommonConstant;

public class DateCreater {

	private static Logger logger = LoggerFactory.getLogger(DateCreater.class.getName());
	public static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static String defaultFromDate() {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(CommonConstant.INPUT_DATE_FORMAT.getValue());
		Date currentDate = new Date();
		try {
			return inputDateFormat.format(currentDate);
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String defaultToDate() {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(CommonConstant.INPUT_DATE_FORMAT.getValue());
		Calendar cal = Calendar.getInstance();
		Date currentDate = new Date();
		try {
			cal.setTime(currentDate);
			cal.add(Calendar.DAY_OF_MONTH, -30);
			return inputDateFormat.format(cal.getTime());
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String yesterdayDate() {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(CommonConstant.OUTPUT_DATE_FORMAT.getValue());
		Calendar cal = Calendar.getInstance();
		Date currentDate = new Date();
		try {
			cal.setTime(currentDate);
			cal.add(Calendar.DATE, -1);

			return inputDateFormat.format(cal.getTime()) + " 00:00:00";
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String todayDate() {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(CommonConstant.OUTPUT_DATE_FORMAT.getValue());
		Calendar cal = Calendar.getInstance();
		Date currentDate = new Date();
		try {
			cal.setTime(currentDate);
			// cal.add(Calendar.DATE, -1);

			return inputDateFormat.format(cal.getTime()) + " 00:00:00";
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String formatFromDate(String dateFrom) {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(CommonConstant.INPUT_DATE_FORMAT.getValue()); // get
		try {
			Date fromDate = (Date) (inputDateFormat.parse(dateFrom));
			// dateFrom = outputDateFormat.format(fromDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(fromDate);
			dateFrom = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);

			return dateFrom;
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String formatDateforChargeback(String date) {

		try {
			String[] parts = date.split("-");
			date = parts[2] + "-" + parts[1] + "-" + parts[0];
			return date;
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String formatToDate(String dateTo) {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(CommonConstant.INPUT_DATE_FORMAT.getValue()); // get
		Calendar cal = Calendar.getInstance();
		try {
			Date toDate = inputDateFormat.parse(dateTo);
			cal.setTime(toDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			dateTo = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
			return dateTo;
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static Date formatStringToDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt = formatter.parse(date);
			return dt;
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static long diffDate(String date1, String date2) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = formatter.parse(formatFromDate(date1));
			Date dt2 = formatter.parse(formatFromDate(date2));
			long diff = dt2.getTime() - dt1.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			return diffDays;
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return 0;
		}
	}

	public static String formatDateForDb(Date date) {
		SimpleDateFormat outputDateFormat = new SimpleDateFormat(CommonConstant.OUTPUT_DATE_FORMAT_DB.getValue());
		return outputDateFormat.format(date);
	}

	public static String formatDate(Date date) {
		SimpleDateFormat outputDateFormat = new SimpleDateFormat(CommonConstant.OUTPUT_DATE_FORMAT.getValue());
		return outputDateFormat.format(date);
	}

	public static String defaultCurrentDateTime() {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(CommonConstant.DATE_TIME_FORMAT.getValue());
		Date currentDate = new Date();
		try {
			return inputDateFormat.format(currentDate);
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}
	
	public static String defaultCurrentDateTime2() {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(CommonConstant.OUTPUT_DATE_FORMAT.getValue());
		Date currentDate = new Date();
		try {
			return inputDateFormat.format(currentDate);
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static Date currentDateTime() {
		try {
			Calendar cal = Calendar.getInstance();
			Date dt = cal.getTime();
			return dt;
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String currentTime() {
		// DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			Calendar cal = Calendar.getInstance();
			Date dt = cal.getTime();
			String date1 = formatDateForDb(dt);
			String[] splitDate = date1.split(" ");
			return splitDate[1].toString();
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String currentDateFormat() {
		// DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			Calendar cal = Calendar.getInstance();
			Date dt = cal.getTime();
			String date1 = dt.toString();
			String[] splitDate = date1.split(" ");
			return splitDate[0].toString();
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static Date formatStringToDateTime(String date) {
		DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			Date dt = inputDateFormat.parse(date);
			return dt;
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static Date convertStringToDateTime(String date) {
		DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt = inputDateFormat.parse(date);
			return dt;
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String formatSaleDateTime(String date) {
		try {
			StringBuilder sbFormatDate = new StringBuilder();
			String[] parts = date.split(" ");
			String[] dateParts = parts[0].split("-");
			sbFormatDate.append(dateParts[0]);
			sbFormatDate.append(dateParts[1]);
			sbFormatDate.append(dateParts[2]);
			if (parts.length == 2) {
				String[] timeParts = parts[1].split(":");
				sbFormatDate.append(timeParts[0]);
				sbFormatDate.append(timeParts[1]);
				sbFormatDate.append(timeParts[2]);
			}
			return sbFormatDate.toString();
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	// Format dd-MM-yyyy to YYYY-MM-dd
	public static String formatDateTime(String date) {
		try {
			StringBuilder sbFormatDate = new StringBuilder();
			String[] parts = date.split(" ");
			String[] dateParts = parts[0].split("-");
			sbFormatDate.append(dateParts[2]);
			sbFormatDate.append("-");
			sbFormatDate.append(dateParts[1]);
			sbFormatDate.append("-");
			sbFormatDate.append(dateParts[0]);
			sbFormatDate.append(" ");
			sbFormatDate.append(parts[1]);
			/*
			 * if(parts.length == 2) { String[] timeParts = parts[1].split(":");
			 * sbFormatDate.append(timeParts[0]); sbFormatDate.append(timeParts[1]);
			 * sbFormatDate.append(timeParts[2]); }
			 */
			return sbFormatDate.toString();
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String formatDate(String date) {
		try {
			StringBuilder sbFormatDate = new StringBuilder();
			String[] parts = date.split(" ");
			String[] dateParts = parts[0].split("-");
			sbFormatDate.append(dateParts[2]);
			sbFormatDate.append("-");
			sbFormatDate.append(dateParts[1]);
			sbFormatDate.append("-");
			sbFormatDate.append(dateParts[0]);
			sbFormatDate.append(" ");
			return sbFormatDate.toString();
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String formatDateTimeToDate(String date) {
		try {
			StringBuilder sbFormatDate = new StringBuilder();
			String[] parts = date.split(" ");
			String[] dateParts = parts[0].split("-");
			sbFormatDate.append(dateParts[2]);
			sbFormatDate.append("-");
			sbFormatDate.append(dateParts[1]);
			sbFormatDate.append("-");
			sbFormatDate.append(dateParts[0]);
			return sbFormatDate.toString();
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String dateIndexToDate(String date) {
		String dbFormatDateTime = null;
		StringBuilder stringBuilder = new StringBuilder();
		String datec = date.substring(6, 8);
		stringBuilder.append(datec);
		stringBuilder.append("-");
		String month = date.substring(4, 6);
		stringBuilder.append(month);
		stringBuilder.append("-");
		String year = date.substring(0, 4);
		stringBuilder.append(year);

		dbFormatDateTime = stringBuilder.toString();
		System.out.println(dbFormatDateTime);
		return dbFormatDateTime;
	}

	public static String formatSaleDate(String date) {
		try {
			StringBuilder sbFormatDate = new StringBuilder();
			String[] parts = date.split(" ");
			String[] dateParts = parts[0].split("-");
			sbFormatDate.append(dateParts[0]);
			sbFormatDate.append(dateParts[1]);
			sbFormatDate.append(dateParts[2]);
			return sbFormatDate.toString();
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String formatSettleDate(String date) {
		try {
			StringBuilder sbFormatDate = new StringBuilder();
			String[] parts = date.split(" ");
			String[] dateParts = parts[0].split("-");

			sbFormatDate.append(dateParts[2]);
			sbFormatDate.append(dateParts[1]);
			sbFormatDate.append(dateParts[0]);
			return sbFormatDate.toString();
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String formatCaptureDate(String date) {
		try {
			StringBuilder sbFormatDate = new StringBuilder();
			String[] parts = date.split(" ");
			String[] dateParts = parts[0].split("-");

			sbFormatDate.append(dateParts[2]);
			sbFormatDate.append(dateParts[1]);
			sbFormatDate.append(dateParts[0]);
			return sbFormatDate.toString();
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String toDateTimeformatCreater(String date) {
		String dbFormatDateTime = null;
		StringBuilder stringBuilder = new StringBuilder();
		String year = date.substring(6, 10);
		stringBuilder.append(year);
		stringBuilder.append("-");
		String month = date.substring(3, 5);
		stringBuilder.append(month);
		stringBuilder.append("-");
		String datec = date.substring(0, 2);
		stringBuilder.append(datec);
		stringBuilder.append(CommonConstant.TO_TIME_FORMAT.getValue());

		dbFormatDateTime = stringBuilder.toString();

		return dbFormatDateTime;

	}

	public static String toDateTimeformatforUpload(String date) {
		String dbFormatDateTime = null;
		StringBuilder stringBuilder = new StringBuilder();
		String month = date.substring(3, 5);
		stringBuilder.append(month);
		stringBuilder.append("/");
		String datec = date.substring(0, 2);
		stringBuilder.append(datec);
		stringBuilder.append("/");
		String year = date.substring(6, 10);
		stringBuilder.append(year);

		dbFormatDateTime = stringBuilder.toString();

		return dbFormatDateTime;

	}

	public static String toDateTimeformatCreaterWithHhMmSs(String date) {

		String hourMinsSec = date.substring(11, date.length()).trim();
		String dbFormatDateTime = null;
		StringBuilder stringBuilder = new StringBuilder();
		String year = date.substring(6, 10);
		stringBuilder.append(year);
		stringBuilder.append("-");
		String month = date.substring(3, 5);
		stringBuilder.append(month);
		stringBuilder.append("-");
		String datec = date.substring(0, 2);
		stringBuilder.append(datec);

		dbFormatDateTime = stringBuilder.toString();

		return dbFormatDateTime.concat(" ").concat(hourMinsSec);

	}

	public static String formDateTimeformatCreater(String date) {
		String dbFormatDateTime = null;
		StringBuilder stringBuilder = new StringBuilder();
		String year = date.substring(6, 10);
		stringBuilder.append(year);
		stringBuilder.append("-");
		String month = date.substring(3, 5);
		stringBuilder.append(month);
		stringBuilder.append("-");
		String datec = date.substring(0, 2);
		stringBuilder.append(datec);
		stringBuilder.append(CommonConstant.FROM_TIME_FORMAT.getValue());

		dbFormatDateTime = stringBuilder.toString();

		return dbFormatDateTime;

	}

	public static String formDateTimeformatUpload(String date) {
		String dbFormatDateTime = null;
		StringBuilder stringBuilder = new StringBuilder();
		String month = date.substring(3, 5);
		stringBuilder.append(month);
		stringBuilder.append("/");
		String datec = date.substring(0, 2);
		stringBuilder.append(datec);
		stringBuilder.append("/");
		String year = date.substring(6, 10);
		stringBuilder.append(year);

		dbFormatDateTime = stringBuilder.toString();

		return dbFormatDateTime;

	}

	public static String formatDateReco(String recoDate) {
		String dbFormatDateTime = null;
		StringBuilder stringBuilder = new StringBuilder();
		String year = recoDate.substring(0, 4);
		stringBuilder.append(year);
		// stringBuilder.append("-");
		String month = recoDate.substring(5, 7);
		stringBuilder.append(month);
		// stringBuilder.append("-");
		String datec = recoDate.substring(8, 10);
		stringBuilder.append(datec);
		// stringBuilder.append(CrmFieldConstants.FROM_TIME_FORMAT.getValue());

		dbFormatDateTime = stringBuilder.toString();

		return dbFormatDateTime;

	}

	public static String formatDateTransaction(String recoDate) {
		String dbFormatDateTime = null;
		StringBuilder stringBuilder = new StringBuilder();
		String year = recoDate.substring(0, 4);
		stringBuilder.append(year);
		// stringBuilder.append("-");
		String month = recoDate.substring(5, 7);
		stringBuilder.append(month);
		// stringBuilder.append("-");
		String datec = recoDate.substring(8, 10);
		stringBuilder.append(datec);
		// stringBuilder.append(CrmFieldConstants.FROM_TIME_FORMAT.getValue());

		dbFormatDateTime = stringBuilder.toString();

		return dbFormatDateTime;

	}

	public static String formatDateSettlement(String settleDate) {
		String dbFormatDateTime = null;
		StringBuilder stringBuilder = new StringBuilder();
		String year = settleDate.substring(0, 4);
		stringBuilder.append(year);
		String month = settleDate.substring(5, 7);
		stringBuilder.append(month);
		String datec = settleDate.substring(8, 10);
		stringBuilder.append(datec);
		dbFormatDateTime = stringBuilder.toString();
		return dbFormatDateTime;

	}

	public static LocalDate formatStringToLocalDate(String date) {
		LocalDate localDate = LocalDate.parse(date, dateTimeFormat);
		return localDate;
	}

	public static String formatSettledDate(String date) {
		try {
			StringBuilder sbFormatDate = new StringBuilder();
			String[] parts = date.split(" ");
			String[] dateParts = parts[0].split("-");
			sbFormatDate.append(dateParts[0]);
			sbFormatDate.append("-");
			sbFormatDate.append(dateParts[1]);
			sbFormatDate.append("-");
			sbFormatDate.append(dateParts[2]);
			sbFormatDate.append(" ");
			return sbFormatDate.toString();
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	// TODO review return type -->because our format can't be parsed in
	// LocalDateTime
	public static String subtractHours(LocalDateTime currentStamp, long hours) {
		LocalDateTime finalStamp = currentStamp.minusHours(hours);
		return finalStamp.format(dateTimeFormat);
	}

	public static String subtractDays(LocalDateTime currentStamp, long days) {
		LocalDateTime finalStamp = currentStamp.minusDays(days);
		return finalStamp.format(dateTimeFormat);
	}

	public static String subtractWeeks(LocalDateTime currentStamp, long weeks) {
		LocalDateTime finalStamp = currentStamp.minusWeeks(weeks);
		return finalStamp.format(dateTimeFormat);
	}

	public static String subtractMonths(LocalDateTime currentStamp, long months) {
		LocalDateTime finalStamp = currentStamp.minusMonths(months);
		return finalStamp.format(dateTimeFormat);
	}

	public static LocalDateTime now() {
		return LocalDateTime.now();
	}

	public static String printDayWeek(String settledDate) {
		String payoutDate = "", tempPayoutDate = "";
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(CommonConstant.OUTPUT_DATE_FORMAT.getValue());
		Calendar cal = Calendar.getInstance();
		Date settledDate1;
		try {
			settledDate1 = (Date) (inputDateFormat.parse(settledDate));
			cal.setTime(settledDate1);
			cal.add(Calendar.DATE, 1);
			payoutDate = inputDateFormat.format(cal.getTime());
			// Date payoutDt = (Date)(inputDateFormat.parse(tempPayoutDate));

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(cal.getTime());

			if (calendar.get(Calendar.DAY_OF_WEEK) == 1 || (calendar.get(Calendar.DAY_OF_WEEK) == 7
					&& (calendar.get(Calendar.WEEK_OF_MONTH) == 2 || calendar.get(Calendar.WEEK_OF_MONTH) == 4))) {
				tempPayoutDate = inputDateFormat.format(inputDateFormat.parse(payoutDate));
				return printDayWeek(tempPayoutDate);
			}

		} catch (Exception exception) {
			logger.error("Exception", exception);
		}

		return payoutDate;

	}

	public static String printMprDayWeek(String settledDate) {
		String payoutDate = "", tempPayoutDate = "";
		SimpleDateFormat inputDateFormat = new SimpleDateFormat(CommonConstant.OUTPUT_DATE_FORMAT.getValue());
		Calendar cal = Calendar.getInstance();
		Date settledDate1;
		try {
			settledDate1 = (Date) (inputDateFormat.parse(settledDate));
			cal.setTime(settledDate1);
			cal.add(Calendar.DATE, 1);
			payoutDate = inputDateFormat.format(cal.getTime());
			// Date payoutDt = (Date)(inputDateFormat.parse(tempPayoutDate));

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(cal.getTime());

		} catch (Exception exception) {
			logger.error("Exception", exception);
		}

		return payoutDate;

	}

	public static String getMonthYear(String date) {

		String[] splitDate = date.split("-");

		String monthYear = getMonthName(Integer.parseInt(splitDate[1])) + "-" + splitDate[0];

		return monthYear;
	}

	public static String getMonthName(int month) {

		String monthName = null;
		switch (month) {
		case 1:
			monthName = "January";
			break;
		case 2:
			monthName = "February";
			break;
		case 3:
			monthName = "March";
			break;
		case 4:
			monthName = "April";
			break;
		case 5:
			monthName = "May";
			break;
		case 6:
			monthName = "June";
			break;
		case 7:
			monthName = "July";
			break;
		case 8:
			monthName = "August";
			break;
		case 9:
			monthName = "September";
			break;
		case 10:
			monthName = "October";
			break;
		case 11:
			monthName = "November";
			break;
		case 12:
			monthName = "December";
			break;
		}

		return monthName;
	}

	public static String getDayMonthYear(String date) {

		LocalDate currentDate = LocalDate.parse(date);

		// Get day from date
		// int day = currentDate.getDayOfMonth();

		String day = date.substring(8);

		// Get month from date
		Month month = currentDate.getMonth();

		// Get year from date
		int year = currentDate.getYear();

		// Print the day, month, and year

		String dateWitMon = day + "-" + month + "-" + year;
		// String date1 =day+"-"+month+"-"+year;
		String[] dat = dateWitMon.split("-");
		String reqMon = dat[1].substring(0, 3).toLowerCase();
		String reqdMon = reqMon.substring(0, 1).toUpperCase() + reqMon.substring(1, 3);

		String reqDate = day + "-" + reqdMon + "-" + year;

		// System.out.println(date);
		return reqDate;
	}

	public static String formatStringToDateMMM(String date) {
		String dtStr = null;
		DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			Date dt = (Date) (inputDateFormat.parse(date));
			dtStr = outputDateFormat.format(dt);
			return dtStr;
		} catch (Exception exception) {
			logger.error("Exception", exception);
			return null;
		}
	}

	public static String addOneSecondInDate(String date) {
		String capturedDate = null;
		DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt = (Date) (inputDateFormat.parse(date));
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			cal.add(Calendar.SECOND, 1);
			capturedDate = outputDateFormat.format(cal.getTime());
		} catch (ParseException e) {
			logger.error("Exception", e);
		}
		return capturedDate;
	}

	public static String getLastDayOfMonth(String yyyyMMdd) {
		try {
			Calendar calInstance = Calendar.getInstance();
			String formatDate = DateCreater.formatDate(calInstance.getTime()); // YYYY-MM-dd
			String lastDate = calInstance.getActualMaximum(Calendar.DATE) > 9
					? String.valueOf(calInstance.getActualMaximum(Calendar.DATE))
					: "0" + String.valueOf(calInstance.getActualMaximum(Calendar.DATE));
			String toDate = formatDate.split("-")[0] + "-" + formatDate.split("-")[1] + "-" + String.valueOf(lastDate); // YYYY-MM-dd
			return toDate;
		} catch (Exception e) {
			return null;
		}
	}

}
