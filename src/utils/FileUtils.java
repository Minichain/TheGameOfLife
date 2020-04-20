package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileUtils {
    public static String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();

        FileInputStream in = new FileInputStream(filename);

        Exception exception = null;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));

            Exception innerExc= null;
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    source.append(line).append('\n');
                }
            } catch (Exception e) {
                exception = e;
            } finally {
                try {
                    reader.close();
                }
                catch (Exception exc) {
                    if (innerExc == null) {
                        innerExc = exc;
                    } else {
                        exc.printStackTrace();
                    }
                }
            }

            if (innerExc != null) {
                throw innerExc;
            }
        } catch (Exception e) {
            exception = e;
        } finally {
            try {
                in.close();
            }
            catch (Exception e) {
                if (exception == null) {
                    exception = e;
                } else {
                    e.printStackTrace();
                }
            }

            if (exception != null) {
                throw exception;
            }
        }

        return source.toString();
    }
}
