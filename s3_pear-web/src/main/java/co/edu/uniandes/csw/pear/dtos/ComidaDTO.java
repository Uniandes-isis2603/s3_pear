/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.pear.dtos;
import co.edu.uniandes.csw.pear.entities.ComidaEntity;
import co.edu.uniandes.csw.pear.entities.DiaEntity;


/**
 *
 * Objeto de transferencia de datos de comidas. Los DTO contienen
 * las represnetaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "cantidad": number,
 *      "alimentos":string,
 *      "direccion: string,
 *      "tipo": string,
 *      "image": String
 *
 *   }
 * </pre> 
 * @author js.cabra
 */
public class ComidaDTO {

    private Integer cantidad;

    private String alimentos;

    private String Tipo;

    private Long id;
    
   
    

    /**
     * Constructor ComidaDTO a partir de la Entity
     *
     * @param entidad
     */
    public ComidaDTO(ComidaEntity entidad) {
        if(entidad != null) {
        cantidad = entidad.getCantidad();
        alimentos = entidad.getAlimentos();
        Tipo = entidad.getTipo();
        
        }
    }

    /**
     * Constructor vacio
     */
    public ComidaDTO() {
        //Constructor para propositos de persistencia
    }

    public Long getId() {
        return id;
    }

    public void setId(Long pId) {
        id = pId;
    }

    /**
     *
     * @return la cantidad de comida.
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     *
     * @param pCantidad nueva cantidad.
     */
    public void setCantidad(Integer pCantidad) {
        cantidad = pCantidad;
    }

    /**
     *
     * @return los alimentos que estan en esta comida.
     */
    public String getAlimentos() {
        return alimentos;

    }

    /**
     *
     * @param pAlimentos nuevos alimentos.
     */
    public void setAlimentos(String pAlimentos) {
        alimentos = pAlimentos;
    }

    /**
     *
     * @return el tipo de comida.
     */
    public String getTipo() {
        return Tipo;
    }

    /**
     *
     * @param pTipo nuevo tipo.
     */
    public void setTIPO(String pTipo) {
        Tipo = pTipo;
    }
    
  
 
    public ComidaEntity toEntity() {
        ComidaEntity en = new ComidaEntity();
        en.setAlimentos(this.alimentos);
        en.setCantidad(this.cantidad);
        en.setId(this.id);
        if ("desayuno".equalsIgnoreCase(Tipo)) {
            en.setTIPO("desayuno");
        } 
        else if ("almuerzo".equalsIgnoreCase(Tipo)) {
            en.setTIPO("almuerzo");
        } 
        else if ("cena".equalsIgnoreCase(Tipo)) {
            en.setTIPO("cena");
        } else if ("adicional".equalsIgnoreCase(Tipo)) {
            en.setTIPO("adicional");
        }
       
       
        return en;
    }
}
