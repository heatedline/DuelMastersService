package com.heatedline.dm.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.solr.EnableFullTextSolrIndexing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

/**
 * ApacheSolr configuration class.
 * @author heatedline
 *
 */
@Configuration
@EnableFullTextSolrIndexing
@EnableFilesystemStores
public class DuelMastersServiceConfig {
	
	@Bean
	public SolrClient solrClient() {
		return new HttpSolrClient.Builder("http://localhost:8983/solr/Duema").build();
	}

}
