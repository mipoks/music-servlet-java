package design.kfu.servlet.functionality.publicity;

import design.kfu.entity.Song;
import design.kfu.service.MusicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/actual")
public class PopularServlet extends HttpServlet {

    private MusicService searchService;

    @Override
    public void init() throws ServletException {
        searchService = new MusicService();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "В трендах");
        Collection<Song> songs = null;
        try {
            songs = searchService.getPopularSongs(req);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("songemptytext", "Хьюстон, у нас проблемы, так как ничего не нашлось");
        }
        req.setAttribute("songs", songs);
        req.getRequestDispatcher("/resources/templates/audio-page.jsp").forward(req, resp);
    }

}