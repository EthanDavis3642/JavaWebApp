import java.util.ArrayList;

public class DataUnits {
	String id;
	String fname;
	String lname;
	String company;
	String email;
	String address1;
	String address2;
	String zip;
	String city;
	String state_l;
	String state_s;
	String phone;

	DataUnits(String id, String fname, String lname, String company, String email, String address1, String address2,
			String zip, String city, String state_l, String state_s, String phone) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.company = company;
		this.email = email;
		this.address1 = address1;
		this.address2 = address2;
		this.zip = zip;
		this.city = city;
		this.state_l = state_l;
		this.state_s = state_s;
		this.phone = phone;

	}
//does not print id
	public String PrintDataUnit() {
		return fname + ", " + lname + ", " + company + ", " + email + ", " + address1 + ", " + address2 + ", " + zip
				+ ", " + city + ", " + state_l + ", " + state_s + ", " + phone + '\n';
	}
//not designed for massive lists, or around the size of the given file
	//checks if the names are similarly pronounced using metaphone 
	//also checks if the names are similar with a few typos, or if
	//phone and email are similar
	//checking levenshetien of less than 3, so allowing up to 2 typos
	//3 was tested against the data set
	public int isDuplicate(ArrayList<DataUnits> d, int counter) {
		Metaphone m = new Metaphone();
		int errorM = 3;
		for (int i = 0; i < d.size(); i++) {
			if ((Levenshtein.distance(this.fname, d.get(i).fname) < errorM)
					&& (Levenshtein.distance(this.lname, d.get(i).lname) < errorM)) {
				counter++;
			} else if ((Levenshtein.distance(this.email, d.get(i).email) < errorM)
					&& (Levenshtein.distance(this.phone, d.get(i).phone) < errorM)) {
				counter++;
			} else if (m.isMetaphoneEqual(this.fname, d.get(i).fname) && (m.isMetaphoneEqual(this.lname, d.get(i).lname))) {
				counter++;
			} 

		}
		//will return number of duplicates + 1, 1 being the name checked against itself
		return counter;
	}

}