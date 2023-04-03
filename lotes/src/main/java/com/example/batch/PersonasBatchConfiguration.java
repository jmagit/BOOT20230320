package com.example.batch;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.model.PhotoDTO;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.example.model.Persona;
import com.example.model.PersonaDTO;

@Configuration
public class PersonasBatchConfiguration {
	@Autowired
	JobRepository jobRepository;
	@Autowired
	PlatformTransactionManager transactionManager;
	
	public FlatFileItemReader<PersonaDTO> personaCSVItemReader(String fname) {
		return new FlatFileItemReaderBuilder<PersonaDTO>().name("personaCSVItemReader")
				.resource(new ClassPathResource(fname))
				.linesToSkip(1)
				.delimited()
				.names(new String[] { "id", "nombre", "apellidos", "correo", "sexo", "ip" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<PersonaDTO>() { {
						setTargetType(PersonaDTO.class);
					}})
				.build();
	}
	@Autowired
	public PersonaItemProcessor personaItemProcessor;

	@Bean
	public JdbcBatchItemWriter<Persona> personaDBItemWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Persona>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO personas VALUES (:id,:nombre,:correo,:ip)")
				.dataSource(dataSource)
				.build();
	}
	
	@Bean
	public Step importCSV2DBStep1(JdbcBatchItemWriter<Persona> personaDBItemWriter) {
		return new StepBuilder("importCSV2DBStep1", jobRepository)
				.<PersonaDTO, Persona>chunk(10, transactionManager)
				.reader(personaCSVItemReader("personas-1.csv"))
				.processor(personaItemProcessor)
				.writer(personaDBItemWriter)
				.build();
	}
	
	// DB a CSV
	
	@Bean
	JdbcCursorItemReader<Persona> personaDBItemReader(DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Persona>().name("personaDBItemReader")
				.sql("SELECT id, nombre, correo, ip FROM personas").dataSource(dataSource)
				.rowMapper(new BeanPropertyRowMapper<>(Persona.class))
				.build();
	}

	@Bean
	public FlatFileItemWriter<Persona> personaCSVItemWriter() {
		return new FlatFileItemWriterBuilder<Persona>().name("personaCSVItemWriter")
				.resource(new FileSystemResource("output/outputData.csv"))
				.lineAggregator(new DelimitedLineAggregator<Persona>() {
					{
						setDelimiter(",");
						setFieldExtractor(new BeanWrapperFieldExtractor<Persona>() {
							{
								setNames(new String[] { "id", "nombre", "correo", "ip" });
							}
						});
					}
				}).build();
	}
	
	@Bean
	public Step exportDB2CSVStep(JdbcCursorItemReader<Persona> personaDBItemReader) {
		return new StepBuilder("exportDB2CSVStep", jobRepository)
				.<Persona, Persona>chunk(100, transactionManager)
				.reader(personaDBItemReader)
				.writer(personaCSVItemWriter())
				.build();
	}

	// Tasklet
	@Bean
	public FTPLoadTasklet ftpLoadTasklet(@Value("${input.dir.name:./ftp}") String dir) {
		FTPLoadTasklet tasklet = new FTPLoadTasklet();
		tasklet.setDirectoryResource(new FileSystemResource(dir));
		return tasklet;
	}

	@Bean
	public Step copyFilesInDir(FTPLoadTasklet ftpLoadTasklet) {
	        return new StepBuilder("copyFilesInDir", jobRepository)
	            .tasklet(ftpLoadTasklet, transactionManager)
	            .build();
	}


//	@Bean
//	public Job personasJob(PersonasJobListener listener, Step importCSV2DBStep1, 
//			Step exportDB2CSVStep, Step copyFilesInDir) {
//		return new JobBuilder("personasJob", jobRepository)
//				.incrementer(new RunIdIncrementer())
//				.listener(listener)
//				.start(copyFilesInDir)
//				.next(importCSV2DBStep1)
//				.next(exportDB2CSVStep)
//				.build();
//	}

	@Autowired 
	private PhotoRestItemReader photoRestItemReader;
	@Autowired 
	private ItemFailureLoggerListener erroresListener;
	
//	@Bean
	public Job photoJob() {
		String[] headers = new String[] { "id", "author", "width", "height", "url", "download_url" };
		return new JobBuilder("photoJob", jobRepository)
			.incrementer(new RunIdIncrementer())
			.start(new StepBuilder("photoJobStep1", jobRepository)
				.listener(erroresListener)
				.<PhotoDTO, PhotoDTO>chunk(100, transactionManager)
				.reader(photoRestItemReader)
				.writer(new FlatFileItemWriterBuilder<PhotoDTO>().name("photoCSVItemWriter")
					.resource(new FileSystemResource("output/photoData.csv"))
					.headerCallback(new FlatFileHeaderCallback() {
						public void writeHeader(Writer writer) throws IOException {
						writer.write(String.join(",", headers));
						}})
					.lineAggregator(new DelimitedLineAggregator<PhotoDTO>() { {
						setDelimiter(",");
						setFieldExtractor(new BeanWrapperFieldExtractor<PhotoDTO>() { {
							setNames(headers);
						}});
					}}).build())
				.build())
			.build();
	}

	public StaxEventItemReader<PersonaDTO> personaXMLItemReader() {
		XStreamMarshaller marshaller = new XStreamMarshaller();
		Map<String, Class> aliases = new HashMap<>();
		aliases.put("Persona", PersonaDTO.class);
		marshaller.setAliases(aliases);
		marshaller.setTypePermissions(AnyTypePermission.ANY);
		return new StaxEventItemReaderBuilder<PersonaDTO>()
				.name("personaXMLItemReader")
				.resource(new ClassPathResource("Personas.xml"))
				.addFragmentRootElements("Persona")
				.unmarshaller(marshaller).build();
	}	
	@Bean
	public Step importXML2DBStep1(JdbcBatchItemWriter<Persona> personaDBItemWriter) {
		return new StepBuilder("importXML2DBStep1", jobRepository)
				.<PersonaDTO, Persona>chunk(10, transactionManager)
				.reader(personaXMLItemReader())
				.processor(personaItemProcessor)
				.writer(personaDBItemWriter).build();
	}
	
	public StaxEventItemWriter<Persona> personaXMLItemWriter() {
		XStreamMarshaller marshaller = new XStreamMarshaller();
		Map<String, Class> aliases = new HashMap<>();
		aliases.put("Persona", Persona.class);
		marshaller.setAliases(aliases);
		return new StaxEventItemWriterBuilder<Persona>()
				.name("personaXMLItemWriter")
				.resource(new FileSystemResource("output/outputData.xml"))
				.marshaller(marshaller)
				.rootTagName("Personas")
				.overwriteOutput(true)
				.build();
	}
	
	@Bean
	public Step exportDB2XMLStep(JdbcCursorItemReader<Persona> personaDBItemReader) {
		return new StepBuilder("exportDB2XMLStep", jobRepository)
				.<Persona, Persona>chunk(100, transactionManager)
				.reader(personaDBItemReader)
				.writer(personaXMLItemWriter())
				.build();
	}
	
	@Bean
	public Job personasJob(Step importXML2DBStep1, Step exportDB2XMLStep, Step exportDB2CSVStep) {
		return new JobBuilder("personasJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(importXML2DBStep1)
				.next(exportDB2XMLStep)
				.next(exportDB2CSVStep)
				.build();
	}



}