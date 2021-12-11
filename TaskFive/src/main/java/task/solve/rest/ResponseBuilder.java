package task.solve.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder implements Serializable {

    private Map<String, String> headers;
    private String body;
    private HttpStatus status;

    public ResponseBuilder() {
        headers = new HashMap<>();
    }

    public ResponseBuilder addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public ResponseBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public ResponseBuilder setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

/*
Пример ответа на запрос
    /*
HTTP/1.1 200
status: 200
content-type: application/json
cache-control: no-cache,no-store,max-age=0,must-revalidate

{"status": "success"}


     */
    public void write(OutputStream output) throws IOException {
        if(this.status == null){
            throw new IllegalArgumentException("Please, provide HttpStatus");
        }
        if(this.body == null){
            throw new IllegalArgumentException("Please, provide body");
        }
//        throw new UnsupportedOperationException();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

        writer.write("HTTP/1.1 " + this.status);
        writer.newLine();
        writer.write(String.valueOf(this.status));
        writer.newLine();
        writer.write(String.valueOf(this.headers));
        writer.newLine();
        writer.newLine();
        writer.write(this.body);
        writer.newLine();
        writer.newLine();
        writer.newLine();
        writer.flush();

//        SerializationHelper<ResponseBuilder> temp = new SerializationHelper<ResponseBuilder>(ResponseBuilder.class);
//        temp.writeJsonToStream(output, this.);
    }

    public static void write404(OutputStream output) throws IOException {
        ResponseBuilder builder = new ResponseBuilder();
        builder
                .setStatus(HttpStatus.NOT_FOUND)
                .setBody("<h1>Page not found</h1>")
                .addHeader("cache-control", "no-cache,no-store,max-age=0,must-revalidate")
                .addHeader("content-type", "text/html; charset=UTF-8")
                .write(output);
    }

    public static void writeError(OutputStream output, Exception exception) throws IOException {
        ResponseBuilder builder = new ResponseBuilder();
        builder
                .setStatus(HttpStatus.SERVER_ERROR)
                .setBody("<h1>Server error</h1><br /><pre>"+exception.getMessage()+"</pre>")
                .addHeader("cache-control", "no-cache,no-store,max-age=0,must-revalidate")
                .addHeader("content-type", "text/html; charset=UTF-8")
                .write(output);
    }

    public static void writeSuccess(OutputStream output) throws IOException {
        ResponseBuilder builder = new ResponseBuilder();
        builder
                .setStatus(HttpStatus.OK)
                .addHeader("cache-control", "no-cache,no-store,max-age=0,must-revalidate")
                .addHeader("content-type", "application/json")
                .setBody("{\"status\": \"sucess\"}")
                .write(output);
    }

    public static void writeFailure(OutputStream output) throws IOException {
        ResponseBuilder builder = new ResponseBuilder();
        builder
                .setStatus(HttpStatus.OK)
                .addHeader("cache-control", "no-cache,no-store,max-age=0,must-revalidate")
                .addHeader("content-type", "application/json")
                .setBody("{\"status\": \"failed\"}")
                .write(output);
    }
}
