package com.batch.arqs.config.batch;

import com.batch.arqs.infra.exception.CustomHandlerExecption;
import com.batch.arqs.infra.liteners.ListenerDeciderExecution;
import com.batch.arqs.infra.models.ObjetoEntrada;
import com.batch.arqs.infra.models.ObjetoSaida;
import com.batch.arqs.infra.processor.ProcessorDados;
import com.batch.arqs.infra.tasklet.TaskletFinalizaProcessamento;
import com.batch.arqs.infra.tasklet.TaskletProcessaArqsInicial;
import com.batch.arqs.infra.tasklet.TaskletProcessamentoEncerrado;
import com.batch.arqs.infra.tasklet.TaskletTrataArquivos;
import com.batch.arqs.infra.writer.WriterDados;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BatchConfig {

    @Value("${common.chunk}")
    private int chunk;

    @Value("${common.sourcePath}")
    private String sourcePath;

    @Value("${arq.dados}")
    private String arqDados;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CustomHandlerExecption customHandlerExecption;
    private final ListenerDeciderExecution listenerDeciderExecution;
    private final TaskletProcessaArqsInicial taskletProcessaArqsInicial;
    private final TaskletTrataArquivos taskletTrataArquivos;
    private final TaskletFinalizaProcessamento taskletFinalizaProcessamento;
    private final TaskletProcessamentoEncerrado taskletProcessamentoEncerrado;
    private final WriterDados writerDados;

    @Bean
    public Job cargaArquivos() {
        return jobBuilderFactory.get("cargaArquivos")
                .incrementer(new RunIdIncrementer())
                .start(stepDeletaArquivosAntigos())
                .on("STOPPPED")
                .to(stepProcessamentoEncerrado())
                .from(stepDeletaArquivosAntigos())
                .next(stepProcessaArqsInicial())
                .next(stepTrataArquivos())
                .next(stepProcessaDados())
                .next(stepFinalizaProcesso())
                .end()
                .build();
    }

    @Bean
    public Step stepDeletaArquivosAntigos() {
        return stepBuilderFactory.get("stepDeletaArquivosAntigos")
                .tasklet(taskletFinalizaProcessamento)
                .listener(listenerDeciderExecution)
                .build();
    }

    @Bean
    public Step stepFinalizaProcesso() {
        return stepBuilderFactory.get("stepDeletaArquivosLocais")
                .tasklet(taskletFinalizaProcessamento)
                .build();
    }

    @Bean
    public Step stepProcessamentoEncerrado() {
        return stepBuilderFactory.get("stepProcessamentoEncerrado")
                .tasklet(taskletProcessamentoEncerrado)
                .build();
    }

    @Bean
    public Step stepProcessaArqsInicial() {
        return stepBuilderFactory.get("stepProcessaArqsInicial")
                .tasklet(taskletProcessaArqsInicial)
                .build();
    }

    @Bean
    public Step stepTrataArquivos() {
        return stepBuilderFactory.get("stepTrataArquivos")
                .tasklet(taskletTrataArquivos)
                .build();
    }

    @Bean
    public Step stepProcessaDados() {
        return stepBuilderFactory.get("stepProcessaDados")
                .<ObjetoEntrada, ObjetoSaida>chunk(this.chunk)
                .reader(readerDados())
                .processor(processamentoDeDados())
                .writer(writerDados)
                .exceptionHandler(customHandlerExecption)
                .build();
    }

    @Bean
    @StepScope
    public ProcessorDados processamentoDeDados() {
        return new ProcessorDados();
    }

    public FlatFileItemReader<ObjetoEntrada> readerDados() {
        FixedLengthTokenizer lineTokenizer = new FixedLengthTokenizer();
        lineTokenizer.setNames(new String[]{
                "linha",
                "arquivo",
                "nomeMae",
                "nome",
                "nomePai"
        });
        lineTokenizer.setColumns(
                new Range(1, 163),
                new Range(1, 50),
                new Range(51, 99),
                new Range(100, 149),
                new Range(150, 163)
        );
        BeanWrapperFieldSetMapper<ObjetoEntrada> beanWrapper = new BeanWrapperFieldSetMapper<>();
        beanWrapper.setTargetType(ObjetoEntrada.class);
        DefaultLineMapper<ObjetoEntrada> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(beanWrapper);
        FlatFileItemReader<ObjetoEntrada> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(sourcePath + "/" + arqDados));
        reader.setEncoding("UTF-8");
        reader.setLineMapper(lineMapper);
        return reader;
    }
}
