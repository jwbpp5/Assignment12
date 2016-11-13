import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Answers")
public class ShowAnswers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int questionID;
	private DBAbs db;
	
    public ShowAnswers() {
        super();
        if (db == null) {
        	db = new DBAbs();
        }
    }

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		questionID = Integer.parseInt(request.getParameter("id"));
		System.out.println(questionID);

		out.println("<html>");
		out.println("<body>");
		
		//Answers answers = answerGateway.getAnswers(Integer.parseInt(questionID)-1);
		ArrayList<String> answers = new ArrayList<String>();
		
		try {
			answers = db.getAllAns(questionID);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < answers.size(); ++i) {
			out.println("<p> " + (i + 1) + ". " + answers.get(i) + " </p>");
		}

		out.println("<form method=\"POST\" action=\"Answers\">");
        out.println("<p><input type=\"text\" name=\"theAnswer\" size=\"50\"> <input type=\"submit\" value=\"Add Answer\"></p>");
        out.println("</form>");
        out.println("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String theAnswer = request.getParameter("theAnswer");
        db.insertAns(questionID, theAnswer);
        out.println("<html>");
		out.println("<body>");
		
		//Answers answers = answerGateway.getAnswers(Integer.parseInt(questionID)-1);
		ArrayList<String> answers = new ArrayList<String>();
		
		try {
			answers = db.getAllAns(questionID);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < answers.size(); ++i) {
			out.println("<p> " + (i + 1) + ". " + answers.get(i) + " </p>");
		}

		out.println("<form method=\"POST\" action=\"Answers\">");
        out.println("<p><input type=\"text\" name=\"theAnswer\" size=\"50\"> <input type=\"submit\" value=\"Add Answer\"></p>");
        out.println("</form>");
        out.println("</body></html>");
		
	}
}
