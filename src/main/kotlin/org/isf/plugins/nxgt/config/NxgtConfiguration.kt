package org.isf.plugins.nxgt.config

import org.isf.plugins.nxgt.auth.port.IPermissionService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient


@Configuration
class NxgtConfiguration {
    companion object {
        const val NAME = "nxgt"
    }
    @Bean(NAME)
    fun provideRestClientFactory(): HttpServiceProxyFactory {
        val restClient = RestClient.create("http://localhost:4001")
        val proxyFactory = HttpServiceProxyFactory.builder()
            .exchangeAdapter(RestClientAdapter.create(restClient))
            .build()
        return proxyFactory
    }

    @Bean
    fun providePermissionService(@Qualifier(NAME) factory: HttpServiceProxyFactory): IPermissionService {
        return factory.createClient<IPermissionService>()
    }
}