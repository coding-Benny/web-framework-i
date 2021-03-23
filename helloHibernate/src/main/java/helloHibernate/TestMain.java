package helloHibernate;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestMain {

	private static SessionFactory sessionFactory;
	
	public static void main(String[] args) {
		
		/*
		 * Configuration conf = new Configuration();
		 * conf.configure();
		 * sessionFactory = conf.buildSessionFactory();
		 */
		
		// chained method
		sessionFactory = new Configuration().configure().buildSessionFactory();
		
		Category category1 = new Category();
		category1.setName("컴퓨터");
		
		Category category2 = new Category();
		category2.setName("자동차");
		
		Product product1 = new Product();
		product1.setName("Notebook1");
		product1.setPrice(2000);
		product1.setDescription("Awesome Notebook!!");
		product1.setCategory(category1);
		category1.getProducts().add(product1);
		
		Product product2 = new Product();
		product2.setName("Notebook2");
		product2.setPrice(3000);
		product2.setDescription("Powerful Notebook!!");
		product2.setCategory(category1);
		category1.getProducts().add(product2);
		
		Product product3 = new Product();
		product3.setName("Sonata");
		product3.setPrice(3000);
		product3.setDescription("Popular Car!!");
		product3.setCategory(category2);
		category2.getProducts().add(product3);
		
		// session 1
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Serializable cid = session.save(category1);
		session.save(category2);
		
		tx.commit();
		
		session.close();
		
		// session 2
		Session session2 = sessionFactory.openSession();
		Transaction tx2 = session2.beginTransaction();
		
		Category aCategory = session2.get(Category.class, cid);
		
		Set<Product> products = aCategory.getProducts();
		
		for(Product p: products)
			System.out.println(p.getName());
		
		tx2.commit();
		session2.close();
		
		sessionFactory.close();
		
	}

}
