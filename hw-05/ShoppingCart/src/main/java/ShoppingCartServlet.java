import com.example.hw5part_2.ShoppingCart;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ShoppingCartServlet", value = "/ShoppingCartServlet")
public class ShoppingCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCart sc = (ShoppingCart) request.getSession().getAttribute("ShoppingCart");

        if(request.getParameter("productId") != null){
            sc.addProductToCart(request.getParameter("productId"), 1);
        }else{
            ShoppingCart newSC = new ShoppingCart();
            for(String id : sc.idQuantityProductMap.keySet()){
                String enteredQuantity = request.getParameter(id);
                int quantity = Integer.parseInt(enteredQuantity);
                if(quantity != 0) newSC.addProductToCart(id, quantity);
            }
            sc = newSC;
        }
        request.getSession().setAttribute("ShoppingCart", sc);

        request.getRequestDispatcher("shoppingCart.jsp").forward(request, response);
    }
}
