package com.gongxm.photo;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * @author 作者 : gongxm
 * @version 创建时间：2020年2月23日 上午12:17:03
 * @description 描述 :
 * 
 */
@Configuration
public class SolrConfig {
	@Bean
	@ConditionalOnMissingBean(SolrTemplate.class)
	public SolrTemplate solrTemplate(SolrClient solrClient) {
		return new SolrTemplate(solrClient);
	}
}
