// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dto;

import java.math.BigDecimal;

import java.sql.Clob;

import java.util.Date;


public class Purchase
{
  private int purchaseId;
  private Date date;
  private Clob description;
  private BigDecimal price;
  private byte paidOff;
  private int customerId;

  //
  // getters / setters
  //
  public int getPurchaseId()
  {
    return this.purchaseId;
  }

  public void setPurchaseId(int purchaseId)
  {
    this.purchaseId = purchaseId;
  }

  public Date getDate()
  {
    return this.date;
  }

  public void setDate(Date date)
  {
    this.date = date;
  }

  public Clob getDescription()
  {
    return this.description;
  }

  public void setDescription(Clob description)
  {
    this.description = description;
  }

  public BigDecimal getPrice()
  {
    return this.price;
  }

  public void setPrice(BigDecimal price)
  {
    this.price = price;
  }

  public byte getPaidOff()
  {
    return this.paidOff;
  }

  public void setPaidOff(byte paidOff)
  {
    this.paidOff = paidOff;
  }

  public int getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(int customerId)
  {
    this.customerId = customerId;
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

    if (obj instanceof Purchase == false)
    {
      return false;
    }

    Purchase other = (Purchase) obj;

    if (purchaseId != other.purchaseId)
    {
      return false;
    }

    if (false == date.equals(other.date))
    {
      return false;
    }

    if (false == description.equals(other.description))
    {
      return false;
    }

    if (false == price.equals(other.price))
    {
      return false;
    }

    if (paidOff != other.paidOff)
    {
      return false;
    }

    if (customerId != other.customerId)
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    int result = 29;

    result = (29 * result) + purchaseId;
    result = (29 * result) + date.hashCode();
    result = (29 * result) + description.hashCode();
    result = (29 * result) + price.hashCode();
    result = (29 * result) + (int) paidOff;
    result = (29 * result) + customerId;

    return result;
  }

  public String toString()
  {
    StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("Purchase").append("(");

    buffer.append("[").append("purchaseId").append("=").append(purchaseId).append("]");
    buffer.append("[").append("date").append("=").append(date).append("]");
    buffer.append("[").append("description").append("=").append(description).append("]");
    buffer.append("[").append("price").append("=").append(price).append("]");
    buffer.append("[").append("paidOff").append("=").append(paidOff).append("]");
    buffer.append("[").append("customerId").append("=").append(customerId).append("]");

    return buffer.append(")").toString();
  }
}
