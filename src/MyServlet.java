
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String HTML_START = "<html><body>";
	public static final String HTML_END = "</body></html>";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get scanner instance
		PrintWriter out = response.getWriter();
		InputStream reader = getServletContext().getResourceAsStream("/WEB-INF/lib/normal.csv");

		Scanner scanner = new Scanner(reader);
		ArrayList<DataUnits> data = new ArrayList<DataUnits>();
		// Set the delimiter used in file
		scanner.useDelimiter(",");

		// Get all tokens and store them in some data structure
		// I am just printing them
		while (scanner.hasNext()) {
			String id, fname, lname, comp, email, ad1, ad2, zip, city, statel, states, phn;
			id = scanner.next();
			fname = scanner.next();
			lname = scanner.next();
			// works assuming there is a max of one comma per company name
			// needs different implementation for more general case
			Pattern p = Pattern.compile("\".*");
			if (scanner.hasNext(p)) {
				comp = scanner.next() + scanner.next();
			} else {
				comp = scanner.next();
			}
			email = scanner.next();
			ad1 = scanner.next();
			ad2 = scanner.next();
			zip = scanner.next();
			city = scanner.next();
			statel = scanner.next();
			states = scanner.next();
			scanner.useDelimiter("\n");
			phn = scanner.next();
			scanner.useDelimiter(",");

			// constructs the list through the loop
			DataUnits datum = new DataUnits(id, fname, lname, comp, email, ad1, ad2, zip, city, statel, states, phn);
			data.add(datum);
		}
		// define the list of duplicates to print to later
		ArrayList<DataUnits> duplicates = new ArrayList<DataUnits>();
		for (int j = 1; j < data.size(); j++) {
			if (data.get(j).isDuplicate(data, 0) == 1) {
				out.println(HTML_START + "<h2>" + data.get(j).PrintDataUnit() + "</h2>" + HTML_END);
			} else {
				duplicates.add(data.get(j));
			}
		}
		out.println(duplicates.size());
		out.println("POTENTIAL DUPLICATES");
		for (int z = 0; z < duplicates.size(); z++) {
			out.println(HTML_START + "<h3>" + duplicates.get(z).PrintDataUnit() + "</h3>" + HTML_END);
		}

		// Do not forget to close the scanner
		scanner.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
