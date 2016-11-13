import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Questions")
public class ShowQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBAbs db = new DBAbs();

	public ShowQuestions() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Questions</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Questions</h1>");
		
		ArrayList<String> qs = new ArrayList<String>();
		try {
			qs = db.getAllQs();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int count = 0;
		for (String q : qs) {
			count++;
			out.print("<p>" + count + ". <a href=\"Answers?id=" + Integer.toString(count) + "\">" + qs.get(count-1) + "</a></p>");	        	
		}
		out.println("<form method=\"POST\" action=\"Questions\">");
		out.println("<p><input type=\"text\" name=\"theQuestion\" size=\"50\"> <input type=\"submit\" value=\"Add Question\"></p>");
		out.println("</form>");
		out.println("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String theQuestion = request.getParameter("theQuestion");

		db.insertQ(theQuestion);

		response.sendRedirect("Questions");
	}

}