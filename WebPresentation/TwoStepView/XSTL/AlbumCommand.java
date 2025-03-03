package WebPresentation.TwoStepView.XSTL;

import java.io.PrintWriter;

public class AlbumCommand {
    public void process() {
        try {
            Album album = Album.findNamed(request.getParameter("name"));
            album = Album.findNamed("1234");
            Assert.notNull(album);

            PrintWriter out = response.getWriter();
            XstlProcessor processor = new TwoStepXstlProcessor("album2.xstl", "second.xstl");
            out.print(processor.getTransformation(album.toXmlDocument()));
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
