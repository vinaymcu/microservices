package com.acn.avs.push.messaging.repository.impl;

import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acn.avs.push.messaging.tenant.TenantContext;
import com.acn.avs.push.messaging.util.SearchCriteriaMapper;
import com.acn.avs.push.messaging.util.SearchFilter;

import mockit.MockUp;

/**
 * @author Happy.Dhingra
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomPMRepositoryImplTest {
	private static final String queryTest="SELECT msg FROM Messages msg WHERE msg.expirationDatetime > 1471471421 AND msg.status=1 AND msg.multiCast=1 and (msg.immediately=1 or msg.displayDatetime < 1471478621)";
	@InjectMocks
	CustomPushMessagingRepositoryImpl customPushMessagingRepositoryImpl;

	@Mock
	TenantContext tenantContext;
	
	@Mock
	EntityManager entityManager;
	
	@Mock
	private SearchCriteriaMapper criteriaMapperImpl;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		new MockUp<TenantContext>() {
			@mockit.Mock
			public void setCurrentTenant(final Object tenant) {
			}
		};
	}

	@Test // test NoResultException method
	public void testDeleteByMessageId() {
		try {
			when(entityManager.createNamedQuery("DELETE_BY_MESSAGE_ID")).thenThrow(new NoResultException());
			customPushMessagingRepositoryImpl.deleteByMessageId(45L);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test // test NoResultException method
	public void testGetAllActiveMulticastMessages() {
		try {
			Query query = getQuery();
			when(entityManager.createQuery(queryTest)).thenReturn(query);
			customPushMessagingRepositoryImpl.getAllActiveMulticastMessages(2,1471471421);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test // test deleteExpiredMessages method
	public void testDeleteExpiredMessages() {
		try {
			Query query = getQuery();
			when(entityManager.createNamedQuery("DELETE_PAST_MESSAGES")).thenReturn(query);
			customPushMessagingRepositoryImpl.deleteExpiredMessages();
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	/**
	 * check exception
	 */
	@Test // test deleteExpiredMessages exception method
	public void testDeleteExpiredMessages2() {
		try {
			when(entityManager.createNamedQuery("DELETE_PAST_MESSAGES")).thenThrow(new NoResultException());
			customPushMessagingRepositoryImpl.deleteExpiredMessages();
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test // test testDeleteFilterByMessageId method
	public void testDeleteFilterByMessageId() {
		try {
			Query query = getQuery();
			when(entityManager.createNamedQuery("DELETE_FILTERS_BY_MESSAGE_ID")).thenReturn(query);
			customPushMessagingRepositoryImpl.deleteFilterByMessageId(1L);
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test // test testDeleteFilterByMessageId exception method
	public void testDeleteFilterByMessageId2() {
		try {
			when(entityManager.createNamedQuery("DELETE_FILTERS_BY_MESSAGE_ID")).thenThrow(new NoResultException());
			customPushMessagingRepositoryImpl.deleteFilterByMessageId(1L);
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test // test search method
	public void testSearch() {
		try {
			Query query = getQuery();
			SearchFilter search = new SearchFilter();
			search.setMapper(getMapper());
			when(entityManager.createQuery("SELECT M FROM Messages M  WHERE M.null >=-1 ORDER BY M.null")).thenReturn(query);
			customPushMessagingRepositoryImpl.search(search);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/**
	 * 
	 */
	private SearchCriteriaMapper getMapper() {
		SearchCriteriaMapper mapper=new SearchCriteriaMapper() {
			
			@Override
			public String sortMapProperty(String criteria) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String searchByMapProperty(String criteria) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		return mapper;
	}
	

	private Query getQuery() {
		Query query=new Query() {
			
			@Override
			public <T> T unwrap(Class<T> cls) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setParameter(int position, Date value, TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setParameter(int position, Calendar value, TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setParameter(String name, Date value, TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setParameter(String name, Calendar value, TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setParameter(int position, Object value) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setParameter(String name, Object value) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T> Query setParameter(Parameter<T> param, T value) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setMaxResults(int maxResult) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setLockMode(LockModeType lockMode) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setHint(String hintName, Object value) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setFlushMode(FlushModeType flushMode) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Query setFirstResult(int startPosition) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isBound(Parameter<?> param) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getSingleResult() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List getResultList() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Set<Parameter<?>> getParameters() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getParameterValue(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getParameterValue(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T> T getParameterValue(Parameter<T> param) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T> Parameter<T> getParameter(int position, Class<T> type) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T> Parameter<T> getParameter(String name, Class<T> type) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Parameter<?> getParameter(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Parameter<?> getParameter(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getMaxResults() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public LockModeType getLockMode() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, Object> getHints() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public FlushModeType getFlushMode() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getFirstResult() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int executeUpdate() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		return query;
	}
}
