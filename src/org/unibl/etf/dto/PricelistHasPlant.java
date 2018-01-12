// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dto;

public class PricelistHasPlant
{
 
  private Pricelist pricelistId;
  private Plant plantId;

  //
  // getters / setters
  //
  public Pricelist getPricelistId()
  {
    return this.pricelistId;
  }

  public void setPricelistId(Pricelist pricelistId)
  {
    this.pricelistId = pricelistId;
  }

  public Plant getPlantId()
  {
    return this.plantId;
  }

  public void setPlantId(Plant plantId)
  {
    this.plantId = plantId;
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }

    if (null == obj)
    {
      return false;
    }

    if (obj instanceof PricelistHasPlant == false)
    {
      return false;
    }

    PricelistHasPlant other = (PricelistHasPlant) obj;

    if (pricelistId != other.pricelistId)
    {
      return false;
    }

    if (plantId != other.plantId)
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    int result = 29;

    result = (29 * result) + pricelistId.hashCode();
    result = (29 * result) + plantId.hashCode();

    return result;
  }

  public String toString()
  {
    StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("PricelistHasPlant")
                                                               .append("(");

    buffer.append("[").append("pricelistId").append("=").append(pricelistId).append("]");
    buffer.append("[").append("plantId").append("=").append(plantId).append("]");

    return buffer.append(")").toString();
  }
}
