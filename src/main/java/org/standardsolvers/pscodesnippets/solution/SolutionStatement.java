package org.standardsolvers.pscodesnippets.solution;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface  SolutionStatement {
    Class<? extends Ps> ps();
}
