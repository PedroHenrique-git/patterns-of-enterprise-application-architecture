package WebPresentation.FrontController;

import java.io.IOException;

import ObjectRelationalStructural.ForeignKeyMapping.SingleValueReference.Artist;

public class ArtistCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        Artist artist = Artist.find(request.getParameter("name"));
        request.setAttribute("helper", new ArtistHelper(artist));
        forward("/artist.jsp");
    }
}
