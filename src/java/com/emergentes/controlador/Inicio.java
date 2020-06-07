package com.emergentes.controlador;

import com.emergentes.dao.AvisoDAO;
import com.emergentes.dao.AvisoDAOimpl;
import com.emergentes.modelo.Aviso;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Inicio", urlPatterns = {"/Inicio"})
public class Inicio extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AvisoDAO dao = new AvisoDAOimpl();
            int id;
            Aviso avi = new Aviso();
            
            String action = (request.getParameter("action") != null) ? request.getParameter("action") : "view";
            
            switch(action){
                case "add":
                    request.setAttribute("aviso", avi);
                    request.getRequestDispatcher("frmaviso.jsp").forward(request, response);
                    break;
                case "edit":
                    id = Integer.parseInt(request.getParameter("id"));
                    avi = dao.getById(id);
                    //System.out.println(avi);
                    request.setAttribute("aviso", avi);
                    request.getRequestDispatcher("frmaviso.jsp").forward(request, response);
                    break;
                /*case "delete":
                    id = Integer.parseInt(request.getParameter("id"));
                    dao.delete(id);
                    request.getRequestDispatcher("Inicio").forward(request, response);
                   // response.sendRedirect(request.getContextPath() + "/Inicio");*/
                    
                    case "delete":
                    id = Integer.parseInt(request.getParameter("id"));
                    dao.delete(id);
                    response.sendRedirect(request.getContextPath()+"/Inicio");
                    break;
                    
                default:
                    List<Aviso> lista = dao.getAll();
                    request.setAttribute("avisos", lista);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                
                    break;
            }
        } catch (Exception ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         AvisoDAO dao = new AvisoDAOimpl();
        int id = Integer.parseInt(request.getParameter("id"));
        String descripcion = request.getParameter("descripcion");
        int stock = Integer.parseInt(request.getParameter("stock"));

        Aviso avi = new Aviso();

        avi.setId(id);
        avi.setDescripcion(descripcion);
        avi.setStock(stock);

        if (id == 0) {
            try {
                //AvisoDAO dao = new AvisoDAOimpl();
                dao.insert(avi);
                response.sendRedirect("Inicio");

            } catch (Exception ex) {
                System.out.println("error" + ex.getMessage());
            }
        } else {
            try {
               // AvisoDAO dao = new AvisoDAOimpl();
               dao.update(avi);
               // dao.updte(avi);
                response.sendRedirect("Inicio");
            } catch (Exception ex) {
                System.out.println("error" + ex.getMessage());
            }
        }
    }

}
