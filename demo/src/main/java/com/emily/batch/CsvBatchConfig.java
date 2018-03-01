package com.emily.batch;

/**
 * 自动触发任务
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.emily.domain.Person;

@Configuration//注释掉此注解不让其自动触发
@EnableBatchProcessing  //开启批处理的支持
public class CsvBatchConfig {

	@Bean
	@StepScope
	public ItemReader<Person> reader() throws Exception{
		
		//使用FlatFileItemReader 读取文件
		FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
		
		//使用FlatFileItemReader 的setResource 方法设置csv文件的路径
		reader.setResource(new ClassPathResource("person.txt"));
		
		//对csv文件的数据和领域模型类做对应映射
		reader.setLineMapper(new DefaultLineMapper<Person>(){{
			setLineTokenizer(new DelimitedLineTokenizer(){{
				setDelimiter("-");
				setNames(new String[]{"name","age","nation","address"});
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){{
				setTargetType(Person.class);
			}});			
		}});
		return reader;
	}
	
	@Bean
	@StepScope
	public ItemProcessor<Person,Person> procssor(){
		
		//使用自定义的CsvItemProcessor
		CsvItemProcessor processor = new CsvItemProcessor();
		
		//为processor 指定校验器 为CsvBeanValidator
		processor.setValidator(csvBeanValidator());
		return processor;
	}
	
	@Bean
	@StepScope
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
	@StepScope
	public JobRepository jobRepository(DataSource dataSource,PlatformTransactionManager transactionManager) throws Exception{
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		jobRepositoryFactoryBean.setDatabaseType("oracle");
		jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
		return jobRepositoryFactoryBean.getObject();
	}
	
	@Bean
	@StepScope
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
