package com.springboot.intuit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springboot.intuit.service.UserAPIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Bean
    public UserAPIService userAPIService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8081/") // Base URL of the API
                .addConverterFactory(GsonConverterFactory.create()) // Use Gson converter
                .build();

        return retrofit.create(UserAPIService.class);
    }
}

