

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class findPpl
 */
@WebServlet("/findPpl")
public class findPpl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public findPpl() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static Connection connectDB() throws SQLException, ClassNotFoundException {
    	Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:testuser/password@localhost";
		Properties props = new Properties();
		props.setProperty("user", "testdb");
		props.setProperty("password", "password");
		Connection conn = DriverManager.getConnection(url, props);
		return conn;
	}
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("$$$$$$$$$$" + request.getParameter("name"));
		Connection conn;
		try {
			//String get_name = request.getParameter("text_name").trim();
			conn = connectDB();
			String sql = "select customers.*, states.state, companies.company ,city." + "\"" + "city"+ "\"" + " from customers, states, companies, city where "  
						+ "city.cityID = customers.cityID and "
						+ "States.stateID = customers.stateID and "
						+ "companies.comapnyID = customers.companyID and " 
						+ "customers.fullname =" + "\'" + request.getParameter("name") + "\'" 
						;
			
			System.out.println(sql);
	        //creating PreparedStatement object to execute query
	        PreparedStatement preStatement = conn.prepareStatement(sql);
	        ResultSet result = preStatement.executeQuery();
	        String line= "";
	        line += "<style>"
					+ ".bs-example{" + "margin-top:20%" + "margin-left:20%"
					+  "margin-bottom:20%" + "}"

					+ "table { " + " table-layout: fixed;"
					+ " word-wrap: break-word;" + "}" + "</style>";
	        
	        
	        line += "<table class=" 
	        		+ "\"table table-striped\"" 
	        		+ "style=width:100%>";
	        
	        line += 
	     			"<tr>" 
					+"<th>" + "title" + "</th><br>"
	     			+"<th>" + "fullname" + "</th><br>"
	     			+"<th>" + "Street Address" + "</th><br>"
	     			+"<th>" + "zipCode" + "</th><br>"
	     			+"<th>" + "email" + "</th><br>"
	     			+"<th>" + "position" + "</th><br>"
	     			+"<th>" + "company" + "</th><br>"
	     			+"<th>" + "city" + "</th><br>"
	     			+ "</tr>"
	     			;
				
		        while(result.next()){
		        	line += "<tr>" 
		        			+ "<td>" +result.getString("TITLE") + "</td>"
		        			+"<td>" + result.getString("FULLNAME") + "</td>"
		        			+"<td>" + result.getString("STREETADDRESS") + "</a></td>"
		        			+"<td>" + result.getString("ZIPCODE")+ "</td>"
		        			+"<td>" + result.getString("emailaddress")+ "</td>"
		        			+"<td>" + result.getString("position")+ "</td>"
		        			+"<td>" + result.getString("company")+ "</td>"
		        			+"<td>" + result.getString("City")+ "</td>"
		        			+ "</tr>"
		        	    ;

		        }
	        
		    line += "</table>";
	        conn.close();
	        
	        request.setAttribute("message", line);
	        getServletContext().getRequestDispatcher("/output.jsp").forward(request, response);	
	       
	        
		}catch (Exception e){
			e.printStackTrace();
		}
	
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn;
		try {
			//String get_name = request.getParameter("text_name").trim();
			conn = connectDB();
			String sql = "select fullname from customers where lastname LIKE" 
						+ "\'" + request.getParameter("text_name").trim() + "%\'" 
						;
	        //creating PreparedStatement object to execute query
	        PreparedStatement preStatement = conn.prepareStatement(sql);
	        ResultSet result = preStatement.executeQuery();
	        String str= "";
	        
	        while(result.next()){
	        	
	        	//str += result.getString("fullname") + "<br>";
	        	str += "<a href" + "=" + "\"findPpl?name=" + result.getString("fullname") + "\"" + ">"+ result.getString("fullname") + "</a><br>";
	        }
	        conn.close();
	        
	    
	        request.setAttribute("message", str);
	        getServletContext().getRequestDispatcher("/output.jsp").forward(request, response);	
	       
		}catch(Exception e){
			e.printStackTrace();
		}
		
	
	}

}
