package com.qpg.config;

import java.time.Duration;

import com.qpg.domain.QuestionBluePrintDetail;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.qpg.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.qpg.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.qpg.domain.User.class.getName());
            createCache(cm, com.qpg.domain.Authority.class.getName());
            createCache(cm, com.qpg.domain.User.class.getName() + ".authorities");
            createCache(cm, com.qpg.domain.PersistentToken.class.getName());
            createCache(cm, com.qpg.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, com.qpg.domain.CategoryMaster.class.getName());
            createCache(cm, com.qpg.domain.CategoryMaster.class.getName() + ".subCategoryMasters");
            createCache(cm, com.qpg.domain.SubCategoryMaster.class.getName());
            createCache(cm, com.qpg.domain.SubCategoryMaster.class.getName() + ".subjectMasters");
            createCache(cm, com.qpg.domain.SubjectMaster.class.getName());
            createCache(cm, com.qpg.domain.SubSubjectMaster.class.getName());
            createCache(cm, com.qpg.domain.SubSubjectMaster.class.getName() + ".topicMasters");
            createCache(cm, com.qpg.domain.TopicMaster.class.getName());
            createCache(cm, com.qpg.domain.SubTopicMaster.class.getName());
            createCache(cm, com.qpg.domain.SubTopicMaster.class.getName() + ".questionMasters");
            createCache(cm, com.qpg.domain.QuestionMaster.class.getName());
            createCache(cm, com.qpg.domain.QuestionTypeMaster.class.getName());
            createCache(cm, com.qpg.domain.QuestionTypeMaster.class.getName() + ".questionMasters");
            createCache(cm, com.qpg.domain.DifficultyTypeMaster.class.getName());
            createCache(cm, com.qpg.domain.DifficultyTypeMaster.class.getName() + ".questionMasters");
            createCache(cm, com.qpg.domain.AnswerMaster.class.getName());
            createCache(cm, com.qpg.domain.QuestionMaster.class.getName() + ".answerMasters");
            createCache(cm, com.qpg.domain.QuestionMaster.class.getName() + ".questionMasters");
            createCache(cm, com.qpg.domain.ExamMaster.class.getName());
            createCache(cm, com.qpg.domain.ExamMaster.class.getName() + ".questionBluePrintMasters");
            createCache(cm, com.qpg.domain.QuestionBluePrintMaster.class.getName());
            createCache(cm, QuestionBluePrintDetail.class.getName());
            createCache(cm, com.qpg.domain.QuestionBankMaster.class.getName());
            createCache(cm, com.qpg.domain.CollegeMaster.class.getName());
            createCache(cm, com.qpg.domain.CollegeMaster.class.getName() + ".departmentMasters");
            createCache(cm, com.qpg.domain.DepartmentMaster.class.getName());
            createCache(cm, com.qpg.domain.DepartmentMaster.class.getName() + ".courseMasters");
            createCache(cm, com.qpg.domain.CourseMaster.class.getName());
            createCache(cm, com.qpg.domain.CourseMaster.class.getName() + ".categoryMasters");
            createCache(cm, com.qpg.domain.TestEntity.class.getName());
            createCache(cm, com.qpg.domain.QbMaster.class.getName());
            createCache(cm, com.qpg.domain.QpBankUploadMaster.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
