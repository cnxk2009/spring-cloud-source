/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.netflix.eureka.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Responsible for adding in a marker bean to activate
 * {@link EurekaServerAutoConfiguration}
 *  Spring Boot 启动的时候会到eureka server jar包Meta-INF中找到spring.factories中配置了EurekaServerAutoConfiguration类
 *  将此类加载到Spring容器中,其中有一条注解@ConditionalOnBean(EurekaServerMarkerConfiguration.Marker.class)
 *  容器中有Marker Bean 时EurekaServerAutoConfiguration才会生效
 *
 * @author Biju Kunjummen
 */
@Configuration
public class EurekaServerMarkerConfiguration {

    @Bean
    public Marker eurekaServerMarkerBean() {
        return new Marker();
    }

    class Marker {
    }
}
