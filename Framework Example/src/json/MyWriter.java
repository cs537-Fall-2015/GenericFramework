package json;

import java.io.FileWriter;
import java.io.IOException;

import main.REMS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyWriter {
	
	REMS remsObject = new REMS(0, 5, 10, 0, 30, 70, 150, 300, 200,
			100, 150, 300, 840, 40, 275, 203, 323, "RUNNING",250);

	public void writeJSONData() {
		String myFilePath = "/Users/Chavda/Desktop/REMSDATA.json";
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		// Instantiate the writer since we're writing to a JSON file.
		FileWriter writer = null;
		try {
			writer = new FileWriter(myFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Object is converted to a JSON String
		String jsonString = gson.toJson(remsObject);
		
		// Write the file
		try {
			writer.write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Close the Writer
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
