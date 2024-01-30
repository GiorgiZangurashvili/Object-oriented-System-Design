import com.example.HW5_part_1.AccountManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateAccountServlet", value = "/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountManager accountManager = (AccountManager) getServletContext().getAttribute("accountManager");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(accountManager.accountExists(username)){
            request.getRequestDispatcher("nameAlreadyInUse.jsp").forward(request,response);
        }else{
            accountManager.createNewAccount(username, password);
            request.getRequestDispatcher("loginSuccess.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
}
