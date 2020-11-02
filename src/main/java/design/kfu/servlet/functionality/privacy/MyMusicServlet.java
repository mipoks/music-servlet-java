package design.kfu.servlet.functionality.privacy;

import design.kfu.entity.dto.SongForm;
import design.kfu.helper.url.URLCoder;
import design.kfu.entity.Song;
import design.kfu.service.PersonMusicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/me")
public class MyMusicServlet extends HttpServlet {

    private PersonMusicService musicService;
    private SongForm songForm;

    @Override
    public void init() throws ServletException {
        musicService = new PersonMusicService();
        songForm = new SongForm();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Сохраненное");
        Collection<Song> songs = null;
        try {
            songs = musicService.getMusic(req, resp);
            req.setAttribute("songs", songs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        req.setAttribute("songemptytext", "Вы ещё ничего не добавили");
        req.getRequestDispatcher("/resources/templates/audio-page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception ex) {
        }

        String urlOrig = req.getParameter("orig");
        String name = req.getParameter("name");

        if (id <= 0 || urlOrig == null || name == null || urlOrig.equals("") || name.equals(""))
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        else {
            name = URLCoder.decodeURIComponent(name);
            songForm.setId(id);
            songForm.setUrlOrig(urlOrig);
            songForm.setName(name);
            int ans = musicService.addMusic(songForm, req, resp);
            if (ans == -1) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            }
        }
    }
}
