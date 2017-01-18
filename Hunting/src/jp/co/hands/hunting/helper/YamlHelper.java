package jp.co.hands.hunting.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.yaml.snakeyaml.Yaml;

public class YamlHelper <T> {

	private Yaml yaml = new Yaml();
		
	public T getYamlInfo(Class<T> targetClass, String filePath) throws IOException {
		
		T returnInstance = yaml.loadAs(Files.newInputStream(Paths.get(filePath)), targetClass);
		return returnInstance;
	}
	
}
