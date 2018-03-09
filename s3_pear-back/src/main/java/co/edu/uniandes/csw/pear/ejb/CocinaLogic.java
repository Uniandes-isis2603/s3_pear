/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.pear.ejb;

import co.edu.uniandes.csw.pear.entities.CocinaEntity;
import co.edu.uniandes.csw.pear.entities.DietaTipoEntity;
import co.edu.uniandes.csw.pear.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.pear.persistence.CocinaPersistence;
import com.sun.xml.internal.rngom.ast.builder.BuildException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de Cocina.
 * @author js.garcial1
 */
@Stateless
public class CocinaLogic {
    
    /**
     * Constante que representa el logger
     */
    private static final Logger LOGGER = Logger.getLogger(CocinaLogic.class.getName());
    
    /**
     * Conexion con Persistencia
     */
    @Inject
    private CocinaPersistence persistence;
    
    
    /**
     * METODOS 
     */
    
    
    /**
     * Retorna todas las Cocinas Entities que se encuentran en la base de datos
     * @return 
     */
    public List<CocinaEntity> getCocinas() {
        LOGGER.info("Inicia consulta de todas las cocinas");
        List<CocinaEntity> cocinas =  persistence.findAll();
        LOGGER.info("Termina la consulta de todas las cocinas");
        return cocinas;
    }
    
    /**
     * Retorna una Cocina Entity por id
     * @param id
     * @return 
     */
    public CocinaEntity getCocina( Long id ) {
        LOGGER.log(Level.INFO, "Inicia consulta de cocina con id = {0}", id);
        CocinaEntity cocina = persistence.find(id);
        if ( cocina ==  null )
            LOGGER.log(Level.INFO, "No existe una Cocina con el id = {0}", id);
        LOGGER.log(Level.INFO, "Termina la consulta de cocina con id = {0}", id);
        return cocina;
    }
    
    /**
     * Retorna colection de instancias de Dietas asociadas a una Cocina por id
     * @param cocina_id
     * @return 
     */
    public List<DietaTipoEntity> getDietasDeCocina ( Long cocina_id ) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las dietas de la Cocina con id = {0}", cocina_id);
        return this.getCocina(cocina_id).getDietas();
    }
    
    /**
     * Retorna una Dieta por id asociada a una Cocina por id
     * @param dieta_id
     * @param cocina_id
     * @return 
     */
    public DietaTipoEntity getDietaDeCocina ( Long dieta_id, Long cocina_id ) {
        LOGGER.log(Level.INFO, "Incia consulta de Dieta con id = {0} de la Cocina con id = {1}", new Object[]{dieta_id, cocina_id});
        DietaTipoEntity dieta = new DietaTipoEntity();
        dieta.setId(dieta_id);
        int index = this.getCocina(cocina_id).getDietas().indexOf(dieta);
        if ( index >= 0 )
            return this.getCocina(cocina_id).getDietas().get(index);
        return null;
    }
    
    /**
     * Crea una Cocina y la guarda en la base de datos
     * @param entity de cocina a persistir
     * @return entidad de cocina persistida
     */
    public CocinaEntity createCocina( CocinaEntity entity ) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creacion de una cocina con id = {0}", entity.getId());
        verificaciones(entity);
        persistence.create(entity);
        LOGGER.log(Level.INFO, "Termina proceso de creacion de una cocina con id = {0}", entity.getId());
        return entity;
    } 
    
    /**
     * Verifica las reglas del negocio
     * @param entity sobre la cual se haran las verificaciones
     */
    public void verificaciones( CocinaEntity entity ) throws BusinessLogicException {
        if ( entity == null ) throw new BusinessLogicException( "La Cocina es NULL.") ;
        if ( entity.getCapacidad().isEmpty() || entity.getCapacidad() == null ) throw new BusinessLogicException( String.format("La Capacidad de Cocina ( capacidad = %s ) es invalida", entity.getCapacidad())) ;
        if ( entity.getUbicacion().isEmpty() || entity.getUbicacion() == null ) throw new BusinessLogicException( String.format("La Ubicacion de Cocina ( ubicacion = %s ) es invalida", entity.getUbicacion())) ;
        //assert !entity.getName().isEmpty() || entity.getName() != null : String.format("El Nombre de Cocina ( nombre = %s ) es invalida", entity.getName()) ;
        //if ( entity.getId() == null ) throw new BusinessLogicException( String.format("El ID de Cocina ( id = %s ) es invalida", entity.getId())) ;
    }
    
    /**
     * Agrega una instancia de Dieta existente a una Cocina por id
     * @param dieta_id
     * @param cocina_id
     * @return 
     */
    public DietaTipoEntity addDietaToCocina( Long dieta_id, Long cocina_id ) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar una Dieta con id = {0} a una Cocina con id = {1}", new Object[]{dieta_id, cocina_id});              
        DietaTipoEntity dieta = new DietaTipoEntity();
        dieta.setId(dieta_id);
        this.getCocina(cocina_id).getDietas().add(dieta);
        return this.getDietaDeCocina(dieta_id, cocina_id);
    }
    
    /**
     * Actualiza una Cocina por id
     * @param id de tipo Long, representa la cocina que se va a actualizar
     * @param entity de Cocina con los cambios deseados
     * @return la entidad de Cocina luego de ser actualizada
     */
    public CocinaEntity updateCocina( Long id, CocinaEntity entity ) throws BusinessLogicException {
        if ( persistence.find(entity.getId()) == null ) throw new BusinessLogicException("la cocina que se piensa eliminar no existe. ID = " + entity.getId());
        LOGGER.log(Level.INFO, "Inica proceso de actualizacion de la dieta con id = {0} " , id);
        CocinaEntity actualizado = persistence.update(entity);
        verificaciones(actualizado);
        LOGGER.log( Level.INFO, "Termina proceso de actualizacion de la cocina, id = {0}", entity.getId() );
        return actualizado;
    }
    
    /**
     * Reemplaza la dietas de la cocina por id
     * @param dietas
     * @param cocina_id
     * @return lista actualizada de dietas
     */
     public List<DietaTipoEntity> updateSemanasDeDieta( List<DietaTipoEntity> dietas, long cocina_id ) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar la lista de dietas de Cocina con id = {0}", new Object[] { cocina_id});
        this.getCocina(cocina_id).setDietas(dietas);
        return this.getCocina(cocina_id).getDietas();
    }
    
    /**
     * Elimina una Cocina por id
     * @param id 
     */
    public void delete( Long id ) {
        LOGGER.log(Level.INFO, "Inicia eliminacion de la cocina con id = {0} " , id);
        persistence.delete(id);
        LOGGER.log( Level.INFO, "Cocina con id = {0} eliminada. ", id );
    }
    
    /**
     * Elimina una Dieta por id de una Cocina por id
     * @param dieta_id
     * @param cocina_id 
     */
    public void deleteDietaDeCocina( Long dieta_id, Long cocina_id ) {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminar la Dieta con id = {0} de la Cocina con id = {1}", new Object[]{dieta_id,cocina_id});
        DietaTipoEntity dieta = new DietaTipoEntity();
        dieta.setId(dieta_id);
        this.getCocina(cocina_id).getDietas().remove(dieta);
    }
    
}
