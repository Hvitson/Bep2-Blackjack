package nl.hu.bep2.casino.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

/*
 A class annotated with @Configuration so Spring initializes it on boot as config.
 The other annotation tells spring to enable HATEOAS according to the HAL spec.
 HAL is an *implementation* of HATEOAS. Much like Scrum is an implementation of Agile.
 */
@Configuration
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class HyperMediaConfiguration {
}
