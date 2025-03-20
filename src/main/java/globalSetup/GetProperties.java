package globalSetup;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GetProperties {
	
	private Properties prop;

	/**
	 * This method is used to load the properties from config.properties file
	 * @return it returns Properties prop object
	 */
	public Properties init_prop() {
		prop = new Properties();
		try {
			String projectPath = System.getProperty("user.dir");
			String propFilePath = projectPath + "/src/test/resources/Properties/data.properties";
			FileInputStream ip = new FileInputStream(propFilePath);
			prop.load(ip);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load properties file: " + e.getMessage());
		}
		return prop;
	}

	public String getProp(String string) throws IOException {
		String projectPath = System.getProperty("user.dir");
		String propFilePath = projectPath + "/src/test/resources/Properties/data.properties";
		
		try (FileReader datareader = new FileReader(propFilePath)) {
			Properties props = new Properties();
			props.load(datareader);
			return props.getProperty(string);
		}
	}
}