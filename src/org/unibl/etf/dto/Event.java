package org.unibl.etf.dto;

import java.util.Date;

public class Event {
	private Integer eventId;
	private String name;
	private String description;
	private Date date;
	private Boolean done;
	private Boolean deleted;

	//
	// getters / setters
	//
	public Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getName() {
		return this.name;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getDone() {
		return this.done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("Event").append("(");

		buffer.append("[").append("eventId").append("=").append(eventId).append("]");
		buffer.append("[").append("name").append("=").append(name).append("]");
		buffer.append("[").append("description").append("=").append(description).append("]");
		buffer.append("[").append("date").append("=").append(date).append("]");
		buffer.append("[").append("done").append("=").append(done).append("]");

		return buffer.append(")").toString();
	}
}
