package cz.muni.fi.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by vacullik on 22/02/2017.
 */
public interface TimetablingClient {

    InputStream getUpdate() throws IOException;

    void submitSplit(String solution) throws UnsupportedEncodingException, IOException;

    InputStream getEvaluation() throws IOException;
}
