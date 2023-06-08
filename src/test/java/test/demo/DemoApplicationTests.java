package test.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileReader;

// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.json.simple.parser.JSONParser;
// import org.json.simple.parser.ParseException;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {

	Project1 gitLabProject = new Project1();

	gitLabProject.setName("Test");
	gitLabProject.setId(10);
	gitLabProject.setUrl("test url");

	System.out.printf(gitLabProject.getName());
	System.out.printf("%s",  gitLabProject.getId());
	System.out.printf(gitLabProject.getUrl());


	}

}

class Project1 {

    String name;
    int id;
    String url;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
        public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}