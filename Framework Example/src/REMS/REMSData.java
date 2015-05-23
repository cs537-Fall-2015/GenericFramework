package REMS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class REMSData {
	REMSClass remsObject = new REMSClass(0, 5, 10, 0, 30, 70, 150, 300, 200,
			100, 150, 300, 840, 40, 275, 203, 323, "RUNNING");

	public String getRemsData() {

		// Gson is used to create a json object that is spaced nicely
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		// Object is converted to a JSON String
		String remsJsonString = gson.toJson(remsObject);

		return remsJsonString;
	}

	public boolean moveRover() {

		boolean moveRover = true;

		if (remsObject.getREMS_VERTICAL_WINDSPEED() >= remsObject
				.getREMS_VERTICAL_WINDSPEED_MAX()
				|| remsObject.getREMS_HORIZONTAL_WINDSPEED() >= remsObject
						.getREMS_HORIZONTAL_WINDSPEED_MAX()
				|| remsObject.getREMS_GROUNDTEMP() >= remsObject
						.getREMS_GROUNDTEMP_MAX()
				|| remsObject.getREMS_AIRTEMP() >= remsObject
						.getREMS_AIRTEMP_MAX()
				|| remsObject.getREMS_HUMIDITY() >= remsObject
						.getREMS_HUMIDITY_MAX()) {
			moveRover = false;
		}

		return moveRover;
	}

	public String postREMSdata() {

		String remsString = "";

		if (remsObject.REMS_STATUS.equals("RUNNING")) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			// Object is converted to a JSON String
			remsString = gson.toJson(remsObject);

		}

		return remsString;

	}

}
