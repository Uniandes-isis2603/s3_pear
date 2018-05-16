/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.pear.ejb;


import co.edu.uniandes.csw.pear.entities.EnvioEntity;
import co.edu.uniandes.csw.pear.persistence.EnvioPersistence;
import co.edu.uniandes.csw.pear.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de Envio.
 * @author js.cabra
 */
@Stateless
public class EnvioLogic {
    
    /**
     * Constante que representa el logger
     */
    private static final Logger LOGGER = Logger.getLogger(EnvioLogic.class.getName());
    
    /**
     * Conexion con Persistencia
     */
    @Inject
    private EnvioPersistence persistence;
    
    
    /**
     * METODOS 
     */
    
    
    
    /**
     * Retorna todas los Envios Entities que se encuentran en la base de datos
     * @return 
     */
    public List<EnvioEntity> getEnvios() {
        LOGGER.info("Inicia consulta de todos los envios");
        List<EnvioEntity> envios =  persistence.findAll();
        LOGGER.info("Termina la consulta de todos los envios");
        return envios;
    }
    
    /**
     * Retorna un Envio Entity por id
     * @param id
     * @return 
     */
    public EnvioEntity getEnvio( long id ) {
        LOGGER.log(Level.INFO, "Inicia consulta de envios con id = {0}", id);
        EnvioEntity envio = persistence.find(id);
        if ( envio ==  null )
            LOGGER.log(Level.INFO, "No existe un Envio con el id = {0}", id);
        LOGGER.log(Level.INFO, "Termina la consulta de envio con id = {0}", id);
        return envio;
    }
    
    /**
     * Crea un Envio y la guarda en la base de datos
     * @param entity de envio a persistir
     * @return entidad de envio persistida
     */
    public EnvioEntity createEnvio( EnvioEntity entity ) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creacion de un envio con id = {0}", entity.getId());
        if(getEnvio(entity.getId())==null)
        {
        persistence.create(entity);
        }
         else
        {
            throw new BusinessLogicException ("este id ya existe");
        }
        LOGGER.log(Level.INFO, "Termina proceso de creacion de un envio con id = {0}", entity.getId());
        return entity;
    } 
    
    /**
     * Actualiza un Envio por id
     * @param id de tipo Long, representa el envio que se va a actualizar
     * @param entity de Envio con los cambios deseados
     * @return la entidad de Envio luego de ser actualizada
     */
    public EnvioEntity updateEnvio( long id, EnvioEntity entity ) throws BusinessLogicException {

        EnvioEntity actualizado = null;
        LOGGER.log(Level.INFO, "Inica proceso de actualizacion de un envio con id = {0} " , id);
        if(id == entity.getId())
        {
         actualizado = persistence.update(entity);
        }
         else
        {
            throw new BusinessLogicException ("el id no puede ser cambiado");
        }
        LOGGER.log( Level.INFO, "Termina proceso de actualizacion de un envio, id = {0}", entity.getId() );
        return actualizado;
    }
    
    /**
     * Elimina un Envio por id
     * @param id 
     */
    public void delete( long id ) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia eliminacion de el envio con id = {0} " , id);
        if(getEnvio(id)!=null)
        {
        persistence.delete(id);
        }
        else
        {
            throw new BusinessLogicException ("este no existe");
        }
        LOGGER.log( Level.INFO, "Envio con id = {0} eliminada. ", id );
    }
    
}
