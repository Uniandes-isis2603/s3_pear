/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.pear.test.logic;

import co.edu.uniandes.csw.pear.ejb.SemanaLogic;
import co.edu.uniandes.csw.pear.entities.DiaEntity;
import co.edu.uniandes.csw.pear.entities.SemanaEntity;
import co.edu.uniandes.csw.pear.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.pear.persistence.SemanaPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author v.chacon
 */
@RunWith(Arquillian.class)
public class SemanaLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
      
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<SemanaEntity> data = new ArrayList<SemanaEntity>();
    
    private List<DiaEntity> diasData = new ArrayList<DiaEntity>();
    
    
    @Inject
    private SemanaLogic logic;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SemanaEntity.class.getPackage())
                .addPackage(SemanaLogic.class.getPackage())
                .addPackage(SemanaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
       /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from SemanaEntity").executeUpdate();
        em.createQuery("delete from DiaEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SemanaEntity entity = factory.manufacturePojo(SemanaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            DiaEntity dias = factory.manufacturePojo(DiaEntity.class);
            em.persist(dias);
            diasData.add(dias);
        }
    }
    
    /**
     * Prueba para crear una Semana
     * @throws BusinessLogicException
     */
    @Test
    public void createTest() throws BusinessLogicException {
        SemanaEntity newEntity = factory.manufacturePojo(SemanaEntity.class);
        SemanaEntity result = logic.createSemana(newEntity);
        Assert.assertNotNull(result);
        SemanaEntity entity = em.find(SemanaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getName(),entity.getName());
        Assert.assertEquals(newEntity.getId(),entity.getId());
    }
    
    /**
     * Prueba para consultar las semanas
     */
    @Test
    public void getListTest() {
        List<SemanaEntity> list = logic.getSemanas();
        Assert.assertEquals( data.size(), list.size());
        for (SemanaEntity entity : list) {
            boolean found = false;
            for (SemanaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
     /**
     * Prueba para consultar una Semana
     */
    @Test
    public void getSemanaTest() {
        SemanaEntity entity = data.get(0);
        SemanaEntity resultEntity = logic.getSemana(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
    }
    
    /**
     * Prueba para eliminar una Semana
     */
    @Test
    public void deleteSemanaTest() throws BusinessLogicException {
        SemanaEntity entity = data.get(0);
        
        logic.deleteSemana(entity.getId());
        SemanaEntity deleted = em.find(SemanaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar una semana
     */
    @Test
    public void updateSemanaTest() throws BusinessLogicException {
        SemanaEntity entity = data.get(0);
        SemanaEntity pojoEntity = factory.manufacturePojo(SemanaEntity.class);

        pojoEntity.setId(entity.getId());

        logic.updateSemana(pojoEntity.getId(), pojoEntity);

        SemanaEntity resp = em.find(SemanaEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
 
    }
    
    /**
     * Prueba para obtener una instancia de Dias asociada a una instancia
     * Semana
     */
    @Test
    public void getDiaTest() throws BusinessLogicException {
        SemanaEntity entity = data.get(0);
        DiaEntity diaEntity = diasData.get(0);
        DiaEntity response = logic.getDiaSemana(entity.getId(), diaEntity.getId());

        Assert.assertEquals(diaEntity.getId(), response.getId());
        Assert.assertEquals(diaEntity.getName(), response.getName());
        Assert.assertEquals(diaEntity.getFecha(), response.getFecha());
        Assert.assertEquals(diaEntity.getSeEnvia(), response.getSeEnvia());
        Assert.assertEquals(diaEntity.getRecomendacion(), response.getRecomendacion());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Dias asociadas a una
     * instancia Semana
     */
    @Test
    public void listDiasTest() {
        List<DiaEntity> list = logic.getDiasSemana(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    
}