package com.emily.batch;

/**
 * 手动触发任务
 */
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.emily.domain.Person;

@Configuration
@EnableBatchProcessing  //开启批处理的支持
public class TriggerBatchConfig {

	@Bean
	@StepScope
	public FlatFileItemReader<Person> reader(@Value("#{jobParameters['input.file.name']}") String pathToFile) throws Exception{
		FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
		reader.setResource(new ClassPathResource(pathToFile));
		reader.setLineMapper(new DefaultLineMapper<Person>(){{
			setLineTokenizer(new DelimitedLineTokenizer(){{
				setNames(new String[]{"name","age","nation","address"});
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){{
				setTargetType(Person.class);
			}});
		}});
		return reader;
	}
	
	@Bean
	public ItemProcessor<Person,Person> procssor(){
		
		//使用自定义的CsvItemProcessor
		CsvItemProcessor processor = new CsvItemProcessor();
		
		//为processor 指定校验器 为CsvBeanValidator
		processor.setValidator(csvBeanValidator());
		return processor;
	}
	
	@Bean
	public ItemWriter<Person> writer(DataSource dataSource){//Spring能让容器中已有的Bean以参数的形式注入，Spring Boot 已为我们定义了 dataSource
		
		//使用JDBC批处理的JdbcBatchItemWriter 来写数据到数据库
		JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
		String sql="insert into person " + "(id,name,age,nation,address) " + "values (hibernate_sequence.nextval, :name, :age, :nation, :address)";
		
		//设置要执行批处理的SQL语句
		writer.setSql(sql);
		writer.setDataSource(dataSource);
		return writer;
	}
	
	@Bean
	public JobRepository jobRepository(DataSource dataSource,PlatformTransactionManager transactionManager) throws Exception{
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		jobRepositoryFactoryBean.setDatabaseType("oracle");
		jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
		return jobRepositoryFactoryBean.getObject();
	}
	
	@Bean
	public SimpleJobLauncher jobLauncher(DataSource dataSource,PlatformTransactionManager transactionManager) throws Exception{
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository(dataSource,transactionManager));
		return jobLauncher;
	}
	
	@Bean
	public Job importJob(JobBuilderFactory jobs  , Step s1){
		return jobs.get("importJob")
				.incrementer(new RunIdIncrementer())
				.flow(s1)
				.end()
				.listener(csvJobListener())
				.build();
	}
	
	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory,ItemReader<Person> reader,ItemWriter<Person> writer,ItemProcessor<Person, Person> processor){
		return stepBuilderFactory
				.get("step1")
				.<Person,Person>chunk(65000)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}
	
	@Bean
	public CsvJobListener csvJobListener(){
		return new CsvJobListener();
	}
	
	@Bean
	public Validator<Person> csvBeanValidator(){
		return new CsvBeanValidator<Person>();
	}
}
