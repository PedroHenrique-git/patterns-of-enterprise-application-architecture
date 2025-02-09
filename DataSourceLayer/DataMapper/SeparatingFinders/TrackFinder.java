package DataSourceLayer.DataMapper.SeparatingFinders;

import javax.sound.midi.Track;

public interface TrackFinder {
    Track find(long id);

    Track findForAlbum(long albumID);
}
