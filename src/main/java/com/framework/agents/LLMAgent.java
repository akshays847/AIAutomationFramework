package com.framework.agents;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;

public class LLMAgent {

    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String MODEL = "llama-3.3-70b-versatile";

    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey;

    public LLMAgent() {
        // read the key from the environment variable you set in Eclipse
        this.apiKey = System.getenv("GROQ_API_KEY");
    }

    // returns true only if we actually have a key to use
    public boolean isAvailable() {
        return apiKey != null && !apiKey.trim().isEmpty();
    }

    public String generateName(WebElement el) {
        try {
            // grab the element's actual HTML to give the LLM context
            String html = el.getAttribute("outerHTML");
            if (html != null && html.length() > 600) {
                html = html.substring(0, 600);   // keep the prompt small/cheap
            }

            String prompt =
                "You are naming a web element for a Selenium test framework. " +
                "Given this HTML, return ONLY a short snake_case variable name " +
                "(e.g. login_button, search_input, forgot_password_link). " +
                "No explanation, no quotes, just the name.\n\nHTML:\n" + html;

            // build the request body Groq expects (OpenAI-compatible format)
            JSONObject message = new JSONObject()
                    .put("role", "user")
                    .put("content", prompt);

            JSONObject body = new JSONObject()
                    .put("model", MODEL)
                    .put("messages", new JSONArray().put(message))
                    .put("temperature", 0)        // deterministic = same name each run
                    .put("max_tokens", 20);

            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(
                            body.toString(),
                            MediaType.parse("application/json")))
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.out.println("LLM call failed: " + response.code());
                    return null;
                }
                String responseBody = response.body().string();

                // dig the model's answer out of the JSON response
                JSONObject json = new JSONObject(responseBody);
                String name = json.getJSONArray("choices")
                                  .getJSONObject(0)
                                  .getJSONObject("message")
                                  .getString("content")
                                  .trim();

                return cleanName(name);
            }

        } catch (Exception e) {
            System.out.println("LLM error: " + e.getMessage());
            return null;   // fall back to rule-based name
        }
    }

    // safety net: strip anything that isn't a clean snake_case name
    private String cleanName(String name) {
        String clean = name.toLowerCase()
                           .replaceAll("[^a-z0-9_]", "_")
                           .replaceAll("_+", "_")
                           .replaceAll("^_+|_+$", "");
        return clean.isEmpty() ? null : clean;
    }
}