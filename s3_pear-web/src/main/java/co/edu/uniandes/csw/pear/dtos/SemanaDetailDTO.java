/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.pear.dtos;
import co.edu.uniandes.csw.pear.entities.SemanaEntity;
import java.util.List;

/**
 *
 * @author v.chacon
 */
public class SemanaDetailDTO extends SemanaDTO {
    
    //-----------------------------------------------------------
    //Atributos
    //-----------------------------------------------------------
    private List<DiaDTO> dias;
    //-----------------------------------------------------------
    //Constructor
    //-----------------------------------------------------------
    
    public SemanaDetailDTO(SemanaEntity entity){
        super(entity);
    }
    
    //-----------------------------------------------------------
    //Metodos
    //-----------------------------------------------------------
    /**
     * Determina los dias que contiene la semana, esta lista siempre tiene que tener 7 elementos. 
     * @param lista de los dias
     */
    public void setListaDias(List<DiaDTO> lista){
       dias = lista;
    }
    
    /**
     * @return lista de los dias que componen la semana
     */
    public List<DiaDTO> getListaDias(){
        return dias;
    }
    
}
