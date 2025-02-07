package com.bankpay.utility;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyMoneyDataSimulator {
    private static final String DECREASE_API_ENDPOINT = "http://localhost:8083/money/decrease-eda";
    private static final String INCREASE_API_ENDPOINT = "http://localhost:8083/money/increase-eda";

    private static final String CREATE_MONEY_API_ENDPOINT = "http://localhost:8083/money/create-member-money";
    private static final String REGISTER_ACCOUNT_API_ENDPOINT = "http://localhost:8082/banking/account/register-eda";

    private static final String[] BANK_NAME = {"KB", "Shinhan", "Woori"};
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        List<Integer> readyMemberList = new ArrayList<>();

        //무한루프
        while (true) {
            //증액 머니, 감액 머니
            int amount = random.nextInt(20001) - 10000; // Random number between -100000 and 100000
            int targetMembershipId = random.nextInt(1000) + 1; // Random number between 1 and 100000

            registerAccountSimulator(REGISTER_ACCOUNT_API_ENDPOINT, targetMembershipId);
            createMemberMoneySimulator(CREATE_MONEY_API_ENDPOINT, targetMembershipId);
            Thread.sleep(100);
            readyMemberList.add(targetMembershipId);

            //증액
            increaseMemberMoneySimulator(INCREASE_API_ENDPOINT, amount, targetMembershipId);

            amount = random.nextInt(20001) - 10000; // Random number between -100000 and 100000

            //감액
            Integer decreaseTargetMembershipId = readyMemberList.get(random.nextInt(readyMemberList.size()));
            increaseMemberMoneySimulator(DECREASE_API_ENDPOINT, amount, decreaseTargetMembershipId);

            try {
                Thread.sleep(100); // Wait for 1 second before making the next API call
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void increaseMemberMoneySimulator(String apiEndpoint, int amount, int targetMembershipId) {
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject jsonRequestBody = new JSONObject();

            jsonRequestBody.put("targetMembershipId", targetMembershipId);
            jsonRequestBody.put("amount", amount);

            call(apiEndpoint, conn, jsonRequestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //계좌 등록
    private static void registerAccountSimulator(String apiEndpoint, int targetMembershipId) {
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            Random random = new Random();

            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("membershipId", targetMembershipId);
            jsonRequestBody.put("bankName", BANK_NAME[random.nextInt(BANK_NAME.length)]);
            jsonRequestBody.put("bankAccountNumber", generateRandomAccountNumber());
            jsonRequestBody.put("linkedStatusIsValid", true);


            System.out.println("Sending JSON Request: " + jsonRequestBody.toString());

            call(apiEndpoint, conn, jsonRequestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //머니 지갑 만들기
    private static void createMemberMoneySimulator(String apiEndpoint, int targetMembershipId) {
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("membershipId", targetMembershipId);

            call(apiEndpoint, conn, jsonRequestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void call(String apiEndpoint, HttpURLConnection conn, JSONObject jsonRequestBody) throws IOException {
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(jsonRequestBody.toString().getBytes());
        outputStream.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        System.out.println("API Response from " + apiEndpoint + ": " + response.toString());
    }

    //랜덤한 계좌번호 생성
    private static String generateRandomAccountNumber() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10); // Generate a random digit (0 to 9)
            sb.append(digit);
        }

        return sb.toString();
    }
}
