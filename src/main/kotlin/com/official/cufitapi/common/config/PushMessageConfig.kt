package com.official.cufitapi.common.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.official.cufitapi.common.config.property.FirebaseProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PushMessageConfig(
    private val firebaseProperties: FirebaseProperties
) {

    @ConditionalOnMissingBean
    @Bean(name = ["firebaseMessaging"])
    fun firebaseMessaging(firebaseApp: FirebaseApp): FirebaseMessaging =
        FirebaseMessaging.getInstance(firebaseApp)


    @ConditionalOnMissingBean
    @Bean(name = ["firebaseApp"])
    fun firebaseApp(googleCredentials: GoogleCredentials): FirebaseApp {
        if (FirebaseApp.getApps().isEmpty()) {
            val options = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build()
            return FirebaseApp.initializeApp(options)
        }
        return FirebaseApp.getInstance(FirebaseApp.DEFAULT_APP_NAME)
    }

    @ConditionalOnMissingBean
    @Bean(name = ["googleCredentials"])
    fun googleCredentials(): GoogleCredentials =
        firebaseProperties.resource.inputStream
            .use(GoogleCredentials::fromStream)
            .createScoped(firebaseProperties.scopes)
}
