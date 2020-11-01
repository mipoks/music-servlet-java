package design.kfu.servlet.functionality.publicity;

import design.kfu.entity.Song;
import design.kfu.service.MusicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private MusicService searchService;

    @Override
    public void init() throws ServletException {
        searchService = new MusicService();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Поиск музыки");
        String search = req.getParameter("search");

        if (search == null) {
            String musicName = null;
            try {
                musicName = searchService.getRandomMusicName();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            req.setAttribute("somemusic", musicName);
            req.getRequestDispatcher("/resources/templates/search.jsp").forward(req, resp);
        } else {
            Collection<Song> songs = null;
            try {
                songs = searchService.search(search, req);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            req.setAttribute("songemptytext", "Ничего не нашлось");
            req.setAttribute("songs", songs);
            req.getRequestDispatcher("/resources/templates/audio-page.jsp").forward(req, resp);
        }
    }

}