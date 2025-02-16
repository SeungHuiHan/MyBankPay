package com.bankpay.membership.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultToken;

// Vault 설정을 하게 돼요
@Configuration
public class VaultConfig {
    @Value("${spring.cloud.vault.token}")
    private String vaultToken;
    @Value("${spring.cloud.vault.scheme}")
    private String vaultScheme;
    @Value("${spring.cloud.vault.host}")
    private String vaultHost;
    @Value("${spring.cloud.vault.port}")
    private int vaultPort;
//
    @Bean
    public VaultTemplate vaultTemplate(){
        VaultEndpoint endpoint = VaultEndpoint.create(vaultHost, vaultPort);
        endpoint.setScheme(vaultScheme); // http 방식 사용
        // vaultScheme: http, https


        VaultTemplate template = new VaultTemplate(endpoint, ()-> VaultToken.of(vaultToken));
        VaultKeyValueOperations ops = template.opsForKeyValue(
                "kv-v1/data/ecrypt", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2
        );

        if (ops.get("dbkey") != null && ops.get("dbkey").getData() != null) {
            String key = (String) ops.get("dbkey").getData().get("key");
            System.out.println("key: " + key);
        } else {
            System.out.println("dbkey 값이 존재하지 않거나 올바른 위치에 없습니다.");
        }

        return template;
    }
}
