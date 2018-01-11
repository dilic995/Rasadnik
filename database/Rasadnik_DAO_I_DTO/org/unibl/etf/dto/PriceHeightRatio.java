// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dto;

import java.math.BigDecimal;


public class PriceHeightRatio
{
  private PriceHeightRatioId id;
  private BigDecimal heightMin;
  private BigDecimal heightMax;
  private BigDecimal price;
  private byte active;

  //
  // getters / setters
  //
  public PriceHeightRatioId getId()
  {
    return this.id;
  }

  public void setId(PriceHeightRatioId id)
  {
    this.id = id;
  }

  public BigDecimal getHeightMin()
  {
    return this.heightMin;
  }

  public void setHeightMin(BigDecimal heightMin)
  {
    this.heightMin = heightMin;
  }

  public BigDecimal getHeightMax()
  {
    return this.heightMax;
  }

  public void setHeightMax(BigDecimal heightMax)
  {
    this.heightMax = heightMax;
  }

  public BigDecimal getPrice()
  {
    return this.price;
  }

  public void setPrice(BigDecimal price)
  {
    this.price = price;
  }

  public byte getActive()
  {
    return this.active;
  }

  public void setActive(byte active)
  {
    this.active = active;
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

    if (obj instanceof PriceHeightRatio == false)
    {
      return false;
    }

    PriceHeightRatio other = (PriceHeightRatio) obj;

    if (false == id.equals(other.id))
    {
      return false;
    }

    if (false == heightMin.equals(other.heightMin))
    {
      return false;
    }

    if (false == heightMax.equals(other.heightMax))
    {
      return false;
    }

    if (false == price.equals(other.price))
    {
      return false;
    }

    if (active != other.active)
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    int result = 29;

    result = (29 * result) + id.hashCode();
    result = (29 * result) + heightMin.hashCode();
    result = (29 * result) + heightMax.hashCode();
    result = (29 * result) + price.hashCode();
    result = (29 * result) + (int) active;

    return result;
  }

  public String toString()
  {
    StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("PriceHeightRatio")
                                                               .append("(");

    buffer.append("[").append("id").append("=").append(id).append("]");
    buffer.append("[").append("heightMin").append("=").append(heightMin).append("]");
    buffer.append("[").append("heightMax").append("=").append(heightMax).append("]");
    buffer.append("[").append("price").append("=").append(price).append("]");
    buffer.append("[").append("active").append("=").append(active).append("]");

    return buffer.append(")").toString();
  }
}
