package argonet.board.api;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TestPabble {
    static PebbleEngine engine = new PebbleEngine.Builder().build();

    public static void main(String[] args) throws IOException {
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/home.peb");

        Writer writer = new StringWriter();

        Map<String, Object> context = new HashMap<>();
        context.put("websiteTitle","My First Website");
        context.put("content","My Interesting Content");

        compiledTemplate.evaluate(writer,context);

        String output = writer.toString();

    }
}
