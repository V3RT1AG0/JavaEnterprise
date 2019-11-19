package swehw3;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

// The file makes use of hibernate for persistence

public class Persistence {
	private static SessionFactory sessionFactory;

	// Load configuration files for hibernate from hibernate.cfg in resources and initialize it.
	static {
		try {
			if (sessionFactory == null) {
				Configuration config = new Configuration();
				config.addAnnotatedClass(swehw3.Student.class);
				config.configure();
				
				StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
				serviceRegistryBuilder.applySettings(config.getProperties());

				ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

				sessionFactory = config.buildSessionFactory(serviceRegistry);
			}

		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		// create sessionFactory object
		return sessionFactory;
	}

	public static void shutdown() {
		// close the seesion
		getSessionFactory().close();
	}
}
