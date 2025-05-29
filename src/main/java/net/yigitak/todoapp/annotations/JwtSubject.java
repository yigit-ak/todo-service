package net.yigitak.todoapp.annotations;


import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;


@Target( ElementType.PARAMETER )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@AuthenticationPrincipal( expression = "subject" )
public @interface JwtSubject { }

