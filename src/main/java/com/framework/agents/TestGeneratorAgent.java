package com.framework.agents;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;

public class TestGeneratorAgent {

    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String MODEL = "llama-3.3-70b-versatile";

    private String apiKey;
    private OkHttpClient client;

    public TestGeneratorAgent() {
        this.apiKey = System.getenv("GROQ_API_KEY");   // env variable se key
        this.client = new OkHttpClient();
    }

    // MAIN method: HTML + test description do, Java code milega
    public String generateTestCode(String url, String pageHtml, String testDescription) throws Exception {

        // 1. LLM ke liye prompt banao
        String prompt = buildPrompt(url, pageHtml, testDescription);

        // 2. Groq ko request bhejo
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);

        JSONArray messages = new JSONArray();
        messages.put(message);

        JSONObject body = new JSONObject();
        body.put("model", MODEL);
        body.put("messages", messages);
        body.put("temperature", 0.3);   // kam = zyada consistent code

        RequestBody requestBody = RequestBody.create(
            body.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
            .url(GROQ_URL)
            .addHeader("Authorization", "Bearer " + apiKey)
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
            .build();

        // 3. response se generated code nikalo
     // 3. response se generated code nikalo
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();

            // ===== DEBUG: yeh 3 lines add karo =====
            System.out.println("===== GROQ RAW RESPONSE =====");
            System.out.println(responseBody);
            System.out.println("=============================");
            // =======================================

            JSONObject json = new JSONObject(responseBody);
            String generatedCode = json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
            return generatedCode;
        }
            }
            
        
    

    // prompt banane wala helper — yahan hum LLM ko instructions dete hain
    private String buildPrompt(String url, String pageHtml, String testDescription) {
        return "You are a Selenium test automation expert.\n\n"
             + "URL: " + url + "\n\n"
             + "Page HTML (relevant parts):\n" + pageHtml + "\n\n"
             + "Test to automate: " + testDescription + "\n\n"
             + "Generate a complete Selenium Java Page Object Model:\n"
             + "1. A Page Object class with locators and action methods\n"
             + "2. A TestNG test class using the Page Object\n"
             + "Use WebDriverWait for waits. Use robust XPath/CSS locators.\n"
             + "Return ONLY the Java code, no explanations.";
    }

    // generated code ko file mein save karo
    public void saveToFile(String code, String fileName) throws Exception {
        FileWriter writer = new FileWriter(fileName);
        writer.write(code);
        writer.close();
        System.out.println("Code saved to: " + fileName);
    }
}