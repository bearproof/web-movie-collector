package ro.isdc.model;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("htmlNodePathMapper")
public class HtmlNodePathMapper {

	HashMap<String,String> nodePathMap;
	
	 public HashMap<String, String> getNodePathMap() {
		return nodePathMap;
	}

	public void setNodePathMap(HashMap<String, String> nodePathMap) {
		this.nodePathMap = nodePathMap;
	}
}
