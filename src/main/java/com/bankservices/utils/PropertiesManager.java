package com.bankservices.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service("propertiesManager")
public class PropertiesManager {


	private static Logger logger = LoggerFactory.getLogger(PropertiesManager.class.getName());
	private static final String fileLocation = System.getenv("PG_PROS");
	private static final String ymlFileLocation = System.getenv("PG_PROS");
	private static final String currencyFile = "currency.properties";
	private static final String currencyAlphabaticToNumericFile = "alphabatic-to-numeric.properties";
	private static final String emailPropertiesFile = "emailer.properties";
	private static final String currencyNameFile = "alphabatic-currencycode.properties";
	private static final String SmsPropertiesFile = "Sms.properties";
	public static Map<String, String> propertiesMap = new HashMap<>();
	private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
	
	public static Map<String, String> saltPropertiesMap = new HashMap<>();

	public PropertiesManager() {

		if (propertiesMap.size() < 1) {

			System.out.println("THIS IS INSIDE CONSTRUCTOR");
			Resource resource = new FileSystemResource(ymlFileLocation + "application.yml");
			List<PropertySource<?>> propertySource = loadYaml(resource);

			Map<String, Object> propertyObjMap = (Map<String, Object>) propertySource.get(0).getSource();
			for (Entry<String, Object> propertyObjMapEntry : propertyObjMap.entrySet()) {
				PropertiesManager.propertiesMap.put(propertyObjMapEntry.getKey(),
						propertyObjMapEntry.getValue().toString());
			}

			// FileModifiedWatcher.init(ymlFileLocation + "application.yml", 600);
		}

		// Initialize poller to update properties file when application.yml is updated

	}
	
	private List<PropertySource<?>> loadYaml(Resource path) {
		if (!path.exists()) {
			throw new IllegalArgumentException("Resource " + path + " does not exist");
		}
		try {
			List<PropertySource<?>> load = this.loader.load("custom-resource", path);
			return load;
		} catch (IOException ex) {
			throw new IllegalStateException("Failed to load yaml configuration from " + path, ex);
		}
	}

	public String getSystemProperty(String key) {
		return propertiesMap.get(key);
	}

	public String getEmailProperty(String key) {
		return getProperty(key, emailPropertiesFile);
	}

	public String getResetPasswordProperty(String key) {
		return getProperty(key, emailPropertiesFile);
	}

	public String getAlphabaticCurrencyCode(String numericCurrencyCode) {
		return getProperty(numericCurrencyCode, currencyNameFile);
	}

	public String getNumericCurrencyCode(String alphabeticCode) {
		return getProperty(alphabeticCode, currencyAlphabaticToNumericFile);
	}

	protected boolean setProperty(String key, String value, String fileName) {

		boolean result = true;
		Properties prop = new Properties();
		OutputStream output = null;

		try {

			FileInputStream input = new FileInputStream(fileLocation + fileName);
			prop.load(input);
			input.close();

			logger.info("salt Location = "+fileLocation + fileName);
			
			output = new FileOutputStream(fileLocation + fileName);
			prop.setProperty(key, value);

			prop.store(output, null);

		} catch (IOException ioException) {
			logger.error("Unable to update properties file = " + fileName + ", Details = " + ioException.getMessage(),
					ioException);
			result = false;
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException ioException) {
					logger.error("Unable to update properties file = " + fileName + ", Details = "
							+ ioException.getMessage(), ioException);
					result = false;
				}
			}
		}

		return result;
	}

	public Map<String, String> getAllProperties(String fileName) {
		Map<String, String> responseMap = new HashMap<String, String>();
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(fileLocation + fileName);

			// load a properties file prop.load(input);

			for (Object key : prop.keySet()) {
				responseMap.put(key.toString(), prop.getProperty(key.toString()));
			}

		} catch (IOException ioException) {
			logger.error("Unable to update properties file = " + fileName + ", Details = " + ioException.getMessage(),
					ioException);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException ioException) {
					logger.error("Unable to update properties file = " + fileName + ", Details = "
							+ ioException.getMessage(), ioException);
				}
			}
		}

		return responseMap;
	}// getAllProperties()

	private String getProperty(String key, String fileName) {

		Properties prop = new Properties();
		InputStream input = null;
		String value = null;

		try {
			logger.info("fileValues" + fileLocation + "\t" + fileName + "\t" + key);
			input = new FileInputStream(fileLocation + fileName);
			prop.load(input);
			value = prop.getProperty(key);

		} catch (IOException ioException) {
			logger.error("Unable to update properties file = " + fileName + ", Details = " + ioException.getMessage(),
					ioException);
		} catch (NullPointerException npe) {
			logger.error("property file error " + npe);
		} finally {

			if (input != null) {
				try {
					input.close();
				} catch (IOException ioException) {
					logger.error("Unable to update properties file = " + fileName + ", Details = "
							+ ioException.getMessage(), ioException);
				}
			}
		}
		propertiesMap.put(key, value);
		return value;
	}
	/*
	 * public static String getSystempropertiesfile() { return systemPropertiesFile;
	 * }
	 */

	public static String getCurrencyfile() {
		return currencyFile;
	}

	public static String getEmailpropertiesfile() {
		return emailPropertiesFile;
	}
	
	public boolean setValueInPropertiesFile(String key, String value, String fileName) {
		logger.info("key = "+key);
		logger.info("value = "+value);
		logger.info("fileName = "+fileName);
		return setProperty(key, value, fileName);
	}
	
	public String getValueInPropertiesFile(String key, String fileName) {
			return getProperty(key, fileName);
	}

}
