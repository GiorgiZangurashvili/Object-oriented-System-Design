import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class WebWorker extends Thread {
    private String url;
    private int rowNum;
    private WebFrame wf;

    public WebWorker(String url, int rowNum, WebFrame wf){
        this.url = url;
        this.rowNum = rowNum;
        this.wf = wf;
    }

/*
  This is the core web/download i/o code... */
 	private void download() {
        InputStream input = null;
        StringBuilder contents = null;
        try {
            long elapsedStart = System.currentTimeMillis();
            URL url = new URL(this.url);
            URLConnection connection = url.openConnection();

            // Set connect() to throw an IOException
            // if connection does not succeed in this many msecs.
            connection.setConnectTimeout(5000);

            connection.connect();
            input = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            char[] array = new char[1000];
            int len;
            contents = new StringBuilder(1000);
            boolean interrupted = false;
            while ((len = reader.read(array, 0, array.length)) > 0) {
                contents.append(array, 0, len);
                Thread.sleep(100);
                if(isInterrupted()){
                    interrupted = true;
                    this.wf.threadDone("interrupted", this.rowNum);
                }
            }
            if(!interrupted) {
                long elapsedEnd = System.currentTimeMillis();
                String date = new SimpleDateFormat("hh:mm:ss").format(new Date(elapsedStart));
                String elapsed = "" + (elapsedEnd - elapsedStart) + "ms";
                String byteSize = "" + contents.length() + " bytes";
                this.wf.threadDone(date + " " + elapsed + " " + byteSize, this.rowNum);
            }
        }
        // Otherwise control jumps to a catch...
        catch (MalformedURLException ignored) {
            this.wf.threadDone("err", this.rowNum);
        } catch (InterruptedException exception) {
            this.wf.threadDone("interrupted", this.rowNum);
        } catch (IOException ignored) {
            this.wf.threadDone("err", this.rowNum);
        }
        // "finally" clause, to close the input stream
        // in any case
        finally {
            try {
                if (input != null) input.close();
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void run(){
         download();
    }
}
