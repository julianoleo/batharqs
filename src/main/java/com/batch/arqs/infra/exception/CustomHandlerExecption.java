package com.batch.arqs.infra.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

@Component
@StepScope
public class CustomHandlerExecption implements ExceptionHandler {

    @Value("#{StepExecution}")
    public StepExecution stepExecution;

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomHandlerExecption.class);

    @Override
    public void handleException(RepeatContext context, Throwable throwable) throws Throwable {
        String mensagem;
        stepExecution.getExecutionContext().put("EXCEPTION_THROWN", true);
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        mensagem = "Processamento finalizado com exececao por " + throwable.getCause() + " - mais detalhes: " + throwable.getMessage() + " - trace {" + sw.toString() + "}";
        LOGGER.error("{}", mensagem);
    }
}
