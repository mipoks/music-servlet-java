package design.kfu.filter;

import design.kfu.entity.Person;
import design.kfu.entity.dto.PersonForm;
import design.kfu.service.SecurityService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter extends HttpFilter {

    protected final String[] protectedPaths = {"/profile", "/me", "/logout"};
    private PersonForm personForm;

    @Override
    public void init() throws ServletException {
        personForm = new PersonForm();
        super.init();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        boolean prot = false;
        for(String path : protectedPaths){
            if(path.equals(req.getRequestURI().substring(req.getContextPath().length()))){
                prot = true;
                break;
            }
        }
        Person temp = SecurityService.getUser(req);

        if(prot && temp == null){
            res.sendRedirect(req.getContextPath() + "/login");
        }
        else{
            if(temp != null){
                personForm.setEmail(temp.getEmail());
                personForm.setName(temp.getName());
                personForm.setSongCount(temp.getSongCount());
                req.setAttribute("user", personForm);
            }
            chain.doFilter(req, res);
        }
    }

}