package fr.ele.services.mapping;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

import fr.ele.model.BookMakerSynchronization;
import fr.ele.model.ref.BookMaker;

@Service
public class BookMakerSynchronizerServiceImpl implements
		BookMakerSynchronizerService, BeanFactoryAware {

	private Map<String, SynchronizerService> synchronizers;

	@Override
	public BookMakerSynchronization synchronize(BookMaker bookMaker) {
		SynchronizerService service = synchronizers.get(bookMaker
				.getSynchronizerService());
		if (service == null) {
			throw new RuntimeException(String.format(
					"Sync service %s not found for bookmaker %s",
					bookMaker.getSynchronizerService(), bookMaker.getCode()));
		}
		String urlSync = bookMaker.getUrlSync();
		try {
			Object dto = service.unmarshall(urlSync, bookMaker.getEncoding());
			return service.synchronize(bookMaker.getCode(), dto);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		ListableBeanFactory lf = (ListableBeanFactory) beanFactory;
		synchronizers = lf.getBeansOfType(SynchronizerService.class);
	}

}
