/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.pear.entities;

import java.io.Serializable;

/**
 *
 * @author ga.bejarano10
 */
public class CalificacionEntity extends BaseEntity implements Serializable
{
    private double puntuacion;

    /**
     * 
     * @return puntuacion. 
     */
    public double getPuntuacion() {
        return puntuacion;
    }

    /**
     * 
     * @param nueva puntuacion 
     */
    public void setPuntuacion(double puntuacion)
    {
        this.puntuacion = puntuacion;
    }
    
}
