package com.ihc.apirest.utilities;


public class GenericFunctions 
{
  
  /**
   * Método que permite convertir una celda a numérica, en caso contrario 
   * @param value Celda a evaluar
   * @return Valor celda
   */
  public static String castNumericCell(String value) 
  {
    try
    {
      if("".equals(value))
      {
        return "";
      }

      double numberValue =Double.parseDouble(value);

      return String.format("%.0f", numberValue);
    }
    catch(Exception e)
    {
      return value.toString();
    }
  }

}
